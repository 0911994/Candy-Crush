package candycrush;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Candy {
    private int candyType;
    public static int candyTypes = Panel.numberOfCandyTypes;
    public static BufferedImage[] candyImages = new BufferedImage[candyTypes];
    
    public void setCandyType(int candyType) {
        this.candyType = candyType;
    }

    public static boolean loadImages()
    {
        try
        {
            for(int i=0; i < candyTypes; i++)
                candyImages[i] =  ImageIO.read(new File("src/images/" + (i+1) + ".png"));
            return true;
        } catch (IOException e) {
            return false;
        }
        
    }
    
    public int getCandyType() {
        return candyType;
    }
    public Candy(int candyType) {
        this.candyType = candyType;
    }
}
