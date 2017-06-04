package candycrush;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

class Panel extends JPanel{
    private static final int panelHeight = Frame.getFrameHeight()-25;
    private static final int panelWidth = Frame.getFrameWidth();
    private final int gridSize = 6;
    private final int numberOfCandyTypes = 4;
    private Candy[][] candyGrid;
    private JButton[][] candyButtonGrid;
    private Random random = new Random();
    
    public static int getPanelHeight() {
        return panelHeight;
    }

    public static int getPanelWidth() {
        return panelWidth;
    }
    
    
    public Panel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        setVisible(true);
        initComponents();
        generateCandies();
    }
    private void initComponents()
    {
        candyGrid = new Candy[gridSize][gridSize];
        candyButtonGrid = new JButton[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++)
        {
            for(int j = 0; j < gridSize; j++)
            {
                candyButtonGrid[i][j] = new JButton();
                candyButtonGrid[i][j].setBounds(panelWidth / (gridSize) * j,
                        (panelHeight) / (gridSize) * i,
                        panelWidth / (gridSize),
                        panelHeight / (gridSize));
                candyButtonGrid[i][j].setName(i + "-" + j);
                candyButtonGrid[i][j].setVisible(true);
                candyButtonGrid[i][j].setEnabled(true);
                candyButtonGrid[i][j].setFocusable(true);

                add(candyButtonGrid[i][j]);
            }
        }
        repaint();
    }
    
    private void generateCandies()
    {
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
            {
                candyGrid[i][j] = new Candy(random.nextInt(numberOfCandyTypes));
            }
        boolean candiesReordered;
        do
        {
            candiesReordered = false;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize - 2; j++) {
                    if (candyGrid[i][j].getCandyType() == candyGrid[i][j + 1].getCandyType()
                            && candyGrid[i][j + 2].getCandyType() == candyGrid[i][j].getCandyType()) {
                        candyGrid[i][j + 1].setCandyType(random.nextInt(numberOfCandyTypes));
                        candiesReordered = true;
                    }
                }
            }
            for (int i = 0; i < gridSize - 2; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (candyGrid[i][j].getCandyType() == candyGrid[i + 1][j].getCandyType()
                            && candyGrid[i + 2][j].getCandyType() == candyGrid[i][j].getCandyType()) {
                        candyGrid[i + 1][j].setCandyType(random.nextInt(numberOfCandyTypes));
                        candiesReordered = true;
                    }
                }
            }
        }
        while (candiesReordered);
         for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                candyButtonGrid[i][j].setText("" + candyGrid[i][j].getCandyType());
    }
    
}
