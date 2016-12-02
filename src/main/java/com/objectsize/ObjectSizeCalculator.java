package com.objectsize;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the Object size. This class provide a public method
 * calculateObjectSize which calculates the size of an Object.
 *
 * @author Sandeep Jindal, Kuldeep Singh Virdi, Rajiv Goyal
 */
@SuppressWarnings("rawtypes")
public class ObjectSizeCalculator {

    private List<Integer> listObjects = new ArrayList<Integer>();
    private List<StaticData> staticObjects = new ArrayList<StaticData>();

    /**
     * Calculates and returns Object size in Bytes.
     *
     * @param object
     *            Object whose size needs to be calculated.
     * @return BigDecimal Size in Bytes
     * @throws ObjectSizeException
     */
    public BigDecimal calculateObjectSize(Object object)
            throws ObjectSizeException {
        FieldsCounter fieldsCounter = new FieldsCounter();
        getObjectSize(object, fieldsCounter);
        BigDecimal size = CalculateSizeHelper.calcuateSize(fieldsCounter);
        BigDecimal sizeInKB = CalculateSizeHelper.getSizeInKB(size);
        System.out.println("Size is :" + sizeInKB + " KB");
        System.out.println(listObjects);
        System.out.println(staticObjects.size());
        return size;
    }

    /**
     * Method that calculates size and adds the fields into listObjects.
     *
     * @param object
     *            Object whose size needs to be calculated.
     * @param fieldsCounter
     * @throws Exception
     */
	private void getObjectSize(Object object, FieldsCounter fieldsCounter)
            throws ObjectSizeException {
        
        if (object == null) {
            return;
        }

        System.out.println(object.getClass() + " " + object);

        if (listObjects.contains(System.identityHashCode(object))) {
            return;
        }
        listObjects.add(System.identityHashCode(object));

        Class objectClassType = object.getClass();
        while (true) {
            Field[] fields = objectClassType.getDeclaredFields();

            if (fields == null) {
                return;
            }

// This is done, because innner object can be Integer/Character/etc
// this would not be used anymore since Elemenet Type is premitive
// only

            int objectElementType = findType(objectClassType);
            if (objectElementType != -1) {
                fieldsCounter.increment(objectElementType);
                return;
            }

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                field.setAccessible(true);
                Class fieldType = field.getType();
                int fieldElementType = findType(fieldType);

                if (isStatic(objectClassType, field)) {
                    continue;
                }
                try {
                    if (fieldElementType != -1) {
                        fieldsCounter.increment(fieldElementType);
                    } else if (fieldType.isArray()) {
                        if (field.get(object) == null) {
                            continue;
                        }

                        int length = Array.getLength(field.get(object));

                        int arrayElementType = findType(fieldType.getComponentType());
                        if (arrayElementType != -1) {
                            fieldsCounter.increment(arrayElementType, length);
                        } else {
                            for (int z = 0; z < length; z++) {
                                Object arrayObject = Array.get(field.get(object), z);
                                if (arrayObject != null) {
                                    getObjectSize(arrayObject, fieldsCounter);
                                }
                            }
                        }
                    } else {
                        getObjectSize(field.get(object), fieldsCounter);
                    }
                } catch (IllegalAccessException iea) {
                    throw new ObjectSizeException();
                }
            }

            if (objectClassType.getSuperclass() != null) {
                objectClassType = objectClassType.getSuperclass();
            } else {
                break;
            }
        }
    }

    /**
     * Returns the type of field for a Class.
     *
     * @param fieldType
     *            Integer
     * @return
     */
    private int findType(Class fieldType) {
        int elementType = -1;

        if (fieldType == byte.class) {
            elementType = FieldsCounter.BYTE;
        } else if (fieldType == short.class) {
            elementType = FieldsCounter.SHORT;
        } else if (fieldType == int.class) {
            elementType = FieldsCounter.INT;
        } else if (fieldType == long.class) {
            elementType = FieldsCounter.LONG;
        } else if (fieldType == char.class) {
            elementType = FieldsCounter.CHAR;
        } else if (fieldType == float.class) {
            elementType = FieldsCounter.FLOAT;
        } else if (fieldType == double.class) {
            elementType = FieldsCounter.DOUBLE;
        } else if (fieldType == boolean.class) {
            elementType = FieldsCounter.BOOLEAN;
        }

        return elementType;
    }

    /**
     * Static fields needs to be counted only once thus special handling is
     * required for the same.
     *
     * @param classStatic
     *            Class
     * @param field
     *            Field
     * @return boolean
     */
    private boolean isStatic(Class classStatic, Field field) {
        if (isStatic(field.getModifiers())) {
            StaticData staticData = new StaticData(classStatic.getName(), field.getName());
            if (staticObjects.contains(staticData)) {                
                return true;
            } else {
                staticObjects.add(staticData);
                return false;
            }
        }
        return false;
    }

    /**
     * Returns if the modifier is static.
     *
     * @param modifiers
     * @return
     */
    private boolean isStatic(int modifiers) {
        return (modifiers & Modifier.STATIC) != 0;
    }
}
