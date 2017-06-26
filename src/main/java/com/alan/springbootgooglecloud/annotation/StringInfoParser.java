package com.alan.springbootgooglecloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 17:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringInfoParser {

    /**
     * Position where starts the value.
     *
     * @return Position.
     */
    int start() default 0;

    /**
     * Width for field.
     *
     * @return Width.
     */
    int width() default 0;

    /**
     * Order of field.
     *
     * @return Order.
     */
    int order() default 0;

    /**
     * Format which will be applied to field.
     *
     * @return Format.
     */
    String format() default "";
}
