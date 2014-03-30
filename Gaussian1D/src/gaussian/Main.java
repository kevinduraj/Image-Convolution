package gaussian;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    private static final String sInput = "src/image/Lenna.png";
    private static final String sReflect = "src/image/Reflection.png";
    private static final String output = "src/image/program2.png";

    private static final int padding_x = 15; // x must be odd integer
    private static final int padding_y = 15; // y must be odd integer


    /*--------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {

        /*--------------- Reflection Padding --------------------*/
        int[][] iimage = ImageRead(sInput);
        Reflection ref = new Reflection();
        int[][] oimage = ref.conv(iimage, padding_x, padding_y);
        ImageWrite(oimage, sReflect);

        /*----------------- Gaussian Blur 1D ---------------------*/
        int[][] paddedImg = Gaussian1D();
        
        /*-------------- Remove Reflection Padding --------------*/
        int[][] outImg = ref.ScaleDown(paddedImg, padding_x, padding_x);        
        ImageWrite(outImg, output);
        
        /*-------------------- Statistics -----------------------*/
        Statistics stat = new Statistics(output);
        System.out.println("\n\n" + output);
        System.out.format("Mean     : %.3f\n\n", +stat.getMean());

    }
    /*--------------------------------------------------------------------------------------------*/

    //private static String Gaussian1D() throws IOException {
    private static int[][] Gaussian1D() throws IOException {

        String gaussian1 = "src/image/gaussian1.png";

        BufferedImage source = ImageIO.read(new File(sReflect));
        BufferedImage destination = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        System.out.println("Computing Gaussian Kernel");
        GaussianBlur gb = new GaussianBlur();
        ConvolveOp cop = gb.getGaussianBlurFilter(15, 1.6f, true);

        System.out.println("Computing Gaussian Horizontal Blur");
        cop.filter(source, destination);

        // Writing to a file
        File outputfile = new File(gaussian1);
        ImageIO.write(destination, "png", outputfile);

        source = ImageIO.read(new File(gaussian1));
        destination = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        cop = gb.getGaussianBlurFilter(15, 1.6f, false);

        System.out.println("Computing Gaussian Vertical Blur");
        cop.filter(source, destination);

        /*---- Writing to a file ----*/
        //outputfile = new File("src/image/gaussian2.png");
        //ImageIO.write(dest, "png", outputfile);            
        //return output;
        
        /*----------- Convert BufferedImage green into Integer array -------------*/
        int[][] array = new int[destination.getHeight()][destination.getWidth()];

        for (int i = 0; i < destination.getHeight(); i++) {
            for (int j = 0; j < destination.getWidth(); j++) {
                array[j][i] = new Color(destination.getRGB(i, j)).getGreen();
            }
        }
        
        return array;
    }
    /*--------------------------------------------------------------------------------------------*/

    public static int[][] ImageRead(String filename) throws IOException {

        File infile = new File(filename);
        BufferedImage bi = ImageIO.read(infile);
        
        int red[][] = new int[bi.getHeight()][bi.getWidth()];
        int grn[][] = new int[bi.getHeight()][bi.getWidth()];
        int blu[][] = new int[bi.getHeight()][bi.getWidth()];
        
        for (int i = 0; i < red.length; ++i) {
            for (int j = 0; j < red[i].length; ++j) {
                red[i][j] = bi.getRGB(j, i) >> 16 & 0xFF;
                grn[i][j] = bi.getRGB(j, i) >> 8 & 0xFF;
                blu[i][j] = bi.getRGB(j, i) & 0xFF;
            }
        }
        return grn;
    }
    /*--------------------------------------------------------------------------------------------*/

    public static void ImageWrite(int img[][], String filename) throws IOException {

        BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < bi.getHeight(); ++i) {
            for (int j = 0; j < bi.getWidth(); ++j) {
                int val = img[i][j];
                int pixel = (val << 16) | (val << 8) | (val);
                bi.setRGB(j, i, pixel);
            }
        }

        File outputfile = new File(filename);
        ImageIO.write(bi, "png", outputfile);
    }
    /*--------------------------------------------------------------------------------------------*/
}
