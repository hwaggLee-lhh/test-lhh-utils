package com.objectsize;

import java.util.ArrayList;

/**
 * FieldCounter class is a Value Object class that stores the number of each
 * data type, Index of the arraylist being the data type.
 *
 * @author Sandeep Jindal, Kuldeep Singh Virdi, Rajiv Goyal
 */
class FieldsCounter {

    public static final int BYTE = 0;
    public static final int SHORT = 1;
    public static final int INT = 2;
    public static final int LONG = 3;
    public static final int FLOAT = 4;
    public static final int DOUBLE = 5;
    public static final int BOOLEAN = 6;
    public static final int CHAR = 7;
    private ArrayList<Long> sizeObject = new ArrayList<Long>(8);

    public FieldsCounter() {
        for (int i = 0; i < 8; i++) {
            sizeObject.add(i, new Long(0));
        }
    }

    /**
     * Increments the element pass by 1.
     *
     * @param element
     */
    public void increment(int element) {
        increment(element, 1);
    }

    /**
     * Incement the element pass by the length.
     *
     * @param element
     * @param length
     */
    public void increment(int element, int length) {
        Long size = sizeObject.get(element);
        if (size == null) {
            size = new Long(0);
        }
        size = size+length;
        sizeObject.set(element, size);
    }

    public String toString() {
        return " FieldCounter:" + sizeObject;
    }

    public Long getNumberOfCharacter(int field) {
        return sizeObject.get(field);
    }
}
