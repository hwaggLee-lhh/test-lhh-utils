package com.utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class UtilsQrCode {
	
	private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("C:\\Users\\huage\\Desktop\\图片\\"+new Date().getTime()+".png");
        encode("http://www.baidu.com", file, BarcodeFormat.QR_CODE, 200, 200);
    }
    
    /**
     * @param contents
     * @param file
     * @param format
     * @param width
     * @param height
     */
    public static  void encode(String contents, File file, BarcodeFormat format, int width, int height) {
        try {
        	contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1"); 
        	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        	hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height,hints);
        	BufferedImage image = toBufferedImage(bitMatrix);
            writeToFile(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static byte[] imgToBytes(BufferedImage image){
    	if( image == null ) return new byte[0];
    	byte[] data = ((DataBufferByte) image.getData().getDataBuffer()).getData();
    	return data;
    }
    

    public static void writeToFile(BufferedImage image, String format, File file) throws IOException {
    	if( image == null || file == null ) return;
        ImageIO.write(image, format, file);
    }

    
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }




}
