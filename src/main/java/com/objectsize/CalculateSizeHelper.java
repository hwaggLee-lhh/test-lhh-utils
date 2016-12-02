package com.objectsize;

import java.math.BigDecimal;

/**
 *
 * Provide utility functions for Object Size Calculator.
 *
 * @author Sandeep Jindal, Kuldeep Singh Virdi, Rajiv Goyal
 */
public class CalculateSizeHelper {

    public static BigDecimal COVERSION_FACTOR = new BigDecimal(1024);
    public static int DIVISION_PRECISION = 38;

    /**
     * Calculates the size of Fields passed.
     *
     * @param fieldsCounter
     * @return BigDecimal Size in Bypes
     */
    protected static BigDecimal calcuateSize(FieldsCounter fieldsCounter) {
        BigDecimal size = BigDecimal.ZERO;
        BigDecimal fieldSize = null;

// Boolean
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.BOOLEAN) * (double) 1 / 8);
        size = size.add(fieldSize);

// Char
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.CHAR) * 2);
        size = size.add(fieldSize);

// Byte
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.BYTE) * 1);
        size = size.add(fieldSize);

// Short
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.SHORT) * 2);
        size = size.add(fieldSize);

// Int
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.INT) * 4);
        size = size.add(fieldSize);

// Long
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.LONG) * 8);
        size = size.add(fieldSize);

// Float
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.FLOAT) * 4);
        size = size.add(fieldSize);

// Double
        fieldSize = new BigDecimal(fieldsCounter.getNumberOfCharacter(FieldsCounter.DOUBLE) * 8);
        size = size.add(fieldSize);

        return size;
    }

    /**
     * Returns the size in Kilo Bypes (KB)
     *
     * @param sizeInBytes
     *            Original Size in Bytes.
     * @return Size in KB
     */
    public static BigDecimal getSizeInKB(BigDecimal sizeInBytes) {
        return sizeInBytes.divide(COVERSION_FACTOR, DIVISION_PRECISION,
                BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Returns the size in MegaBytes (MB)
     *
     * @param sizeInBytes
     *            Original Size in Bytes
     * @return Size in MB
     */
    public static BigDecimal getSizeInMB(BigDecimal sizeInBytes) {
        return getSizeInKB(getSizeInKB(sizeInBytes));
    }
}
