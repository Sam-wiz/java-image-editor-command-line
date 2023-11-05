import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.*;
import java.lang.Math;
import java.io.IOException;

public class imgeditor
{
	public static BufferedImage greyscale(BufferedImage inpimg)
	{
		int height = inpimg.getHeight();
        int width = inpimg.getWidth();
        BufferedImage outimg = new BufferedImage(width , height , BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0 ; i<height ; i++){

            for(int j=0 ; j<width ; j++){

                outimg.setRGB(j,i, inpimg.getRGB(j,i));

            }
        }
        return outimg;
	}
	public static BufferedImage Blur(BufferedImage inpimg, int amtOfBlur)
	{
		int height = inpimg.getHeight();
        int width = inpimg.getWidth();
        BufferedImage outimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height -amtOfBlur; i +=amtOfBlur)
        {
            for(int j=0; j<width- amtOfBlur; j +=amtOfBlur)
            {
                BufferedImage tempImage = new BufferedImage(amtOfBlur, amtOfBlur, BufferedImage.TYPE_INT_RGB);
                int red_sum = 0;
                int blue_sum = 0;
                int green_sum = 0;

                for(int m = 0; m<amtOfBlur; m++)
                {
                    for( int n = 0; n<amtOfBlur; n++)
                    {
                        Color pixel = new Color(inpimg.getRGB(j+n, i+m));
                        red_sum += pixel.getRed(); 
                        blue_sum +=pixel.getBlue();
                        green_sum += pixel.getGreen();
                    }
                }
                int avg_red=red_sum/(amtOfBlur*amtOfBlur);
                int avg_blue=blue_sum/(amtOfBlur*amtOfBlur);
                int avg_green=green_sum/(amtOfBlur*amtOfBlur);

                for(int k=0; k<amtOfBlur; k++)
                {
                    for(int l=0; l<amtOfBlur; l++)
                    {
                        Color newPixel= new Color(avg_red,avg_green,avg_blue);
                        tempImage.setRGB(l , k , newPixel.getRGB());
                    }
                }

                for(int m=0; m<amtOfBlur; m++)
                {
                    for(int n=0; n<amtOfBlur; n++)
                    {
                        outimg.setRGB(j+n,i+m,tempImage.getRGB(n,m));
                    }
                }

            }
        }
        return outimg;
	}
	public static BufferedImage colorinversion(BufferedImage inpimg)
	{
	    int height = inpimg.getHeight();
        int width = inpimg.getWidth();

        BufferedImage outimg = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        for(int i=0 ; i<height ; i++){

            for(int j=0 ; j<width ; j++){

                Color pixel = new Color(inpimg.getRGB(j,i));

                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                red = 255-red;
                green = 255-green;
                blue = 255-blue;

                Color newPixel = new Color(red , green , blue);

                outimg.setRGB(j , i , newPixel.getRGB());

            }
        }
        return outimg;
	}
	public static BufferedImage crop(BufferedImage inpimg, int choice)
    {
		Scanner in = new Scanner(System.in);
		int height = inpimg.getHeight();
	  	int width = inpimg.getWidth();
	  	BufferedImage outimg = null;
		if(choice==1)
		{
			System.out.println("Please enter the radius of circle ");
				int radius = in.nextInt();
	        	int centreRow = height/2;
	            int centreColumn = width/2;
		         outimg = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
		        Color blackColor = new Color(0 ,0 ,0);
		        for(int i=0 ; i<height ; i++)
		        {
		            for(int j=0 ; j<width ; j++)
		            {
		                if((j-centreColumn)*(j-centreColumn)+(i-centreRow)*(i-centreRow) > radius*radius)
		                {
		                    outimg.setRGB(j, i , blackColor.getRGB());
		                }
		                else
		                {
		                    outimg.setRGB(j , i , inpimg.getRGB(j,i));
		                }
		            }
		        }
		}
		else if(choice == 2)
		{
			System.out.println("Please enter the side length of square");
		    	int length = in.nextInt();
	        	int size = Math.min(length,length);
	        	int x1 = (width - size) / 2;
	            int y1 = (height - size) / 2;
	             outimg = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
	            for (int i = 0; i < size; i++) {
	                for (int j = 0; j < size; j++) {
	                    int rgb = inpimg.getRGB(x1 + i, y1 + j);
	                    outimg.setRGB(i, j, rgb);
	                }
	            }
		}
		else if(choice == 3)
		{
			System.out.println("Please enter the side length and breadth of rectangle");
		    	int length1 = in.nextInt();
		    	int breadth = in.nextInt();
	        	int size1 = Math.min(length1,breadth);
	        	int x2 = (width - size1) / 2;
	            int y2 = (height - size1) / 2;
	             outimg = new BufferedImage(length1, breadth, BufferedImage.TYPE_INT_RGB);
	            for (int i = 0; i < size1; i++) {
	                for (int j = 0; j < size1; j++) {
	                    int rgb = inpimg.getRGB(x2 + i, y2 + j);
	                    outimg.setRGB(i, j, rgb);
	                }
	            }
		} 
		else 
		{
			System.out.println("Invalid choice");
			
		}
	        return outimg;
		}
	public static BufferedImage mirrorimg(BufferedImage inpimg)
	{
		int height = inpimg.getHeight();
        int width = inpimg.getWidth();

        BufferedImage outimg = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
        
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width/2 ; j++){
                Color pixel = new Color(inpimg.getRGB(j,i));
                outimg.setRGB(j,i,inpimg.getRGB(inpimg.getWidth()-1-j , i));
                outimg.setRGB(inpimg.getWidth()-1-j , i , pixel.getRGB());
                
            }
        }
        return outimg;
	}
	public static BufferedImage AntiClockwise(BufferedImage inpimg)
    {
        int height = inpimg.getHeight(null);
        int width = inpimg.getWidth(null);
        BufferedImage outimg = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for( int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                outimg.setRGB(i, j, inpimg.getRGB(j, i) );
                
            }
        }
        return outimg;
    }
    public static BufferedImage Clockwise(BufferedImage inpimg)
    {
        int height = inpimg.getHeight();
        int width = inpimg.getWidth();

        BufferedImage outimg = new BufferedImage(height , width , BufferedImage.TYPE_INT_RGB);
        
        //transpose.
        for(int i=0 ; i<width ; i++){
            for(int j=0; j<height ; j++){
                Color pixel = new Color(inpimg.getRGB(i,j));
                outimg.setRGB(j,i,pixel.getRGB());
            }
        }

        outimg = mirrorimg(outimg);

        return outimg;
    }
	public static BufferedImage contrastimg(BufferedImage inpimg , int percentage)
    {

        int height = inpimg.getHeight();
        int width = inpimg.getWidth();

        BufferedImage outimg = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        for(int i=0 ; i<height ; i++)
        {
            for(int j=0 ; j<width ; j++)
            {
                Color pixel = new Color(inpimg.getRGB(j , i));
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                if(red>127) 
                {
                	red = red + (red*percentage/100);
                }
                else 
                {
                	red = red - (red*percentage/100);
                }
                if(green>127) 
                {
                	green = green + (green*percentage/100);
                }
                else 
                {
                	green = green - (green*percentage/100);
                }
                if(blue>127) 
                {
                	blue = blue + (blue*percentage/100);
                }
                else 
                {
                	blue = blue - (blue*percentage/100);
                }
                if(red>255) 
                {
                	red=255;
                }
                if(red<0) 
                {
                	red=0;
                }
                if(green>255) 
                {
                	green=255;
                }
                if(green<0) 
                {
                	green=0;
                }
                if(blue>255) 
                {
                	blue=255;
                }
                if(blue<0) 
                {
                	blue=0;
                }

                Color newPixel = new Color(red , green , blue);

                outimg.setRGB(j ,i , newPixel.getRGB());
            }
        }
            return outimg;
    }
    public static BufferedImage edgeDetectionimg(BufferedImage inpimg)
    {

        int height = inpimg.getHeight();
        int width = inpimg.getWidth();

        BufferedImage outimg = new BufferedImage(width+5 , height+5 ,BufferedImage.TYPE_INT_RGB);

        for(int i=0 ; i<height ; i++)
        {
            for(int j=0 ; j<width ; j++)
            {

                Color pixel = new Color(inpimg.getRGB(j,i));

                outimg.setRGB(j , i , pixel.getRGB());
            }
        }

            for(int i=5 ; i<height+5 ; i++)
            {
                for(int j=5 ; j<width+5 ; j++)
                {

                    Color pixel = new Color(inpimg.getRGB(j-5 , i-5));

                    Color newPixel = new Color(outimg.getRGB(j, i));

                    int finalRed = newPixel.getRed()-pixel.getRed();
                    int finalGreen = newPixel.getGreen()-pixel.getGreen();
                    int finalBlue = newPixel.getBlue()-pixel.getBlue();

                    if(finalRed<0) 
                    {
                    	finalRed=0;
                    }
                    if(finalGreen<0) 
                    {
                    	finalGreen=0;
                    }
                    if(finalBlue<0) 
                    {
                    	finalBlue=0;
                    }

                    Color finalPixel = new Color(finalRed , finalGreen , finalBlue);

                    outimg.setRGB(j , i , finalPixel.getRGB() );
                }
            }
        return outimg;
    }
    public static BufferedImage pencilSketchimg(BufferedImage inpimg)
    {

        BufferedImage outimg;
        outimg = edgeDetectionimg(inpimg);
        outimg = colorinversion(outimg);
        return outimg;
    }
    public static BufferedImage Brightentheimg(BufferedImage inpimg , int increase)
    {
        int height = inpimg.getHeight();
        int width = inpimg.getWidth();
        BufferedImage outimg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                Color pixel = new Color(inpimg.getRGB(j, i));
                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen();
                red = red + (increase*red/100);
                blue = blue + (increase*blue/100);
                green = green + (increase*green/100);
                if(red > 255) 
                {
                	red=255;
                }
                if(blue > 255) 
                {
                	blue=255;
                }
                if(green > 255) 
                {
                	green=255;
                }
                if(red < 0) 
                {
                	red=0;
                }
                if(blue < 0) 
                {
                	blue=0;
                }
                if(green < 0) 
                {
                	green=0;
                }
                Color newPixel = new Color(red, green, blue);
                outimg.setRGB(j, i, newPixel.getRGB());

            }
        }
        return outimg;
    }
    public static BufferedImage addimg(BufferedImage inpimg)
    {
        Scanner in = new Scanner(System.in);
        int height = inpimg.getHeight();
        int width = inpimg.getWidth();
        BufferedImage outimg = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
        System.out.println("Enter the path of the image to be inserted");
        String addresss = in.nextLine();
        try
        {
            BufferedImage insertImage = ImageIO.read(new File(addresss));
        System.out.println("Define the position to insert the image(x & y)");
        int x = in.nextInt(); 
        int y = in.nextInt();
        if (x >= 0 && y >= 0 && x + insertImage.getWidth() <= width && y + insertImage.getHeight() <= height) 
        {
                Graphics2D g2d = outimg.createGraphics();
                g2d.drawImage(inpimg, 0, 0, null);
                g2d.drawImage(insertImage, x, y, null);
                g2d.dispose();
        }
        else
        {
            System.out.println("Co-ordinates out of Bound");
        }
        }
        catch(IOException e)
           {
               e.printStackTrace();
           }
        return outimg;
    }
    public static void main(String args[])
    {
        while(true)
        {

    	    Scanner in = new Scanner(System.in);
        	System.out.println("Welcome to image editor");
            System.out.println("Please input the address of the image to edit");
            String address = in.nextLine();
            File inputFile = new File(address);
            System.out.println("Choose the operation you would like to perform on the image:\n\n1 -> Change Brightness\n2 -> Convert your image to GreyScale\n3 -> Blur image\n4 -> Mirror Image \n5 -> Color Inversion\n6 -> To rotate Image AntiClockwise\n7 -> To rotate Image Clockwise\n8 -> Highlight the edges of elements in your image \n9 -> Print contrast of your image\n10 -> Print pencil sketch\n11 -> Crop your image\n12 -> Insert an image\n13 -> To insert an image over another image\n14 -> Exit");
            System.out.print("Enter choice: ");
            int choice = in.nextInt();
            if(choice == 14)
            {
                System.out.println("Thank You for Using the editor");
                break;
            }
            System.out.println();
           try{
               BufferedImage inputImage = ImageIO.read(inputFile);

   
               switch(choice){
   
                   case 1: 
                   		System.out.print("Enter the amount of brightness to be increased: ");
                   		int amnt = in.nextInt();
                   		BufferedImage BrightenedImage = Brightentheimg(inputImage,amnt);
                        File outputImage = new File("BrightenedImage.jpeg");
                        ImageIO.write(BrightenedImage , "jpeg" , outputImage);
                        System.out.println("\nThe Image have been changed and saved\n");
                        break;
                   case 2: 
                   			BufferedImage grayScale = greyscale(inputImage);
                           File grayScaleImage = new File("grayScaleImage.jpeg");
                           ImageIO.write(grayScale , "jpeg" , grayScaleImage);
                           System.out.println("\nThe Image have been changed and saved\n");
                           break;
                   case 3: 
                   		 	System.out.print("Enter the amount to blur the image: ");
                            int amount = in.nextInt();
                            BufferedImage blurred = Blur(inputImage , amount);
                            File blurredImage = new File("blurred.jpeg");
                            ImageIO.write(blurred , "jpeg" , blurredImage);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;
   
                   case 4: BufferedImage mirrored = mirrorimg(inputImage);
                           File mirroredImage = new File("mirrored.jpeg");
                           ImageIO.write(mirrored , "jpg" , mirroredImage);
                           System.out.println("\nThe Image have been changed and saved\n");
                           break;

                    case 5: BufferedImage inverted = colorinversion(inputImage);
                             File invertedImage = new File("inverted.jpeg");
                             ImageIO.write(inverted , "jpeg" , invertedImage);
                             System.out.println("\nThe Image have been changed and saved\n");
                             break;

                    case 6: BufferedImage rotatedAntiClockwise = AntiClockwise(inputImage);
                           File rotatedAntiClockwiseImage = new File("rotatedAntiClockwise.jpeg");
                           ImageIO.write(rotatedAntiClockwise , "jpeg" , rotatedAntiClockwiseImage);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;

                    case 7: BufferedImage rotatedClockwise = Clockwise(inputImage);
                           File rotatedClockwiseImage = new File("rotatedClockwise.jpeg");
                           ImageIO.write(rotatedClockwise , "jpeg" , rotatedClockwiseImage);
                           System.out.println("\nThe Image have been changed and saved\n");
                           break;

                    case 8: BufferedImage edgeDetected = edgeDetectionimg(inputImage);
                            File edgeDetectedImage = new File("edgeDetected.jpeg");
                            ImageIO.write(edgeDetected , "jpeg" , edgeDetectedImage);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;

                    case 9: System.out.print("\nBy what percentage? ");
                            int percentage = in.nextInt();
                            BufferedImage contrasted = contrastimg(inputImage , percentage);
                            File contrastedImage = new File("contrasted.jpeg");
                            ImageIO.write(contrasted , "jpeg" , contrastedImage);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;

                    case 10: BufferedImage pencilSketched = pencilSketchimg(inputImage);
                             File pencilSketchedImage = new File("pencilSketched.jpeg");
                             ImageIO.write(pencilSketched , "jpg" , pencilSketchedImage);
                             System.out.println("\nThe Image have been changed and saved\n");
                             break;
                    case 11:
                    		 System.out.println("Please Enter-:");
                    		 System.out.println("1 -> To crop image in a circle\n2 -> To crop image in square \n 3 -> To crop image in rectangle");
                    		 int ch = in.nextInt();
                    		BufferedImage cropped = crop(inputImage , ch);
                            File croppedImage = new File("cropped.jpeg");
                            ImageIO.write(cropped , "jpeg" , croppedImage);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;
                    case 12:
                            BufferedImage insertedimg = addimg(inputImage);
                            File insimg = new File("insertedimg.jpeg");
                            ImageIO.write(insertedimg , "jpeg" , insimg);
                            System.out.println("\nThe Image have been changed and saved\n");
                            break;
                   default: 
                            System.out.println("\nplease enter a valid option.\n");
               }
   
   
   
           }
           catch(IOException e)
           {
               e.printStackTrace();
           }
        System.out.println("Thank You");
        }
    }
    }

