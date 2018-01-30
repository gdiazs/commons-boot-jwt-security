package io.gdiazs.commons.boot.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import io.gdiazs.commons.boot.security.CommonsSecurity;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
@Documented
@Import(CommonsSecurity.class)
public @interface EnableCommonsJwtSecurity {

}
