package com.acefet.blog.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			resize(new File("d:\\java\\att\\tmpFiles\\2014-06-25_200233.jpg"),
//					new File("d:\\java\\att\\tmpFiles\\small_2014-06-25_200233.jpg"),
//					200, 1f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		File file = new File("d:\\java\\att\\tmpFiles\\2014-06-25_200233.jpg");
		System.out.println(file.length());
	}
	
	public static File resize(byte[] inputbs, File resizedFile,  
            int newWidth, float quality) throws IOException { 
		ImageIcon ii = new ImageIcon(inputbs);
		return resize(ii, resizedFile, newWidth, quality);
	}
	
	public static File resize(File originalFile, File resizedFile,  
            int newWidth, float quality) throws IOException { 
		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		return resize(ii, resizedFile, newWidth, quality);
	}
	
	public static File resize(ImageIcon ii, File resizedFile,  
            int newWidth, float quality) throws IOException {  
  
        if (quality > 1) {  
            throw new IllegalArgumentException(  
                    "Quality has to be between 0 and 1");  
        }
        String filepath = resizedFile.getParent();
        File direct = new File(filepath);
        if(!direct.exists()) {
        	direct.mkdirs();
        }
        if(!resizedFile.exists()) {
        	resizedFile.createNewFile();
        }
        
        //ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
        Image i = ii.getImage();  
        Image resizedImage = null;  
  
        int iWidth = i.getWidth(null);  
        int iHeight = i.getHeight(null);  
  
        if (iWidth > iHeight) {  
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)  
                    / iWidth, Image.SCALE_SMOOTH);  
        } else {  
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,  
                    newWidth, Image.SCALE_SMOOTH);  
        }  
  
        // This code ensures that all the pixels in the image are loaded.  
        Image temp = new ImageIcon(resizedImage).getImage();  
  
        // Create the buffered image.  
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
  
        // Copy image to buffered image.  
        Graphics g = bufferedImage.createGraphics();  
  
        // Clear background and paint the image.  
        g.setColor(Color.white);  
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
        g.drawImage(temp, 0, 0, null);  
        g.dispose();  
  
        // Soften.  
        float softenFactor = 0.05f;  
        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
        Kernel kernel = new Kernel(3, 3, softenArray);  
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
        bufferedImage = cOp.filter(bufferedImage, null);  
  
        // Write the jpeg to a userFile.
        FileOutputStream out = new FileOutputStream(resizedFile);
  
        // Encodes image as a JPEG data stream  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        JPEGEncodeParam param = encoder
                .getDefaultJPEGEncodeParam(bufferedImage);

        param.setQuality(quality, true);

        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
        log.info("create new userFile:"+resizedFile);
        
        return resizedFile;
    }

}
