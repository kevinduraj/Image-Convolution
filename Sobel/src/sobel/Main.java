package sobel;

import java.io.IOException;

public class Main {

    private static final String sInput = "src/image/Lenna.png";   
    
    /*--------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {
        
        /* Sobel */
        Sobel sobel = new Sobel();
        sobel.process(sInput);
        
        /* Statistics */
        Statistics stat1 = new Statistics("src/image/Lenna_Mag.png");
        System.out.println("\n\n" + "src/image/Lenna_Mag.png");
        System.out.format("Mean     : %.3f\n\n", + stat1.getMean());

        /* Statistics */
        Statistics stat2 = new Statistics("src/image/Lenna_Dir.png");
        System.out.println("\n\n" + "src/image/Lenna_Mag.png");
        System.out.format("Mean     : %.3f\n\n", + stat2.getMean());
        
    }
    /*--------------------------------------------------------------------------------------------*/     
}
