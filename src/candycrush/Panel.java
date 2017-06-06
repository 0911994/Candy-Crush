package candycrush;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

class Panel extends JPanel implements ActionListener{
    private static final int panelHeight = Frame.getFrameHeight()-25;
    private static final int panelWidth = Frame.getFrameWidth();
    private final int gridSize = 6;
    private final int numberOfCandyTypes = 4;
    private Candy[][] candyGrid;
    private JButton[][] candyButtonGrid;
    private Random random = new Random();
    private boolean fistEnter = true;
    private int points;
    
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
        points = 0;
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
                candyButtonGrid[i][j].addActionListener(this);
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
        HandleCollisions();
    }
    private void HandleCollisions()
    {
        boolean candiesReordered;
        do
        {
            candiesReordered = false;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize - 2; j++) {
                    if (candyGrid[i][j].getCandyType() == candyGrid[i][j + 1].getCandyType()
                            && candyGrid[i][j + 2].getCandyType() == candyGrid[i][j].getCandyType()) {
                        candyGrid[i][j + 1].setCandyType(random.nextInt(numberOfCandyTypes));
                        candyGrid[i][j + 2].setCandyType(random.nextInt(numberOfCandyTypes));
                        candyGrid[i][j].setCandyType(random.nextInt(numberOfCandyTypes));
                        candiesReordered = true;
                        if(!fistEnter)
                            points+=2;
                    }
                }
            }
            for (int i = 0; i < gridSize - 2; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (candyGrid[i][j].getCandyType() == candyGrid[i + 1][j].getCandyType()
                            && candyGrid[i + 2][j].getCandyType() == candyGrid[i][j].getCandyType()) {
                        candyGrid[i + 2][j].setCandyType(random.nextInt(numberOfCandyTypes));
                        candyGrid[i + 1][j].setCandyType(random.nextInt(numberOfCandyTypes));
                        candyGrid[i][j].setCandyType(random.nextInt(numberOfCandyTypes));
                        
                        candiesReordered = true;
                        if(!fistEnter)
                            points+=2;
                    }
                }
            }
        }
        while (candiesReordered);
         for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                candyButtonGrid[i][j].setText("" + candyGrid[i][j].getCandyType());
    }
    private void CheckMatches()
    {
        HandleCollisions();

        for(int i = 0; i < gridSize; i++)
                for(int j = 0; j < gridSize; j++)
                    candyButtonGrid[i][j].setEnabled(true);
        fistEnter = true;
        System.out.println(points);
    }
    int[] inititor = new int[2];
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] indeses = ((JButton) e.getSource()).getName().split("-");
        int xCoordinate = Integer.parseInt(indeses[0]);
        int yCoordinate = Integer.parseInt(indeses[1]);
        if(fistEnter)
        {
            fistEnter = false;
            for(int i = 0; i < gridSize; i++)
                for(int j = 0; j < gridSize; j++)
                    candyButtonGrid[i][j].setEnabled(false);        
            if(yCoordinate < gridSize-1)
                candyButtonGrid[xCoordinate][yCoordinate+1].setEnabled(true);
            if(yCoordinate > 0)
                candyButtonGrid[xCoordinate][yCoordinate-1].setEnabled(true);
            if(xCoordinate < gridSize-1)
                candyButtonGrid[xCoordinate+1][yCoordinate].setEnabled(true);
            if(xCoordinate > 0)
                candyButtonGrid[xCoordinate-1][yCoordinate].setEnabled(true);
            inititor[0] = xCoordinate;
            inititor[1] = yCoordinate;
        }
        else
        {
            Candy tempCandy;
            tempCandy = candyGrid[xCoordinate][yCoordinate];
            candyGrid[xCoordinate][yCoordinate] =candyGrid[inititor[0]][inititor[1]]; 
            candyGrid[inititor[0]][inititor[1]] = tempCandy;
            CheckMatches();
        }
    }
}
