package com.client;

import com.client.types.ConstantFactory;
import com.client.types.Packet;
import com.client.types.PacketData;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

@SupportedOptions("debug") // declare the `debug` option
@SupportedAnnotationTypes("com.client.types.Packet")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class PacketProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.messager = this.processingEnv.getMessager();

        // Packet
        int packetIdx = 0;
        String packageName = "";
        List<String> constMap = new ArrayList<>();
        for(Element element : roundEnv.getElementsAnnotatedWith(Packet.class)) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                packageName = this.getPackage(typeElement.getQualifiedName().toString());
                final String constName = this.setupPacketAnnotation(packetIdx, typeElement);
                if (constName != null) {
                    constMap.add(constName);
                    this.printLog(Diagnostic.Kind.NOTE, "Idx " + " Class " + constName);
                    packetIdx++;
                }
            }
        }
        this.writeConstantsClass(packageName, constMap);
//        // ConstantFactory
//        for(Element element : roundEnv.getElementsAnnotatedWith(ConstantFactory.class)) {
//            if (element instanceof TypeElement) {
//                this.setupConstantFactoryAnnotation((TypeElement) element, messager);
//            }
//        }
        return true;
    }

    private void setupConstantFactoryAnnotation(TypeElement typeElement) {
        if (typeElement.getKind() != ElementKind.CLASS) {
            this.printLog(Diagnostic.Kind.ERROR, "ConstantFactory can only be applied to the class!");
            return;
        }
        final String packageName = this.getPackage(typeElement.getQualifiedName().toString());
        ConstantFactory annotation = typeElement.getAnnotation(ConstantFactory.class);
//        this.writeConstantsClass(annotation.name(), packageName, annotation.value());
    }

    private void writeConstantsClass(String packageName, List<String> constMap) {
        final String className = "PacketPids";
        TypeSpec.Builder navigatorClass = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        for (short i = 0; i < constMap.size(); i++) {
            FieldSpec constantField = FieldSpec.builder(short.class, this.toConstantName(constMap.get(i)))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$L", i)
                    .build();
            navigatorClass.addField(constantField);
        }
        try {
            JavaFile.builder(packageName, navigatorClass.build())
                    .build().writeTo(this.processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String setupPacketAnnotation(int packetIdx, TypeElement typeElement) {
        if (typeElement.getKind() != ElementKind.CLASS) {
            this.printLog(Diagnostic.Kind.ERROR, "Packet can only be applied to the class!");
            return null;
        }

//        if (typeElement.getSuperclass().toString().equals(BasePacket.)) TODO
//            messager.printMessage(Diagnostic.Kind.ERROR, "SuperClass " + typeElement.getSuperclass().toString());

        final String packageName = this.getPackage(typeElement.getQualifiedName().toString());
        final String className = typeElement.getSimpleName().toString();
//        final int packetPid = typeElement.getAnnotation(Packet.class).value();
        this.printLog(Diagnostic.Kind.NOTE, "Class " + className + " = " + packageName);

//        String className = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();

        List<VariableElement> variablesList = new ArrayList<>();
        for (Element varElement : typeElement.getEnclosedElements()) {
            if (varElement.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) varElement;
                if (variableElement.getAnnotation(PacketData.class) != null) {
                    if (variableElement.getModifiers().contains(Modifier.PROTECTED)){
                        if (variableElement.asType().getKind().isPrimitive() || variableElement.asType().toString().equals(String.class.getName())) {
                            variablesList.add(variableElement);
                        } else {
                            this.printLog(Diagnostic.Kind.ERROR, variableElement.asType().toString() + "Variable needs to be primitive or string!");
                        }
                    } else {
                        this.printLog(Diagnostic.Kind.WARNING, variableElement.getSimpleName().toString() + " field has to be protected!");
                    }
                }
                // variableElement.getSimpleName().toString(), variableElement.asType()
//                        messager.printMessage(Diagnostic.Kind.ERROR, variableElement.getSimpleName() + " = " + variableElement.asType().toString());
            }

        }
//                if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
//                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "it should be final");
//                }

        this.writePacketExtensionClass(className, typeElement.asType(), packageName, packetIdx, variablesList);
        this.printLog(Diagnostic.Kind.NOTE, "Created!");
        return className;
    }

    private void writePacketExtensionClass(String className, TypeMirror classNameExtends, String packageName, int packetPid, List<VariableElement> variablesList) {
        final String CLASS_SUFFIX = "Packet";
        final String SETTER_PREFIX = "set";
        final String GETTER_PREFIX = "get";

        TypeSpec.Builder navigatorClass = TypeSpec
                .classBuilder(className + CLASS_SUFFIX)
                .superclass(TypeName.get(classNameExtends))
                .addModifiers(Modifier.PUBLIC);

//        for (Map.Entry<String, TypeName> element : variablesList.entrySet())
//            navigatorClass.addField(element.getValue(), element.getKey(), Modifier.PRIVATE);

        MethodSpec constructor = MethodSpec.methodBuilder("setPacketPid")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this.packetConstructor.setPacketPid($L)", packetPid)
                .build();
        navigatorClass.addMethod(constructor);

        for (VariableElement variableElement : variablesList) {
            String variableName = variableElement.getSimpleName().toString();
            TypeName variableType = TypeName.get(variableElement.asType());
            MethodSpec setter;
            if (variableType.isPrimitive()) {
                setter = MethodSpec
                        .methodBuilder(SETTER_PREFIX + this.capitalize(variableName))
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(variableType, variableName)
                        .addStatement("this.$N = $N", variableName, variableName)
                        .addStatement("this.packetConstructor.packVariable($S, this.$N)", variableName, variableName)
                        .build();
            } else {
                setter = MethodSpec
                        .methodBuilder(SETTER_PREFIX + this.capitalize(variableName))
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(variableType, variableName)
                        .addStatement("this.$N = $N", variableName, variableName)
                        .addStatement("this.packetConstructor.packVariable($S, this.$N, $L)", variableName, variableName, variableElement.getAnnotation(PacketData.class).stringLength())
                        .build();
            }

            navigatorClass.addMethod(setter);
        }

        for (VariableElement variableElement : variablesList) {
            String variableName = variableElement.getSimpleName().toString();
            TypeName variableType = TypeName.get(variableElement.asType());
            MethodSpec getter;
            String fieldTypeName;
            if (variableType.isPrimitive()) {
                fieldTypeName = this.capitalize(variableType.toString());
                getter = MethodSpec
                        .methodBuilder(GETTER_PREFIX + this.capitalize(variableName))
                        .returns(variableType)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.$N = this.packetConstructor.getPacked$N($S)", variableName, fieldTypeName, variableName)
                        .addStatement("return this.$N", variableName)
                        .build();
            } else {
                fieldTypeName = variableType.toString().substring(variableType.toString().lastIndexOf(".") + 1, variableType.toString().length());
                getter = MethodSpec
                        .methodBuilder(GETTER_PREFIX + this.capitalize(variableName))
                        .returns(variableType)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.$N = this.packetConstructor.getPacked$N($S, $L)", variableName, fieldTypeName, variableName, variableElement.getAnnotation(PacketData.class).stringLength())
                        .addStatement("return this.$N", variableName)
                        .build();
            }
            navigatorClass.addMethod(getter);
        }
        try {
            JavaFile.builder(packageName, navigatorClass.build())
                    .build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPackage(String classWithPackage) {
        return classWithPackage.substring(0, classWithPackage.lastIndexOf('.'));
    }

    private String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String toConstantName(String sentence) {
        int lastWordIdx = 0;
        String constantName = "PACKET_";
        for (int i = 1; i < sentence.length(); i++)
            if (Character.isUpperCase(sentence.charAt(i))) {
                constantName += sentence.substring(lastWordIdx, i).toUpperCase() + "_";
                lastWordIdx = i;
            }
        return constantName + sentence.substring(lastWordIdx, sentence.length()).toUpperCase();
    }

    private void printLog(Diagnostic.Kind kind, String message) {
        this.processingEnv.getMessager().printMessage(kind, message);
    }
}
