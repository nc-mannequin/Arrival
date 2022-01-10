/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class SelectFrame extends JFrame{
    private boolean active;
    private JPanel        contentpane;
    private MyPane	  drawpane;
    private JButton	  nextButton;
    private JCheckBox  [] radio;
    private JLabel AvailableLabel,bg;
    private JFrame gameFrame;
    private Data PlayerData;
    private MyImageIcon Icon;
    private String [] items = {"Shooting Robot","Solar Robot","Blue Robot","Ship Robot","Radio Robot","Tank Robot","Mecury","Blitz Robot","Gollum Robot","Falcon Robot"};
    private ArrayList<Object> team  = new ArrayList<Object>();
    private int Available;
    MySoundEffect themeSound;
    
    public SelectFrame(Data d){
        PlayerData =d;
        active = true;
        setTitle("Choose Your Robots");
        Icon = new MyImageIcon("resources/icon.png");
        setIconImage( Icon.getImage() );
        setResizable(false);
	setBounds(300, 200, 1366, 768);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        addWindowListener(new WindowAdapter(){
        public void windowOpened(WindowEvent e){
            Checking();
        }
        public void windowClosing(WindowEvent e){
            active = false;
        }
        });
        
	contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new FlowLayout() );
	AddComponents();
        
          }
    
    public void closeFrame(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    public void Checking(){
            Thread t = new Thread() {
            public void run() {
                while (active) {
                    
                    try{Thread.sleep(200);}catch(Exception e){}
                    for (int i = 0; i < 10; ++i) {
                        if (Available == 0) {
                           if (!radio[i].isSelected()) {
                                radio[i].setEnabled(false);
                            }
                        } else {
                            radio[i].setEnabled(true);
                        }
                    }
                    
                }
                closeFrame();
            }
        };
        t.start();
    }
    
    public void AddComponents()
    {
        themeSound = new MySoundEffect("resources/sound/theme.wav");
        themeSound.playLoop();
        
         switch(PlayerData.getProgress()){
            case 0:{MyImageIcon img = new MyImageIcon("resources/select/selectmap1.jpg").resize(1366, 768); Available = 4; bg = new JLabel(img); break;}
            case 1:{MyImageIcon img = new MyImageIcon("resources/select/selectmap2.jpg").resize(1366, 768); Available = 6; bg = new JLabel(img); break;}
            case 2:{MyImageIcon img = new MyImageIcon("resources/select/selectmap3.jpg").resize(1366, 768); Available = 7; bg = new JLabel(img); break;}
            case 3:{MyImageIcon img = new MyImageIcon("resources/select/selectmap4.jpg").resize(1366, 768); Available = 8; bg = new JLabel(img); break;}
        }
        
        
        bg.setLayout(null);
        radio = new JCheckBox[10];
        JPanel rpanel	   = new JPanel();
        nextButton = new JButton("GO!");
        nextButton.setBounds(750, 400, 50, 50);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeSound.stop();
                if (gameFrame == null) {
                 for(int i=0;i<drawpane.getComponentCount();++i){team.add(drawpane.getComponent(i));}
                    gameFrame = new GameFrame(PlayerData,team);
                    active = false;
                    dispose();
                } else {
                    gameFrame.setVisible(true);
                }
            }
        });
        
        AvailableLabel = new JLabel();
        AvailableLabel.setText(String.valueOf(Available));
        for (int i=0; i < 10; i++) 
        {
            radio[i] = new JCheckBox( items[i].toString() );
            
            if(i<(PlayerData.getProgress()*2)+6)
            rpanel.add( radio[i] );

            radio[i].addItemListener( new ItemListener() {
		public void itemStateChanged( ItemEvent e )
		{
                    JCheckBox temp = (JCheckBox)e.getItem();
                    if (e.getStateChange()==ItemEvent.SELECTED&&Available>0)
                    {
                      			drawpane.doAdd(temp.getText());                      
                    }
                    else if(e.getStateChange()==ItemEvent.DESELECTED){
                   		drawpane.doRemove(temp.getText());
                    }
                    
                    AvailableLabel.setText(String.valueOf(Available));
		}
            });	
	}
	


	drawpane = new MyPane();
        rpanel.setBounds(180, 500, 1000, 40);
        drawpane.setBounds(290,335,870,100);
        drawpane.setOpaque(false);
              nextButton.setBounds(1205, 500, 100, 40);
        bg.add(drawpane);
                bg.add(rpanel);
        bg.add(nextButton);
        bg.add(rpanel);
        contentpane.add(bg);
        validate();
    }
    
    class MyPane extends JPanel {

     private JLabel Solar,Gunner1,Wall,Ship,Radio,Tank,Gunner2,Gunner3,Gunner4,Gunner5;

        public MyPane() {
            setPreferredSize(new Dimension(800,100));
            Gunner1 = new RobotWalker1Label(0);
            Gunner2 = new RobotWalker2Label(0);
            Gunner3 = new RobotWalker3Label(0);
            Gunner4 = new RobotWalker4Label(0);
            Gunner5 = new RobotWalker5Label(0);
            Solar = new SolarBotLabel(0);
            Wall = new BlueBotLabel(0);
            Ship = new ShipBotLabel(0);
            Radio = new RadioRobotLabel(0);
            Tank = new TankBotLabel(0);
        }

        public void doAdd(String no) {
            if(Available>0){
            switch(no){
                case "Shooting Robot":{add(Gunner1); break;}
                case "Solar Robot":{add(Solar); break;}
                case "Blue Robot":{add(Wall); break;}
                case "Ship Robot":{add(Ship); break;}
                case "Radio Robot":{add(Radio); break;}
                case "Tank Robot":{add(Tank); break;}
                case "Mecury":{add(Gunner2); break;}
                case "Blitz Robot":{add(Gunner3); break;}
                case "Gollum Robot":{add(Gunner4); break;}
                case "Falcon Robot":{add(Gunner5); break;}
                
            }
            Available-=1;
            
                validate();
                repaint();
            }
        }

        public void doRemove(String no) {
            if (getComponentCount() > 0)
            {
                switch(no){
                case "Shooting Robot":{remove(Gunner1); break;}
                case "Solar Robot":{remove(Solar); break;}
                case "Blue Robot":{remove(Wall); break;}
                case "Ship Robot":{remove(Ship); break;}
                case "Radio Robot":{remove(Radio); break;}
                case "Tank Robot":{remove(Tank); break;}
                case "Mecury":{remove(Gunner2); break;}
                case "Blitz Robot":{remove(Gunner3); break;}
                case "Gollum Robot":{remove(Gunner4); break;}
                case "Falcon Robot":{remove(Gunner5); break;}
                
            }
                Available+=1;
                

                validate();		
                repaint();	
            }
        }
    }
}

