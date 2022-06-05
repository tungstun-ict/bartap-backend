package com.tungstun.sharedlibrary.security;

import java.lang.annotation.*;

/**
 * Methods annotated with {@code @BarPreAuthorization} are authenticated and authorized before method itself is invoked.
 * */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BarPreAuthorization {
    /**
     * Defines the id of the bar to check authorization against.
     * Can be used to define a method parameter name to use its value as id
     * Parameter names should be prefixed with '#'. e.g. '#someId'
     * @return The id or id parameter name of the bar to verify authorization with
     * */
    String id();

    /**
     * Defines a string array of user roles that are authorized to invoke the annotated method.<br>
     * If no roles are defined, an empty array will be returned by default.<br>
     * When an empty array is returned, the user will only need to be authenticated, but won't need specific
     *      authorization to invoke the method.<br>
     * @return A string array of user roles
     * */
    String[] roles() default {};
}
