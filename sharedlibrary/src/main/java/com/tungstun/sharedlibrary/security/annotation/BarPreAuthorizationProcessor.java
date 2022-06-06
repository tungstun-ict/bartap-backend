package com.tungstun.sharedlibrary.security.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Annotation processor that verifies the id arguments in all {@code BarPreAuthorization} annotations at build time.
 * */
@SupportedAnnotationTypes({"com.tungstun.sharedlibrary.security.annotation.BarPreAuthorization"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BarPreAuthorizationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getElementsAnnotatedWith(BarPreAuthorization.class)) {
                if (element instanceof ExecutableElement method) {
                    processElement(method);
                }
            }
        }
        return true;
    }

    private void processElement(ExecutableElement method) {
        String idOrExpression = method.getAnnotation(BarPreAuthorization.class).id();
        if (!idOrExpression.startsWith("#")) {
            // Id is a static coded id value
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    "Hard coded id value used. " +
                            "Only use hardcoded values if you are sure this value will always be the same.",
                    method);
            return;
        }

        String strippedExpression = idOrExpression.replace("#", "");
        if (strippedExpression.contains(".")) {
            // Expression contains parameter and field name
            handleParameterFieldExpression(strippedExpression, method);
        } else {
            // Expression contains a parameter name
            if (findParameterClass(method, strippedExpression) == null) {
                createBuildError(String.format("Parameter does not exist: '%s'", strippedExpression), method);
            }
        }
    }

    private void handleParameterFieldExpression(String strippedExpression, ExecutableElement method) {
        String[] splitParameterNames = strippedExpression.split("\\.");
        String parameterName = splitParameterNames[0];
        String fieldName = splitParameterNames[1];
        VariableElement paramClass = findParameterClass(method, parameterName);
        if (paramClass == null) {
            createBuildError(String.format("Parameter does not exist: '%s'", parameterName), method);
        } else if (!hasParameterWithField(paramClass, fieldName)) {
            createBuildError(String.format("Class of parameter '%s' does not contain field: '%s'", parameterName, fieldName), method);
        }
    }

    private VariableElement findParameterClass(ExecutableElement method, String className) {
        return method.getParameters().stream()
                .filter(param -> param.getSimpleName().toString().equals(className))
                .findFirst()
                .orElse(null);
    }

    private boolean hasParameterWithField(VariableElement parameterClassElement, String fieldName) {
        return ((DeclaredType) parameterClassElement.asType())
                .asElement()
                .getEnclosedElements()
                .stream()
                .anyMatch(element -> element.getKind().equals(ElementKind.FIELD) &&
                        fieldName.equals(element.getSimpleName().toString()));
    }

    private void createBuildError(String msg, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element);
    }
}
