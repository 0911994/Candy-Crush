package candycrush;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
        setResizable(false);                          
        setVisible(true);                           
        setDefaultCloseOperation(EXIT_ON_CLOSE);    
        setLocation(0, 0);
        
        setJMenuBar(initJMenuBar());
     
        add(panel);
        setContentPane(panel);
        pack();
    }
    private JMenuBar initJMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu Files = new JMenu("Files");
        
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem exit = new JMenuItem("Exit");
        
        JMenuItem highScore = new JMenuItem("High Score");
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int again = javax.swing.JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to start new game?",
                        "Question?", javax.swing.JOptionPane.YES_NO_OPTION, 
                                            javax.swing.JOptionPane.WARNING_MESSAGE);
                if(again == javax.swing.JOptionPane.YES_OPTION) 
                    panel.startGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int again;
                again = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                                            "Question?", javax.swing.JOptionPane.YES_NO_OPTION, 
                                            javax.swing.JOptionPane.WARNING_MESSAGE);
                if(again == javax.swing.JOptionPane.YES_OPTION)
                    System.exit(0);
            }        
        });
        
        highScore.addMouseListener(new MouseAdapter() {
              @Override
            public void mouseClicked(MouseEvent e) {
                   new HighScore(panel);
            }
        });
        
        Files.add(newGame);
        Files.add(exit);
        menuBar.add(Files);
        menuBar.add(highScore);
        
        return menuBar;
    }
}
