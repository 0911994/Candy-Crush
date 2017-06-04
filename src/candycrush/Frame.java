package candycrush;

import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

class Frame extends JFrame{
    private static final int frameHeight = 500;
    private static final int frameWidth = 500;
    private Panel panel = new Panel();
    public static int getFrameHeight() {
        return frameHeight;
    }

    public static int getFrameWidth() {
        return frameWidth;
    }

    public Frame() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setResizable(false);                        //da se ne moze mjenjati velicina  
        setVisible(true);                           //da je vidljivo 
        setDefaultCloseOperation(EXIT_ON_CLOSE);    // da se na X gasi
        setLocation(0, 0);
        
        add(panel);
        setContentPane(panel);
        pack();
    }
    
    
}
