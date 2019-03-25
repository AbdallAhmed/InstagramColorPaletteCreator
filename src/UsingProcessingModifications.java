import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import de.androidpit.colorthief.ColorThief;
import processing.core.PApplet;
import processing.core.PImage;

public class UsingProcessingModifications extends PApplet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("UsingProcessingModifications");
	}
	
	int[][] pal;
	int numberColors = 10;
	PImage img;
	PImage imgUntouched;
	String imageStr = "./Images/IMG_8558.JPG";
	
	
	public void settings(){
		BufferedImage source = null;
		try {
			source = ImageIO.read(new File(imageStr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//pal = ColorThief.getPalette(source, numberColors);
		pal = ColorThief.getPalette(source, numberColors, 1, true);
		
		
//		for(int[] a : pal) {
//			for(int i : a) {
//				System.out.print(i + " " );
//			}
//			System.out.println();
//		}
		img = loadImage(imageStr);
		imgUntouched = loadImage(imageStr);
		
		
		System.out.println(img.height + " " + img.width);
		
//		img.loadPixels();
//		
//		System.out.println(img.pixels.length);
//	
//		int[] list = Arrays.stream(img.pixels).distinct().toArray();
//		System.out.println(list.length);
		
//		for(int i = 500; i < 1000; i++) {
//			float red = img.pixels[i] >> 16 & 0xFF;
//			float green = img.pixels[i] >> 8 & 0xFF;
//			float blue = img.pixels[i] & 0xFF;
//			
//			System.out.println(red + " " + green + " " + blue);
//		}
		
		
		
        //size(100*numberColors,400);
		size(1080, 900);
    }

    public void setup(){
        //fill(120,50,240);
    	System.out.println(width/pal.length);
    }

    
    public void draw(){
    		noLoop();
    		
    		fill(255);
    		rect(-10, -10, width+10, height+10);
    		img.resize(width-50, 0);
    		image(img, 25, 15);
    		
    		//fill(255);
    		//rect(0, img.height-15, width, height-img.height);
    		for(int i = 0; i < pal.length; i++) {
    			fill(pal[i][0], pal[i][1], pal[i][2]);
    			//System.out.println((width/pal.length) * i );
    			if(i == pal.length-1) {
    				rect( (width/pal.length) * i, img.height + 30 , (width/pal.length)+(width-(width/pal.length)), height-img.height);
    			}
    			else {
    				rect( (width/pal.length) * i, img.height + 30 , (width/pal.length), height-img.height);
    			}
    		}
    		
    		save("test.jpg");
//        fill(pal[0][0], pal[0][1], pal[0][2]);
//        rect(0,0,width/5,height);
//        
//        fill(pal[1][0], pal[1][1], pal[1][2]);
//        rect(width/5,0,width/5,height);
//        
//        fill(pal[2][0], pal[2][1], pal[2][2]);
//        rect((width/5)*2,0,width/5,height);
//        
//        fill(pal[3][0], pal[3][1], pal[3][2]);
//        rect((width/5)*3,0,width/5,height);
//        
//        fill(pal[4][0], pal[4][1], pal[4][2]);
//        rect((width/5)*4,0,width/5,height);
    }
}
