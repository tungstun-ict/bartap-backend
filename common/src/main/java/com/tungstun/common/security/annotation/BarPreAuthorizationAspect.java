package com.tungstun.common.security.annotation;

import com.tungstun.common.security.Authorization;
import com.tungstun.common.security.BartapUserDetails;
import com.tungstun.common.security.exception.NotAuthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Handles methods annotated with {@code @BarPreAuthorization}.
 * Aspect pre-processes the method by taking in the {@code barId} and {@code roles} that are authorized to invoke the annotated method.
 * The aspect retrieves the access token from the request headers and authenticates and authorized the user.
 * If given {@code roles} is an empty array, only authentication will find place.
 */
@Aspect
@Component
public class BarPreAuthorizationAspect {
    @SuppressWarnings("java:S1186")
    @Pointcut("@annotation(com.tungstun.common.security.annotation.BarPreAuthorization)")
    private void barPreAuthorizationAnnotation() {
    }

    @SuppressWarnings("java:S2201")
    @Around("@annotation(com.tungstun.common.security.annotation.BarPreAuthorization)")
    public Object handleBarPreAuthorization(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        BarPreAuthorization annotation = methodSignature.getMethod().getAnnotation(BarPreAuthorization.class);
        String barId = extractBarId(annotation.id(), methodSignature, pjp);
        String[] roles = annotation.roles();

        BartapUserDetails bartapUserDetails = (BartapUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Authorization> authorizations = bartapUserDetails.getAuthorizations();
        if (authorizations != null) {
            authorizations.stream()
                    .filter(authorization -> authorization.barId().equals(barId))
                    .filter(authorization -> Arrays.stream(roles).anyMatch(authorization.role()::equalsIgnoreCase))
                    .findAny()
                    .orElseThrow(() -> new NotAuthorizedException("User not authorized for action or resource"));
        }
        return pjp.proceed();
    }

    /**
     * If the id string starts with '#' this method will extract the value of the given parameter name
     * If id also contains a '.' value it will assume the given parameter value is an object
     * and reflectively retrieve the given field name following the '.' character.
     *
     * @return String id value
     */
    private String extractBarId(String id, final MethodSignature methodSignature, final ProceedingJoinPoint pjp) throws IllegalAccessException {
        if (!id.startsWith("#")) {
            return id;
        }
        String parameterName = id.replace("#", "");
        if (!parameterName.contains(".")) {
            int parameterIndex = List.of(methodSignature.getParameterNames()).indexOf(parameterName);
            if (parameterIndex == -1) {
                throw new NoSuchParameterException("No parameter found with the name " + parameterName);
            }
            return (String) pjp.getArgs()[parameterIndex];
        }

        String[] splitParameterName = parameterName.split("\\.");
        String classParameterName = splitParameterName[0];
        String fieldName = splitParameterName[1];

        int parameterIndex = Arrays.asList(methodSignature.getParameterNames()).indexOf(classParameterName);
        if (parameterIndex == -1) {
            throw new NoSuchParameterException("No parameter found with the name " + classParameterName);
        }
        Object argObject = pjp.getArgs()[parameterIndex];
        return FieldUtils.getFieldValue(argObject, fieldName).toString();
    }
}
