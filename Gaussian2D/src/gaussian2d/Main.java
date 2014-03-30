package gaussian2d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    
        //public static final String input = "src/image/reflection.png";
    private static final String sInput = "src/image/Lenna.png";
    private static final String sReflect = "src/image/Reflection.png";
    private static final String output = "src/image/program1.png";

    private static final int padding_x = 15; // x must be odd integer
    private static final int padding_y = 15; // y must be odd integer
                    
    /*--------------------------------------------------------------------------------------------*/    
    public static void main(String[] args) throws IOException {
        
        /*--------------- Reflection Padding --------------------*/
        int[][] iimage = ImageRead(sInput);
        Reflection ref = new Reflection();
        int[][] oimage = ref.conv(iimage, padding_x, padding_y);
        ImageWrite(oimage, sReflect);
                
        /*---------------- Gausian 2D --------------------------*/
        Gaussian2D g2d = new Gaussian2D();
        int[][] source = ImageRead(sReflect);
        float[][] kernel = g2d.kernel(1.6f, 15, false);
        int[][] paddedImg = g2d.convolve(source, kernel);
        
        /*-------------- Remove Reflection Padding --------------*/
        int[][] outImg = ref.ScaleDown(paddedImg, padding_x, padding_x);        
        ImageWrite(outImg, output);
        
        /*-------------------- Statistics -----------------------*/
        Statistics stat = new Statistics(output);
        System.out.println("\n\n" + output);
        System.out.format("Mean     : %.3f\n\n", +stat.getMean());

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
