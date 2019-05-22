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
		
		//connecting the application to the processing core!
		PApplet.main("UsingProcessingModifications");
	}
	
	//global variables to be used throughout the processing functions
	int[][] pal;
	int numberColors = 11;
	PImage img;
	PImage imgUntouched;
	String imageStr = "./Images/IMG_8305.JPG";
	
	
	public int[][] thinArray(int[][] arr){
		
		int distanceToPrune = 10;
		int removed = 0;
		if(arr.length <= 10)
			return arr;
		
		do {
			System.out.println("iterating..");
			for(int i = 0; i < arr.length; i++) {
				int r = arr[i][0];
				int g = arr[i][1];
				int b = arr[i][2];
				//System.out.println(r + " " + g + " " + b);
				if(r == 255 &&  g == 255 && b == 255)
					continue;
				
				//System.out.println("inside the loop");
				for(int compare = i+1; compare < arr.length; compare++) {
					int thisr = arr[compare][0];
					int thisg = arr[compare][1];
					int thisb = arr[compare][2];
					//System.out.println(thisr + " " + thisg + " " + thisb);
					
					if(thisr == 255 && thisg == 255 && thisb == 255)
						;
					else {
						double distance = Math.sqrt(
											Math.pow(r-thisr, 2) +
											Math.pow(g-thisg, 2) +
											Math.pow(b-thisb, 2) 
										);
						//System.out.println("Calculated distance: " + distance);
						if(distance <= distanceToPrune && (arr.length - removed) > 10) {
							arr[compare][0] = 255;
							arr[compare][1] = 255;
							arr[compare][2] = 255;
							
							removed++;
						}
					}
				}
			}
			distanceToPrune += 10;
		}while(arr.length - removed > 10);
		return arr;
	}
	public void settings(){
		//loading the image and obtaining the palette
		BufferedImage source = null;
		try {
			source = ImageIO.read(new File(imageStr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//pal = ColorThief.getPalette(source, numberColors);
		pal = ColorThief.getPalette(source, numberColors, 1, true);
		
		pal = thinArray(pal);
		
		int[][] finalColorSet = new int[10][3];
		
		int count = 0;
		for(int i = 0; i < pal.length; i++) {
			if(pal[i][0] + pal[i][1] + pal[i][2] != (255*3)) {
				finalColorSet[count][0] = pal[i][0];
				finalColorSet[count][1] = pal[i][1];
				finalColorSet[count][2] = pal[i][2];
				count++;
			}
				
		}
		
		pal = finalColorSet;
		
		for(int[] a : pal) {
			for(int i : a) {
				System.out.print(i + " " );
			}
			System.out.println();
		}
		//now loading the image for Processing to use
		img = loadImage(imageStr);
		imgUntouched = loadImage(imageStr);
		
		
		System.out.println(img.height + " " + img.width);
		
		//img.resize(1080,0);
		
		if(img.width >= img.height)
		{
			img.resize(1080-50, 0);
			size(1080, (int)(img.height*1.25));
		}
		else {
			img.resize(0, 1080-50);
			size( (int)(img.width*1.25), 1080);
		}
			
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
		
		
		//resizing the processing window
		
    }

    public void setup(){
        //fill(120,50,240);
    		System.out.println(width/pal.length);
    }

    
    public void draw(){
    		noLoop();
    		
    		//give the whole screen a white background
    		fill(255);
    		rect(-10, -10, width+10, height+10);
    		
    		//resize the image
    		//img.resize(width-50, 0);
    		
    		if(img.width > img.height)
    			image(img, 25, 15);
    		else
    			image(img, 15, 25);
    		
    		//fill(255);
    		//rect(0, img.height-15, width, height-img.height);
    		
    		//add the color palette to the bottom of the image
    		System.out.println(pal.length);

    		for(int i = 0; i < pal.length; i++) {
    			fill(pal[i][0], pal[i][1], pal[i][2]);
    			
    			//the width of the image is equal to:
    			// total width - 50 (buffer on the side)
    			// this minus 10 * the number of spaces there will be
    			// divide by the length
//    			int thisWidth = (width-(10*(pal.length-1))-50)/pal.length;
//    			rect(25+((thisWidth+10)*i), (img.height+15)+10, thisWidth, (height-(img.height+15))-25);
    			
    			if(img.width > img.height) {
	    			int rectangleWidth =  (width-(10*(pal.length-1))-50)/pal.length;
	    			int xPosition = 25+((rectangleWidth+10)*i);
	    			
	    			int rectangleHeight = (height-(img.height+15))-25;
	    			int yPosition = (img.height+15)+10;
    			
	    			rect(xPosition, yPosition, rectangleWidth, rectangleHeight);
    			}
    			else {
    				int rectangleWidth = (width-(img.width+15)) - 25;
	    			int xPosition = (img.width+15) + 10;
	    			
	    			int rectangleHeight = (height-(10*(pal.length-1))-50)/pal.length;
	    			int yPosition = 25+((rectangleHeight+10)*i);
	    			
	    			rect(xPosition, yPosition, rectangleWidth, rectangleHeight);
    			}
    		}
//    		for(int i = 0; i < pal.length; i++) {
//    			fill(pal[i][0], pal[i][1], pal[i][2]);
//    			//System.out.println((width/pal.length) * i );
//    			if(i == pal.length-1) {
//    				rect( (width/pal.length) * i, img.height + 30 , (width/pal.length)+(width-(width/pal.length)), height-img.height);
//    			}
//    			else {
//    				rect( (width/pal.length) * i, img.height + 30 , (width/pal.length), height-img.height);
//    			}
//    		}
    		
 
    		save("test.jpg");

    }
}
