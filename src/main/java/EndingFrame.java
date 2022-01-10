/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class EndingFrame extends JFrame {
    
    private int                         spaceCount;
    private ArrayList<String>           storyLine;
    private JPanel                      contentpane;
    private MyImageIcon                 Icon, backgroundImg;
    private JLabel                      drawpane;
    private JTextArea                   storyTextArea;
    private Data                        PlayerData;
    MySoundEffect                       endingSound;
    private int frameWidth  = 1366, frameHeight  = 768;
    
    
    
    public EndingFrame(Data d){
        PlayerData = d;
        storyLine = new ArrayList<String>();
        storyInput();
        setTitle("Arrival");
        setBounds(50,50,frameWidth,frameHeight);
        Icon = new MyImageIcon("resources/icon.png");
        setIconImage( Icon.getImage() );
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout(new BorderLayout());
        addComponent();
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            endingSound.stop();
            new MainApplication(PlayerData);
        }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if(spaceCount < 3)
                    {
                    spaceCount++;
                    setBackgroundImg();
                    setText();
                    }
                    else
                    {
                        setVisible(false);
                        PlayerData.resetLv();
                        new MainApplication(PlayerData);
                        endingSound.stop();
                        dispose();
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    if(spaceCount > 0)
                    {
                     spaceCount--;   
                    }
                    setBackgroundImg();
                    setText();
                }
            }
        });
    }
    
    public void addComponent(){
        storyTextArea = new JTextArea("\n" + storyLine.get(spaceCount) + "\n", 4 , 2);
        Font f = new Font("SanSerif", Font.BOLD + Font.ITALIC, 20);
	storyTextArea.setFont(f);
	storyTextArea.setEditable(false);
        storyTextArea.setForeground(Color.BLACK);
        storyTextArea.setBackground(Color.lightGray);
        endingSound = new MySoundEffect("resources/sound/ending.wav");
        endingSound.playLoop();
        
        backgroundImg = new MyImageIcon("resources/StoryChapters/Story-0.jpg").resize(frameWidth, frameHeight - storyTextArea.getHeight());
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        contentpane.add(drawpane, BorderLayout.CENTER);
        contentpane.add(storyTextArea, BorderLayout.SOUTH);
        validate();
    }
    
    public void setBackgroundImg()
    {
	Thread backgroundThread = new Thread() {
            public void run()
            {
		switch(spaceCount)
                {
                    case 0: backgroundImg = new MyImageIcon("resources/StoryChapters/Story-0.jpg").resize(frameWidth, frameHeight - storyTextArea.getHeight()); break;
                    case 1: backgroundImg = new MyImageIcon("resources/StoryChapters/Story-3.jpg").resize(frameWidth, frameHeight - storyTextArea.getHeight()); break;
                }
                drawpane.setIcon(backgroundImg);
                validate();
            }
	};
	backgroundThread.start();
    }
    
    public void storyInput() {
        boolean finished = false;
        while (!finished) {
            try { Scanner input = new Scanner(new File("EndingStory.txt"));
                while (input.hasNextLine()) { 
                    String line = input.nextLine();
                    storyLine.add(line);
                }
                finished = true;
                input.close();
            } catch (FileNotFoundException e) {
                   System.out.println(e);
                   
            }
        }
    }
    
    public void setText()
    {
	Thread textThread = new Thread() {
            public void run()
            {
                storyTextArea.setText("\n" + storyLine.get(spaceCount) + "\n");
                validate();
            }
	};
	textThread.start();
    }
    
    class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};
}