package com.objectsize;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * This class provides Sample of how to use ObjectSizeCalculator.
 *
 * @author Sandeep Jindal, Kuldeep Singh Virdi, Rajiv Goyal
 *
 */
public class SampleApp {

    public ArrayList<String> list;

    public static void main(String args[]) {
        BigDecimal b = new BigDecimal(1000);
        SampleApp sampleApp = new SampleApp();

// Creates instance of ObjectSizeCalculator.
        ObjectSizeCalculator coz = new ObjectSizeCalculator();

        try {
            System.out.println("Size of b: " + coz.calculateObjectSize(b) + " bytes");

            BigDecimal sizeInBytes = coz.calculateObjectSize(sampleApp);
            System.out.println("Size of SampleApp: " + sizeInBytes + " bytes");

            System.out.println("Size of SampleApp: " + CalculateSizeHelper.getSizeInKB(sizeInBytes) + " KB");
            System.out.println("Size of SampleApp: " + CalculateSizeHelper.getSizeInMB(
                    coz.calculateObjectSize(sizeInBytes)).toPlainString() + " MB");
        } catch (ObjectSizeException e) {
            e.printStackTrace();
        }
    }

    public SampleApp() {
        list = new ArrayList<String>();
        list.add("Calculate Object Size1");
        list.add("Calculate Object Size2");
        list.add("Calculate Object Size3");
    }
}
