package orm.mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {
    Class<?> targetEntity();
    String[] columns();
    String name() default "";
    String mappedBy() default "";
}
