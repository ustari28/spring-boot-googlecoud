package com.alan.springbootgooglecloud.parser;

import com.alan.springbootgooglecloud.annotation.StringInfoParser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 17:07
 */
@Slf4j
public class ParserStringJson {

    /**
     * @param string String to parse.
     * @param clazz  Class.
     * @param <T>    Type object.
     *
     * @return New Object with values.
     */
    public static <T> T parserString(final String string, final Class<T> clazz) {
        int level = 0;
        try {
            return parserString(string, clazz, level);
        } catch (ClassNotFoundException e) {
            log.error(e.toString());
        } catch (NoSuchMethodException e) {
            log.error(e.toString());
        } catch (IllegalAccessException e) {
            log.error(e.toString());
        } catch (InvocationTargetException e) {
            log.error(e.toString());
        } catch (InstantiationException e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * Private method which reads a json from a plane string.
     *
     * @param string String to parse.
     * @param clazz  Class.
     * @param level  Level.
     *
     * @return new Object with values.
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    static <T> T parserString(final String string, final Class<T> clazz, int level) throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> cons = clazz.getConstructor();
        Object obj = cons.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(StringInfoParser.class)) {
                StringInfoParser annotation = field.getAnnotation(StringInfoParser.class);
                int start = annotation.start();
                int width = annotation.width();
                try {
                    String value = string.substring(start, start + width);
                    boolean accessible = field.isAccessible();
                    field.setAccessible(Boolean.TRUE);
                    switch (field.getType().getName()) {
                        case "java.lang.String":
                            field.set(obj, value);
                            break;
                        case "java.lang.Integer":
                            field.set(obj, Integer.valueOf(value));
                            break;
                        case "java.lang.Long":
                            field.set(obj, Long.valueOf(value));
                            break;
                        case "java.lang.Float":
                            field.set(obj, Float.valueOf(value));
                            break;
                        case "java.lang.Double":
                            field.set(obj, Double.valueOf(value));
                            break;
                        default:
                            log.warn("Type isn't supported : %s", field.getType().getName());
                            break;
                    }
                    field.setAccessible(accessible);
                } catch (StringIndexOutOfBoundsException e) {
                    log.error(e.toString() + ":" + field.getName() + ":" + start + ":" + width);
                } catch (NumberFormatException e) {
                    log.error(e.toString() + ":" + field.getName() + ":" + start + ":" + width);
                }
            } else if (!field.getType().isPrimitive()) {
                boolean accessible = field.isAccessible();
                field.setAccessible(Boolean.TRUE);
                field.set(obj, parserString(string, field.getType(), level));
                field.setAccessible(accessible);
            } else {
                log.warn("Field won't parsed %s ", field.getName());
            }
        }
        return (T) obj;
    }
}
