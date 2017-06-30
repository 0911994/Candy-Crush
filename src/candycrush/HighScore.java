package candycrush;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HighScore extends JFrame{
    private static JLabel[] playerName = new JLabel[11];
    private static JLabel[] positionNumbers = new JLabel[11];
    private static JLabel[] pointsArchieved = new JLabel[11];
    
    private static ArrayList<String> scoreTracker = new ArrayList<>();
    
    public HighScore(String ime, int osvojenihPoena, Component panel) throws HeadlessException {
        showScore(panel);
        setScore(ime, osvojenihPoena);
    }
    public HighScore(Component panel)
    {
        showScore(panel);
    }
    public void showScore(Component panel)
    {
        this.setTitle("High Score");
        this.setSize(350, 350);
        this.setVisible(true);
        setLocationRelativeTo(panel);
        for (int i = 0; i < 11; i++) {
            playerName[i] = new JLabel("");
            pointsArchieved[i] = new JLabel("");
            positionNumbers[i] = new JLabel(""+(i+1));
        }
        
        readTextFileLineByLine();
        String[] spliter = new String[2];
        for (int i = 0; i < 11; i++) {
            positionNumbers[i].setBounds(0, i * 20, 20, 20);
            playerName[i].setBounds(40, i * 20, 220, 20);
               pointsArchieved[i].setBounds(240, i * 20, 300, 20);
            positionNumbers[i].setHorizontalAlignment(JLabel.CENTER);
            playerName[i].setHorizontalAlignment(JLabel.LEFT);
            add(positionNumbers[i]);
            add(playerName[i]);
            add(pointsArchieved[i]);
        }
        
        for (int i = 0; i < scoreTracker.size(); i++) {
            spliter = scoreTracker.get(i).split("-");
            playerName[i].setText(spliter[0]);
            pointsArchieved[i].setText(spliter[1]);
        }

        pointsArchieved[10].setVisible(false);
        playerName[10].setVisible(false);
        positionNumbers[10].setVisible(false);
    }
    public void setScore(String ime, final int score) {
        boolean moved = false;
        for(int i = 0; i < scoreTracker.size(); i++) {
            if (score > Integer.parseInt(pointsArchieved[i].getText())) {
                if(scoreTracker.size() < 10)
                    scoreTracker.add(scoreTracker.get(scoreTracker.size()-1));
                for (int k = scoreTracker.size()-1; k > i; k--) {
                    playerName[k].setText(playerName[k-1].getText());
                    pointsArchieved[k].setText(pointsArchieved[k-1].getText());
                    scoreTracker.set(k, scoreTracker.get(k-1));
                    moved = true;
                }
                pointsArchieved[i].setText("" + score);
                pointsArchieved[i].setVisible(true);
                
                String nameToPut = ime + "-" + score;
                scoreTracker.set(i, nameToPut);
                    break;
            }
        }
        if(!moved)
        {
            if(scoreTracker.size() < 10)
                scoreTracker.add(ime + "-" + score);
        }
        String[] spliter = new String[2];
        for (int i = 0; i < scoreTracker.size(); i++) {
            spliter = scoreTracker.get(i).split("-");
            playerName[i].setText(spliter[0]);
            pointsArchieved[i].setText(spliter[1]);
        }
        writeTextFileLineByLine();
    }; 
    public static void readTextFileLineByLine() {
        FileReader in = null;
        File file = new File(".\\files\\rezultati.txt");
        //BufferedReader dozvoljava čitanje većeg "komada" datoteke odjednom.
        BufferedReader bin = null;
        if(!file.exists()){
            if(!(new File(file.getParent()).exists()))
                file.getParentFile().mkdirs();
                try{                                
                    file.createNewFile();
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage().toString());
                }
        }
        // File file = new File(".\\files\\rezultati.txt");
        scoreTracker.clear();
        try {
            in = new FileReader(file);
            bin = new BufferedReader(in);
            String data;
            int count = 0;
                while ((data = bin.readLine()) != null) {
                    scoreTracker.add(data);
                    count++;
                }
            if(count == 0)
                System.out.println("error");
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        }
    }
    public static void writeTextFileLineByLine() {
        FileWriter out = null;

        try {
            out = new FileWriter(".\\files\\rezultati.txt");
            for (int i = 0; i < scoreTracker.size(); i++) {
                for (int j = 0; j < scoreTracker.get(i).length(); j++) {
                    out.write(scoreTracker.get(i).charAt(j));
                }
                out.write("\n");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }

        }
    }
}
