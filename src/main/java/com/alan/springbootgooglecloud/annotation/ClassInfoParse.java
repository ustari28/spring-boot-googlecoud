package com.alan.springbootgooglecloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alan Dávila<br>
 *         27 jun. 2017 16:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassInfoParse {
    /**
     * Field separator.
     *
     * @return Separator.
     */
    String separtor() default "|";

}
