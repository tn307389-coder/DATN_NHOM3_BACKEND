package org.example.datn_nhom3_backend.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAction {
    String action() default "";
    String table() default "";
}
