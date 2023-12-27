package com.techsavvy.security.aspects;

import com.techsavvy.security.annotations.ApiPermission;
import com.techsavvy.security.exception.ApiAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Aspect
@Component
public class PermissionAspect {

    @Before("@annotation(apiPermission)")
    private void hasPermission(JoinPoint joinPoint, ApiPermission apiPermission) throws ApiAccessDeniedException {
        Set<String> permissions = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        /*
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation apiAnnotation = Arrays.stream(methodSignature.getMethod()
                        .getAnnotations())
                .filter(annotation -> !annotation.annotationType().getSimpleName().equals("Permission"))
                .filter(annotation -> {
                            String annotationName  = annotation.annotationType().getSimpleName();
                            return annotationName.equals("GetMapping");
                }).findAny().orElseThrow();
        */
        Arrays.stream(apiPermission.value())
                .filter(permissions::contains)
                .findAny().orElseThrow(() -> new ApiAccessDeniedException("Access Denied"));

    }
}
