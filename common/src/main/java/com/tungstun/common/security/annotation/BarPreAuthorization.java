package com.tungstun.common.security.annotation;

import java.lang.annotation.*;

/**
 * Methods annotated with {@code @BarPreAuthorization} are authenticated and authorized before method itself is invoked.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BarPreAuthorization {
    /**
     * Defines static id value or expression with location of id value.<br>
     * Id value is the id of the bar to check authorization against.<br><br>
     * Types of notations with examples: <br>
     * <ul>
     * <li>Static id value<br></li>
     * <pre>
     *     &#064;BarPreAuthorization(id  = "1234")
     *     public void func() { ... }
     * </pre>
     *
     * <li>Expression with the name of the parameter to be used as id value (prefixed with #)<br></li>
     *    <pre>
     *        &#064;BarPreAuthorization(id = "#id")
     *        public void func(Long id) { ... }
     *    </pre>
     *
     * <li>Expression with the name of the parameter and the name of the field inside the parameter's class (prefixed with #, separated with .)</li>
     *    <pre>
     *        &#064;BarPreAuthorization(id = "#someClass.id")
     *        public void func(DataTransferObject someClass) { ... }
     *
     *        public class DataTransferObject {
     *             private Long id;
     *        }
     *    </pre>
     * </ul>
     *
     * @return The id or id parameter name of the bar to verify authorization with
     */
    String id();

    /**
     * Defines a string array of user roles that are authorized to invoke the annotated method.<br>
     * If no roles are defined, an empty array will be returned by default.<br>
     * When an empty array is returned, the user will only need to be authenticated, but won't need specific
     * authorization to invoke the method.<br>
     *
     * @return A string array of user roles
     */
    String[] roles() default {};
}
