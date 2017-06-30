package candycrush;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Panel extends JPanel implements ActionListener{
    private static final int panelHeight = Frame.getFrameHeight()-45;
    private static final int panelWidth = Frame.getFrameWidth();
    private final int gridSize = 6;
    public static final int numberOfCandyTypes = 4;
    
    private Candy[][] candyGrid;
    private JButton[][] candyButtonGrid;
    
    private Random random = new Random();
    private boolean fistEnter = true; //kad se pocne igra, prvi prikaz
    private int points; 
    
    private boolean imagesLoaded;
    private ImageIcon[] icons; //da bi se crtalo na dugmadima mora imati ovaj tip

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
        
        imagesLoaded = Candy.loadImages();
        icons = new ImageIcon[numberOfCandyTypes]; //ikonice su niz dugmadi
        initComponents(); //ucitaj komponente
        startGame();
    }
    public void startGame()
    {
        points = 0;
        generateCandies();
    }
    
    private void gameOver()
    {
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
            {
                candyButtonGrid[i][j].setVisible(false);
            }
         String odgovor;
        do {
            odgovor = JOptionPane.showInputDialog(this, "Congratulation! You won.\nEnter your name: ");
            if(odgovor != null)
                if(!odgovor.isEmpty())
                    new HighScore(odgovor, points, this);
                else
                    JOptionPane.showMessageDialog(this, "Molim vas unesite ime");
            else
                break;
        } while (odgovor.isEmpty());
        
    }
    private void initComponents()
    {
        if(imagesLoaded)
            for(int i = 0; i < numberOfCandyTypes; i++) 
            {
                Image newimg = Candy.candyImages[i].getScaledInstance(panelWidth / (gridSize),
                        panelHeight / (gridSize),  java.awt.Image.SCALE_SMOOTH ) ;
                icons[i] = new ImageIcon( newimg ); //ikonica postaje nova smanjenja slika
            }
        points = 0;
        candyGrid = new Candy[gridSize][gridSize]; //pravi matrice
        candyButtonGrid = new JButton[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++)
        {
            for(int j = 0; j < gridSize; j++)
            {
                candyButtonGrid[i][j] = new JButton();
                candyButtonGrid[i][j].setBounds(panelWidth / (gridSize) * j,
                        (panelHeight) / (gridSize) * i,
                        panelWidth / (gridSize),
                        panelHeight / (gridSize)); //postavlja granice 
                candyButtonGrid[i][j].setName(i + "-" + j);
                candyButtonGrid[i][j].setVisible(true);
                candyButtonGrid[i][j].setEnabled(true);
                candyButtonGrid[i][j].setFocusable(true);
                candyButtonGrid[i][j].addActionListener(this);
                add(candyButtonGrid[i][j]);
            }
        }
        repaint(); //nacrtaj
    }
    
    private void generateCandies()
    {
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
            {
                candyButtonGrid[i][j].setVisible(true);
            }
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
            {
                candyGrid[i][j] = new Candy(random.nextInt(numberOfCandyTypes));
            }
        normalize();
    }
    private void normalize()
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
            for(int j = 0; j < gridSize; j++){
             if(imagesLoaded)
                 candyButtonGrid[i][j].setIcon(icons[candyGrid[i][j].getCandyType()]);
             else
                candyButtonGrid[i][j].setText("" + candyGrid[i][j].getCandyType());
            }
    }
    private boolean HandleCollisions(int x, int y)
    {
        ArrayList<Integer> sameInRow = new ArrayList<>();
        ArrayList<Integer> sameInColumn = new ArrayList<>();
            
        sameInColumn.clear();
        sameInRow.clear();
        sameInColumn.add(y);
        sameInRow.add(x);
            for(int j = y; j < gridSize - 1; j++)
                if(candyGrid[x][j].getCandyType() == candyGrid[x][j+1].getCandyType())
                    sameInColumn.add(j+1);
                else
                    break;
            for(int j = y; j > 0; j--)
                if(candyGrid[x][j].getCandyType() == candyGrid[x][j-1].getCandyType())
                    sameInColumn.add(j-1);
                else
                    break;
            for(int j = x; j > 0; j--)
                if(candyGrid[j][y].getCandyType() == candyGrid[j-1][y].getCandyType())
                    sameInRow.add(j-1);
                else
                    break;
            for(int j = x; j < gridSize - 1; j++)
                if(candyGrid[j][y].getCandyType() == candyGrid[j+1][y].getCandyType())
                    sameInRow.add(j+1);
                else
                    break;
            if(sameInRow.size() < 3 && sameInColumn.size() < 3  )
            {
                return false;
            }
            else
            {
                if(sameInColumn.size() >= 3)
                {
                    int maxColumn, minColumn;
                    maxColumn = minColumn = sameInColumn.get(0);
                    for(int i = 1; i < sameInColumn.size(); i++)
                    {
                        if(maxColumn < sameInColumn.get(i))
                            maxColumn = sameInColumn.get(i);
                        if(minColumn > sameInColumn.get(i))
                            minColumn = sameInColumn.get(i);
                    }
                    for (int i = minColumn; i <= maxColumn; i++) 
                    {
                        candyGrid[x][i] = new Candy(random.nextInt(numberOfCandyTypes));
                        points++;
                    }
                }
                if(sameInRow.size() >= 3)
                {
                    int maxColumn, minColumn;
                    maxColumn = minColumn = sameInRow.get(0);
                    for(int i = 1; i < sameInRow.size(); i++)
                    {
                        if(maxColumn < sameInRow.get(i))
                            maxColumn = sameInRow.get(i);
                        if(minColumn > sameInRow.get(i))
                            minColumn = sameInRow.get(i);
                    }
                        for(int i = sameInRow.get(0); i > 0; i--)
                        {    
                            candyGrid[i][y] = new Candy(random.nextInt(numberOfCandyTypes));
                            points++;
                        }   
                }
            }
            return true;
    }
    
    private void Swap(int x, int y)
    {
        Candy tempCandy = candyGrid[x][y];
        candyGrid[x][y] = candyGrid[inititor[0]][inititor[1]];
        candyGrid[inititor[0]][inititor[1]] = tempCandy;
    }
    private void CheckMatches(int x, int y)
    {
        Swap(x, y);
        boolean first = HandleCollisions(x, y);
        boolean second = HandleCollisions(inititor[0], inititor[1]);
        if(first || second)
            normalize();
        else
            Swap(x, y);
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                candyButtonGrid[i][j].setEnabled(true);
        fistEnter = true;
        System.out.println(points);
        if(points > 20)
            gameOver();
    }
    private int[] inititor = new int[2];
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
            CheckMatches(xCoordinate, yCoordinate);
        }
    }
}
