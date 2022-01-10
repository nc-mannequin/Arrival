/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainApplication extends JFrame{
    private JPanel contentpane;
    private JLabel BG;
    private JLabel StartLabel,WikiLabel,CreatorLabel,HowtoplayLabel;
    private MyImageIcon Icon, BackgroundImg;
    private JDialog wikiFrame,CreatorFrame,HowtoplayFrame;
    private JFrame OpeningFrame;
    private Data PlayerData;
    MyImageIcon Startimg,Wikiimg,Creatorimg,Howtoplayimg;
    MySoundEffect clickSound, mainSound, themeSound;
    
    public MainApplication(Data d){
        PlayerData=d;
        setTitle("Arrival");
        setBounds(50,50,1366,768);
        Icon = new MyImageIcon("resources/icon.png");
        setIconImage( Icon.getImage() );
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            JFrame f = new JFrame();
            mainSound.stop();
            themeSound.stop();
            int op = JOptionPane.showConfirmDialog( f, "Would you like to exit the game?", "EXIT GAME?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
            if(op==0){setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );}
            else{f.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );}
        }
        });
        
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout(new BorderLayout());
        addComponent();
    }
    
    public void addComponent(){
        BackgroundImg = new MyImageIcon("resources/mainbg2.jpg");
        BG = new JLabel();
        BG.setIcon(BackgroundImg);
        BG.setLayout(null);
        clickSound = new MySoundEffect("resources/sound/beep.wav");
        mainSound = new MySoundEffect("resources/sound/main.wav");
        themeSound = new MySoundEffect("resources/sound/theme.wav");
        mainSound.playLoop();
        
        Startimg = new MyImageIcon("resources/MainAppLabel/startlabel.png").resize(328, 128);
        StartLabel = new JLabel(Startimg);
        StartLabel.setBounds(150, 0, 328, 128);
        StartLabel.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            mainSound.stop();
            clickSound.playOnce();
            Thread OpeningFrameThread = new Thread() {
                public void run() {
                    if (OpeningFrame == null) {
                        OpeningFrame = new OpeningFrame(PlayerData);
                        dispose();
                    } else {
                        OpeningFrame.setVisible(true);
                    }
                }
            };
            
            if (PlayerData.getProgress() == -1) {
                OpeningFrameThread.start();
            }
            
            else {new SelectFrame(PlayerData);
            dispose();}
            
        }
        });
        Wikiimg = new MyImageIcon("resources/MainAppLabel/informationlabel.png").resize(328, 128);
        WikiLabel = new JLabel(Wikiimg);
        WikiLabel.setBounds(150, 120, 328, 128);
        WikiLabel.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            clickSound.playOnce();
            if(wikiFrame == null){wikiFrame = new Wiki();}
            else {wikiFrame.setVisible(true); }
        }
        });
        Howtoplayimg = new MyImageIcon("resources/MainAppLabel/howtoplaylabel.png").resize(328, 128);
        HowtoplayLabel = new JLabel(Howtoplayimg);
        HowtoplayLabel.setBounds(150, 240, 328, 128);
        HowtoplayLabel.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            clickSound.playOnce();
            if(HowtoplayFrame == null){HowtoplayFrame = new Howtoplay();}
            else {HowtoplayFrame.setVisible(true); }
        }
        });
        Creatorimg = new MyImageIcon("resources/MainAppLabel/memberlabel.png").resize(328, 168);
        CreatorLabel = new JLabel(Creatorimg);
        CreatorLabel.setBounds(150, 364, 328, 168);
        CreatorLabel.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            clickSound.playOnce();
            if(CreatorFrame == null){CreatorFrame = new Creator();}
            else {CreatorFrame.setVisible(true); }
        }
        });
        
        
        BG.add(StartLabel);
        BG.add(WikiLabel);
        BG.add(CreatorLabel);
        BG.add(HowtoplayLabel);
        
        contentpane.add(BG,BorderLayout.CENTER);
        
        validate();
    }
    
    
    public static void main(String[] Args){
        new MainApplication(new Data(-1));
    }
    
    
    class MyImageIcon extends ImageIcon {

        public MyImageIcon(String fname) {
            super(fname);
        }

        public MyImageIcon(Image image) {
            super(image);
        }

        public MyImageIcon resize(int width, int height) {
            Image oldimg = this.getImage();
            Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            return new MyImageIcon(newimg);
        }
    }; 
    
}

class MySoundEffect {

    private java.applet.AudioClip audio;

    public MySoundEffect(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            audio = java.applet.Applet.newAudioClip(file.toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
        audio.play();
    }

    public void playLoop() {
        audio.loop();
    }

    public void stop() {
        audio.stop();
    }
}
