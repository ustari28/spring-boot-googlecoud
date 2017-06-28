package com.alan.springbootgooglecloud.parser;

import com.alan.springbootgooglecloud.annotation.ClassInfoParse;
import com.alan.springbootgooglecloud.annotation.StringInfoParse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Dávila<br>
 *         26 jun. 2017 17:07
 */
@Slf4j
public class ParserStringJson {


    private static List<Class> supportedClasses = new ArrayList<>();

    static {
        supportedClasses.add(String.class);
        supportedClasses.add(Integer.class);
        supportedClasses.add(Float.class);
        supportedClasses.add(Integer.class);
        supportedClasses.add(Long.class);
        supportedClasses.add(Double.class);
    }

    /**
     * @param string String to parse.
     * @param clazz  Class.
     * @param <T>    Type object.
     *
     * @return New Object with values.
     */
    public static <T> T parseString(final String string, final Class<T> clazz) {
        int level = 0;
        try {
            if (clazz.isAnnotationPresent(ClassInfoParse.class)) {
                ClassInfoParse classInfoParse = clazz.getAnnotation(ClassInfoParse.class);
                return parseFlexibleString(string, clazz, classInfoParse.separtor(), level);
            } else {
                return parseFixedString(string, clazz, level);
            }
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
    private static <T> T parseFixedString(final String string, final Class<T> clazz, int level) throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> cons = clazz.getConstructor();
        Object obj = cons.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(StringInfoParse.class)) {
                boolean accessible = field.isAccessible();
                field.setAccessible(Boolean.TRUE);
                StringInfoParse annotation = field.getAnnotation(StringInfoParse.class);
                int start = annotation.start();
                int width = annotation.width();
                if (!field.getType().isArray() && supportedClasses.contains(field.getType())) {
                    try {
                        String value = string.substring(start, start + width);
                        field.set(obj, parseField(field.getType(), value));
                    } catch (StringIndexOutOfBoundsException e) {
                        log.error(e.toString() + ":" + field.getName() + ":" + start + ":" + width);
                    }
                } else if (!field.getType().isArray() && !field.getType().isPrimitive()) {
                    String value = string.substring(start, start + width);
                    if (field.getType().isAnnotationPresent(ClassInfoParse.class)) {
                        ClassInfoParse classInfoParse = clazz.getAnnotation(ClassInfoParse.class);
                        field.set(obj, parseFlexibleString(value, field.getType(), classInfoParse.separtor(), level));
                    } else {
                        field.set(obj, parseFixedString(string, field.getType(), level));
                    }
                } else if (field.getType().isArray()) {
                    int size = annotation.size();
                    Object elements = Array.newInstance(field.getType().getComponentType(), width / size);
                    for (int i = 0; i < width / size; i++) {
                        int newStart = start + (i * size);
                        Object objArray = null;
                        try {
                            String value = string.substring(newStart, newStart + size);
                            if (supportedClasses.contains(field.getType().getComponentType())) {
                                objArray = parseField(field.getType(), value);
                            } else {
                                objArray = parseString(value, field.getType().getComponentType());
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            log.warn("it can't cut the field {}", field.getName());
                        }
                        Array.set(elements, i, objArray);
                    }
                    field.set(obj, elements);
                } else {
                    log.warn("Type isn't supported");
                }
                field.setAccessible(accessible);
            } else {
                log.warn("Field won't be parsed {} ", field.getName());
            }
        }
        return (T) obj;
    }

    /**
     * Parsing a flexible string.
     *
     * @param string    String.
     * @param clazz     Target class.
     * @param separator Separator.
     * @param level     Level.
     * @param <T>       New Object.
     *
     * @return New Object.
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static <T> T parseFlexibleString(final String string, final Class<T> clazz, final String separator, int
            level)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> cons = clazz.getConstructor();
        Object obj = cons.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        final String[] values = string.split(separator);
        for (Field field : fields) {
            if (field.isAnnotationPresent(StringInfoParse.class)) {
                boolean accessible = field.isAccessible();
                field.setAccessible(Boolean.TRUE);
                StringInfoParse annotation = field.getAnnotation(StringInfoParse.class);
                int order = annotation.order();
                String value = values[order];
                if (!field.getType().isArray() && supportedClasses.contains(field.getType())) {
                    field.set(obj, parseField(field.getType(), value));
                } else if (!field.getType().isArray() && !field.getType().isPrimitive()) {
                    if (field.getType().isAnnotationPresent(ClassInfoParse.class)) {
                        ClassInfoParse classInfoParse = clazz.getAnnotation(ClassInfoParse.class);
                        field.set(obj, parseFlexibleString(value, field.getType(), classInfoParse.separtor(), level));
                    } else {
                        field.set(obj, parseFixedString(string, field.getType(), level));
                    }
                } else if (field.getType().isArray()) {
                    int size = annotation.size();
                    Object elements = Array.newInstance(field.getType(), size);
                    for (int i = 0; i < size; i++) {
                        Object objArray = null;
                        if (supportedClasses.contains(field.getType().getComponentType())) {
                            objArray = parseField(field.getType(), value);
                        } else {
                            objArray = parseString(value, field.getType().getComponentType());
                        }
                        Array.set(elements, i, objArray);
                    }
                    field.set(obj, elements);
                } else {
                    log.warn("Type isn't supported");
                }
                field.setAccessible(accessible);
            } else {
                log.warn("Field won't be parsed {} ", field.getName());
            }
        }
        return (T) obj;
    }

    /**
     * Parsing value field.
     *
     * @param clazz Class.
     * @param value Value.
     */
    private static Object parseField(final Class<?> clazz, final String value) throws
            IllegalAccessException {
        Object obj = null;
        if (clazz.getName().contains("java.lang.String")) {
            obj = value;
        } else if (clazz.getName().contains("java.lang.Integer")) {
            obj = Integer.valueOf(value);
        } else if (clazz.getName().contains("java.lang.Long")) {
            obj = Long.valueOf(value);
        } else if (clazz.getName().contains("java.lang.Float")) {
            obj = Float.valueOf(value);
        } else if (clazz.getName().contains("java.lang.Double")) {
            obj = Double.valueOf(value);
        } else {
            log.warn("Type isn't supported : {}", clazz.getName());
        }
        return obj;
    }
}
