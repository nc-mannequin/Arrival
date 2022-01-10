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





public class GameFrame extends JFrame{
    private Data PlayerData;
    private JPanel cpane;
    private JTextField energyText;
    private ArrayList<Object> selectedrobot = new ArrayList<Object>();
    public static int energy;
    private JLabel bg;
    private boolean active;
    private MyImageIcon backgroundImg, Icon;
    MySoundEffect themeSound;
    public TileLabel selectingTile;
    public Tile SelectTile;
    private JProgressBar RoundProgressBar;
    public MonsterLabel[] FirstMonster = new MonsterLabel[5];
    public ArrayList<MonsterLabel> MonsterInLine0 = new ArrayList<MonsterLabel>();
    public ArrayList<MonsterLabel> MonsterInLine1 = new ArrayList<MonsterLabel>();
    public ArrayList<MonsterLabel> MonsterInLine2 = new ArrayList<MonsterLabel>();
    public ArrayList<MonsterLabel> MonsterInLine3 = new ArrayList<MonsterLabel>();
    public ArrayList<MonsterLabel> MonsterInLine4 = new ArrayList<MonsterLabel>();
    
    public ArrayList<MonsterLabel> Sorting(ArrayList<MonsterLabel> ALM){
        int min = 1500;
        for(int i=0;i<ALM.size();++i){
            if(ALM.get(i).getCurX()<min){min = ALM.get(i).getCurX();ALM.add(0,ALM.get(i)); ALM.remove(i+1); }
        }
        return ALM;
    }
    
    public void killAll(){
        for(MonsterLabel m :MonsterInLine0){m.getML().hurt(100);}
        for(MonsterLabel m :MonsterInLine1){m.getML().hurt(100);}
        for(MonsterLabel m :MonsterInLine2){m.getML().hurt(100);}
        for(MonsterLabel m :MonsterInLine3){m.getML().hurt(100);}
        for(MonsterLabel m :MonsterInLine4){m.getML().hurt(100);}
        for(int i=0;i<5;++i){
                for(int j=0;j<11;++j){
                    if(BoardSyncData.Board[i][j].getRobotLabel()!=null){BoardSyncData.Board[i][j].setDeployable(true);BoardSyncData.Board[i][j].setDeployed(false); BoardSyncData.Board[i][j].getRobotLabel().getRobot().hurt(100);} 
                }
            }
    }
    
    void popLoseframe(){
        JDialog lose = new JDialog();
        lose.setTitle("You Lose");
        lose.setBounds(50, 50, 1366, 768);
        MyImageIcon Img = new MyImageIcon("resources/youlose.png").resize(1366, 768);
        JLabel pic = new JLabel(Img);
        pic.setBounds(50, 50, 1000, 600);
        lose.add(pic);
        validate();
        lose.setVisible(true);
        try{Thread.sleep(3000);}catch(Exception e){}
        lose.dispose();
        themeSound.stop();
        new MainApplication(PlayerData);
    }
    void popWinframe(){
        JDialog lose = new JDialog();
        lose.setTitle("You Win");
        lose.setBounds(50, 50, 1366, 768);
        MyImageIcon Img = new MyImageIcon("resources/youwin.png").resize(1366, 768);
        JLabel pic = new JLabel(Img);
        pic.setBounds(50, 50, 1000, 600);
        lose.add(pic);
        validate();
        lose.setVisible(true);
        try{Thread.sleep(3000);}catch(Exception e){}
        lose.dispose();
        themeSound.stop();
        if(PlayerData.getProgress() == 4)
        {
           new EndingFrame(PlayerData);
        }
        else
        {new MainApplication(PlayerData);}
    }
    void startgame(){
        Thread Map1Thread = new Thread(){
        	public void run(){
                	BoardSyncData.setSummonedMonster(0);
                	BoardSyncData.setAllMonster(30);
                	BoardSyncData.setMonsterRemaining(30);
                 	while(BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()&&active){
                   	Random rand = new Random();
                    	int row = rand.nextInt(5);
                    	MonsterLabel Monster = new MonsterLabel();
                    	BoardSyncData.SummonMonster();
                    	int phase=0;
                    	if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.2)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.5)){phase=1;}
                        else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.5)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.7)){phase=2;}
                    	else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.7)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()){phase=3;}
                    	switch(rand.nextInt(3)+phase){
                        	case 0:{Monster = new MonsterIILabel(row); break;}
                        	case 1:{Monster = new MonsterIIILabel(row); break;}
                        	case 2:{Monster = new RedwithnobrainLabel(row); break;}
                        	case 3:{Monster = new GreenWithBrainLabel(row); break;}
                        	case 4:{Monster = new GirlWraithLabel(row); break;}
                        	case 5:{Monster = new UnicornWraithLabel(row); break;}
                    	}
                    	Monster.setBounds(1000, 70+98*row, 100, 75);
                    	bg.add(Monster);
                    	switch(row){
                        	case 0: {MonsterInLine0.add(Monster); break;}
                        	case 1: {MonsterInLine1.add(Monster); break;}
                        	case 2: {MonsterInLine2.add(Monster); break;}
                        	case 3: {MonsterInLine3.add(Monster); break;}
                        	case 4: {MonsterInLine4.add(Monster); break;}
                    	}
                	try{Thread.sleep((rand.nextInt(100)*10+5000)/(phase+1));}catch(Exception e){}
               	}
               	while(BoardSyncData.getMonsterRemaining()!=0&&active){try{Thread.sleep(100);}catch(Exception e){}}if(BoardSyncData.getMonsterRemaining()==0){active = false; killAll(); dispose(); PlayerData.passLv(); popWinframe();}
        	}
    	};
        
        Thread Map2Thread = new Thread(){
            public void run(){
                backgroundImg = new MyImageIcon("resources/BG2.jpg").resize(1366, 618); 
                bg.setIcon(backgroundImg);
                bg.setLayout(null);
                validate();
                        BoardSyncData.setSummonedMonster(0);
                	BoardSyncData.setAllMonster(40);
                	BoardSyncData.setMonsterRemaining(40);
                 	while(BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()&&active){
                     	Random rand = new Random();
                    	int row = rand.nextInt(5);
                    	MonsterLabel Monster = new MonsterLabel();
                    	int phase=0;
                    	if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.3)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.6)){phase=1;}
                        else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.6)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()){phase=2;}
                        int check = 0;
                        switch(rand.nextInt(7) + phase){
                            case 0:{Monster = new MonsterIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 1:{Monster = new MonsterIIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 2:{Monster = new RedwithnobrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 3:{Monster = new GreenWithBrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 4:{JLabel wind = new WindLabel(bg,row); check = 1; break;}
                            case 5:{Monster = new GirlWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 6:{Monster = new UnicornWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 7:{Monster = new MonsterIVLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 8:{Monster = new BigTrollTreeLabel(row); Monster.setBounds(1000, 70+98*row, 200, 150); break;}
                        }
                        bg.add(Monster);
                        if(check != 1){
                            BoardSyncData.SummonMonster();
                            switch(row){
                                case 0: {MonsterInLine0.add(Monster); break;}
                                case 1: {MonsterInLine1.add(Monster); break;}
                                case 2: {MonsterInLine2.add(Monster); break;}
                                case 3: {MonsterInLine3.add(Monster); break;}
                                case 4: {MonsterInLine4.add(Monster); break;}
                            }
                        }
                    try{Thread.sleep((rand.nextInt(100)*10+5000)/(phase+1));}catch(Exception e){}
                   }
                   while(BoardSyncData.getMonsterRemaining()!=0&&active){try{Thread.sleep(100);}catch(Exception e){}}if(BoardSyncData.getMonsterRemaining()==0){active = false; killAll(); dispose(); PlayerData.passLv(); popWinframe();}
            }
        };

        Thread Map3Thread = new Thread(){
            public void run(){
                    backgroundImg = new MyImageIcon("resources/BG3.jpg").resize(1366, 618);
                    bg.setIcon(backgroundImg);
                    bg.setLayout(null);
                    validate();
                    
                        BoardSyncData.setSummonedMonster(0);
                	BoardSyncData.setAllMonster(50);
                	BoardSyncData.setMonsterRemaining(50);
                        Random rand = new Random();
                        MonsterLabel Monster;
                    for(int i=0;i<3;i++){
                        int rowland = rand.nextInt(5);
                        int colland = rand.nextInt(4)+7;
                        if(!BoardSyncData.Board[rowland][colland].isGraved()){
                            BoardSyncData.SummonMonster();
                            switch(rowland){
                                case 0: new Obstrucle3Label(bg,rowland,colland,MonsterInLine0); BoardSyncData.Board[rowland][colland].setGraved(true); BoardSyncData.Board[rowland][colland].setDeployable(false);BoardSyncData.Board[rowland][colland].setDeployed(false); break;
                                case 1: new Obstrucle3Label(bg,rowland,colland,MonsterInLine1); BoardSyncData.Board[rowland][colland].setGraved(true); BoardSyncData.Board[rowland][colland].setDeployable(false);BoardSyncData.Board[rowland][colland].setDeployed(false); break;
                                case 2: new Obstrucle3Label(bg,rowland,colland,MonsterInLine2); BoardSyncData.Board[rowland][colland].setGraved(true); BoardSyncData.Board[rowland][colland].setDeployable(false);BoardSyncData.Board[rowland][colland].setDeployed(false); break;
                                case 3: new Obstrucle3Label(bg,rowland,colland,MonsterInLine3); BoardSyncData.Board[rowland][colland].setGraved(true); BoardSyncData.Board[rowland][colland].setDeployable(false);BoardSyncData.Board[rowland][colland].setDeployed(false); break;
                                case 4: new Obstrucle3Label(bg,rowland,colland,MonsterInLine4); BoardSyncData.Board[rowland][colland].setGraved(true); BoardSyncData.Board[rowland][colland].setDeployable(false);BoardSyncData.Board[rowland][colland].setDeployed(false); break;
                            }
                        }
                        else{i-=1;}
                    }
                     while(BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()&&active){
                        int row = rand.nextInt(5);
                        Monster = new MonsterLabel();
                        int phase=0;
                    	if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.2)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.5)){phase=1;}
                        else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.5)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.7)){phase=2;}
                    	else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.7)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()){phase=3;}
                        switch(rand.nextInt(6) + phase){
                            case 0:{Monster = new MonsterIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 1:{Monster = new MonsterIIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 2:{Monster = new RedwithnobrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 3:{Monster = new GreenWithBrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 4:{Monster = new GirlWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 5:{Monster = new UnicornWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 6:{Monster = new MonsterIVLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 7:{Monster = new BigTrollTreeLabel(row); Monster.setBounds(1000, 70+98*row, 200, 150); break;}
                            case 8:{Monster = new WillowTreeLabel(row); Monster.setBounds(1000, 70+98*row, 200, 150); break;}
                        }
                        BoardSyncData.SummonMonster();
                        bg.add(Monster);
                        switch(row){
                            case 0: {MonsterInLine0.add(Monster); break;}
                            case 1: {MonsterInLine1.add(Monster); break;}
                            case 2: {MonsterInLine2.add(Monster); break;}
                            case 3: {MonsterInLine3.add(Monster); break;}
                            case 4: {MonsterInLine4.add(Monster); break;}
                        }
                    
                    try{Thread.sleep((rand.nextInt(100)*10+5000)/(phase+1));}catch(Exception e){}
                   }
                   while(BoardSyncData.getMonsterRemaining()!=0&&active){try{Thread.sleep(100);}catch(Exception e){}}if(BoardSyncData.getMonsterRemaining()==0){active = false; killAll(); dispose(); PlayerData.passLv(); popWinframe();}
            }
        };
        Thread Map4Thread = new Thread(){
            public void run(){
                    backgroundImg = new MyImageIcon("resources/BG4.jpg").resize(1366, 618);
                    bg.setIcon(backgroundImg);
                    bg.setLayout(null);
                    validate();
                    
                    for(int i = 0; i < 10; i++)
                    {
                        BoardSyncData.Board[2][i].setWater(true);
                    }
                        BoardSyncData.setSummonedMonster(0);
                	BoardSyncData.setAllMonster(70);
                	BoardSyncData.setMonsterRemaining(70);
                 	while(BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()&&active){
                     	Random rand = new Random();
                    	int row = rand.nextInt(5);
                    	MonsterLabel Monster = new MonsterLabel();
                    	BoardSyncData.SummonMonster();
                    	int phase = 0;
                    	if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.2)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.5)){phase=1;}
                        else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.5)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()*(0.7)){phase=2;}
                    	else if(BoardSyncData.getSummonedMonster()>BoardSyncData.getAllMonster()*(0.7)&&BoardSyncData.getSummonedMonster()<BoardSyncData.getAllMonster()){phase=3;}
                    	switch(rand.nextInt(8) + phase){
                            case 0:{Monster = new MonsterIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 1:{Monster = new MonsterIIILabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 2:{Monster = new RedwithnobrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 3:{Monster = new GreenWithBrainLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 4:{Monster = new GirlWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75);break;}
                            case 5:{Monster = new UnicornWraithLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 6:{Monster = new MonsterIVLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 7:{Monster = new BigTrollTreeLabel(row); Monster.setBounds(1000, 98*row, 200, 150); break;}
                            case 8:{Monster = new WillowTreeLabel(row); Monster.setBounds(1000, 70+98*row, 200, 150); break;}
                            case 9:{Monster = new VioletaLabel(row); Monster.setBounds(1000, 70+98*row, 100, 75); break;}
                            case 10:{Monster = new BigTrollStreetLabel(row); Monster.setBounds(1000, 70+98*row, 200, 150); break;}
                        }
                        Monster.getML().setSpeed(Monster.getML().getSpeed() + 3);
                        bg.add(Monster);
                        switch(row){
                            case 0: {MonsterInLine0.add(Monster); break;}
                            case 1: {MonsterInLine1.add(Monster); break;}
                            case 2: {MonsterInLine2.add(Monster); break;}
                            case 3: {MonsterInLine3.add(Monster); break;}
                            case 4: {MonsterInLine4.add(Monster); break;}
                        }
                    try{Thread.sleep((rand.nextInt(100)*10+5000)/(phase+1));}catch(Exception e){}
                   }
                     while(BoardSyncData.getMonsterRemaining()!=0&&active){try{Thread.sleep(100);}catch(Exception e){}}if(BoardSyncData.getMonsterRemaining()==0){active = false; killAll(); dispose(); PlayerData.passLv(); popWinframe();}
            }
        };
        switch(PlayerData.getProgress()){
            case 0:{Map1Thread.start(); break;}
            case 2:{Map3Thread.start(); break;}
            case 3:{Map4Thread.start(); break;}
            case 1:{Map2Thread.start(); break;}

        }
        Thread UpdateEnergyThread = new Thread(){
        public void run(){
            while(active){
            energy = BoardSyncData.getEnergy();
            energyText.setText(String.valueOf(energy));
            try{Thread.sleep(100);RoundProgressBar.setValue( (BoardSyncData.getAllMonster()-BoardSyncData.getMonsterRemaining())*100/BoardSyncData.getAllMonster()) ;}catch(Exception e){}
                    }
        }
        };
        Thread SortMonsterThread = new Thread(){
            public void run(){
                while(active){
            
            MonsterInLine0.removeIf(arg -> arg.getML().isDead());
            MonsterInLine1.removeIf(arg -> arg.getML().isDead());
            MonsterInLine2.removeIf(arg -> arg.getML().isDead());
            MonsterInLine3.removeIf(arg -> arg.getML().isDead());
            MonsterInLine4.removeIf(arg -> arg.getML().isDead());
            
            if(MonsterInLine0.size()!=0)        Sorting(MonsterInLine0);
            
            if(MonsterInLine0.size()!=0)FirstMonster[0] = MonsterInLine0.get(0);
            else{FirstMonster[0]=null;}        
            Sorting(MonsterInLine1);
            if(MonsterInLine1.size()!=0)FirstMonster[1] = MonsterInLine1.get(0);
            else{FirstMonster[1]=null;}        
            Sorting(MonsterInLine2);
            if(MonsterInLine2.size()!=0)FirstMonster[2] = MonsterInLine2.get(0);
            else{FirstMonster[2]=null;}        
            Sorting(MonsterInLine3);
            if(MonsterInLine3.size()!=0)FirstMonster[3] = MonsterInLine3.get(0);
            else{FirstMonster[3]=null;}        
            Sorting(MonsterInLine4);
            if(MonsterInLine4.size()!=0)FirstMonster[4] = MonsterInLine4.get(0);
            else{FirstMonster[4]=null;}        
            BoardSyncData.UpdateMonster(FirstMonster);
            for(int i=0;i<5;++i){
                if(FirstMonster[i]!=null&&FirstMonster[i].getCurX()<-40){popLoseframe(); active = false; try{Thread.sleep(1000);}catch(Exception e){}killAll(); dispose();  break;}
            }
            try{Thread.sleep(100);}catch(Exception e){}
                }
                }
        };
        Thread UpdateInvadeThread = new Thread(){
            public void run()
            {
                while(active)
                {
                    for(int i = 0; i < 5; i++)
                    {
                        for(int j = 0; j < 10; j++)
                        {
                            BoardSyncData.Board[i][j].setInvaded(false);
                        }
                        int locate = 9;
                        if (BoardSyncData.FirstMonsterInLine[i] != null){
                        if (BoardSyncData.FirstMonsterInLine[i].getX() < 63) {
                            locate = 0;
                            
                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 63 && BoardSyncData.FirstMonsterInLine[i].getX() < 169) {
                            locate = 1;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 169 && BoardSyncData.FirstMonsterInLine[i].getX() < 275) {
                            locate = 2;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 275 && BoardSyncData.FirstMonsterInLine[i].getX() < 381) {
                            locate = 3;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 381 && BoardSyncData.FirstMonsterInLine[i].getX() < 487) {
                            locate = 4;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 487 && BoardSyncData.FirstMonsterInLine[i].getX() < 593) {
                            locate = 5;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 593 && BoardSyncData.FirstMonsterInLine[i].getX() < 699) {
                            locate = 6;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 699 && BoardSyncData.FirstMonsterInLine[i].getX() < 805) {
                            locate = 7;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 805 && BoardSyncData.FirstMonsterInLine[i].getX() < 911) {
                            locate = 8;

                        } else if (BoardSyncData.FirstMonsterInLine[i].getX() > 911 && BoardSyncData.FirstMonsterInLine[i].getX() < 1071) {
                            locate = 9;
                        }
                        for(int j = 9 - locate; j >= 0; --j)
                        {
                            BoardSyncData.Board[i][9-j].setInvaded(true);
                        }
                    }
                    }
                    try{Thread.sleep(100);}catch(Exception e){}
                }
            }
        };
        
        UpdateEnergyThread.start();
        SortMonsterThread.start();
        
        UpdateInvadeThread.start();
    }
    
    public GameFrame(Data d,ArrayList<Object> SRB){
        active=true;
        setTitle("Playing");
        setBounds(50,50,1366,768);
        Icon = new MyImageIcon("resources/icon.png");
        setIconImage( Icon.getImage() );
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e){
            themeSound.stop();
            killAll();
            new MainApplication(PlayerData);
        }
        });
        
        selectedrobot = SRB;
        cpane = (JPanel)getContentPane();
        cpane.setLayout(new BorderLayout());
        
        PlayerData = d;
        addComponent();
        startgame();
    }
    
    public void addComponent(){
        BoardSyncData.generatedBoard();
        RobotLabel[] slot = new RobotLabel[selectedrobot.size()];
        
        SelectTile = new Tile();
        backgroundImg = new MyImageIcon("resources/BG1.jpg").resize(1366, 618);
        bg = new JLabel();
        bg.setIcon(backgroundImg);
        bg.setLayout(null);
        JLabel BarLabel = new JLabel("Progression :");
        BarLabel.setBounds(0, 0, 100, 10);
        RoundProgressBar = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
        RoundProgressBar.setBounds(1000, 600, 300, 50);
        RoundProgressBar.setValue(0);
        RoundProgressBar.setStringPainted(false);
        themeSound = new MySoundEffect("resources/sound/theme.wav");
        themeSound.playLoop();
        
        for(int i=0;i<selectedrobot.size();++i){
            switch(selectedrobot.get(i).getClass().getName()){
                case "RobotWalker1Label"  :{slot[i] = new RobotWalker1Label(1); break;}
                case "SolarBotLabel"    :{slot[i] = new SolarBotLabel(1); break;}
                case "BlueBotLabel"     :{slot[i] = new BlueBotLabel(1); break;}
                case "ShipBotLabel"     :{slot[i] = new ShipBotLabel(1); break;}
                case "RadioRobotLabel"    :{slot[i] = new RadioRobotLabel(1); break;}
                case "TankBotLabel"       :{slot[i] = new TankBotLabel(1); break;}
                case "RobotWalker2Label"  :{slot[i] = new RobotWalker2Label(1); break;}
                case "RobotWalker3Label"  :{slot[i] = new RobotWalker3Label(1); break;}
                case "RobotWalker4Label"  :{slot[i] = new RobotWalker4Label(1); break;}
                case "RobotWalker5Label"  :{slot[i] = new RobotWalker5Label(1); break;}
            }
        }
        for(int i=0;i<selectedrobot.size();++i){
        if(slot[i]!=null){
        slot[i].addMouseMotionListener(new MyMouseMotionAdapter(slot[i]));
        slot[i].addMouseListener(new MyMouseAdapter(slot[i]));        }
        
        }
        selectingTile = new TileLabel("resources/BGTile/white.jpg"); 
        selectingTile.setBounds(0, 114, 106, 98);
        
        EnergyLabel energyLabel = new EnergyLabel();
        BoardSyncData.setEnergy(500*(PlayerData.getProgress()+1));
        energyText = new JTextField(String.valueOf(500*(PlayerData.getProgress()+1)), 10);		
	energyText.setEditable(false);
        energyText.setEnabled(false);
        
        
        JPanel upane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        upane.setBackground(new Color(68,148,162));
        upane.add(energyLabel);
        upane.add(energyText);
        for(int i=0;i<selectedrobot.size();++i){
            if(slot[i]!=null)upane.add(slot[i]);
        }
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            themeSound.stop();
            new MainApplication(PlayerData);
            active=false;
            killAll();
            dispose();
        }});
        upane.add(back);
        upane.add(bg);
        cpane.add(upane);

        bg.add(selectingTile);
        JPanel sp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sp.add(BarLabel);
        sp.add(RoundProgressBar);
        
        
        
        
        
        cpane.add(sp,BorderLayout.SOUTH);
        
        
        validate();
    }
    
        void Deploy(String rbt, Tile t) {
   	 
    	switch (rbt) {
        	case "RobotWalker1Label":
            	RobotWalker1Label t1 = new RobotWalker1Label(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (t1.getRobotData().getCost() + 200)) {
                    	RobotWalker1Label rw1 = new RobotWalker1Label(bg,1);
                    	rw1.setCurX(t.getCurX());
                    	rw1.setCurY(t.getCurY() - 40);
                    	rw1.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rw1);
                    	bg.add(rw1);
                    	BoardSyncData.spendEnergy((t1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= t1.getRobotData().getCost()) {
                	RobotWalker1Label rw1 = new RobotWalker1Label(bg);
                	rw1.setCurX(t.getCurX());
                	rw1.setCurY(t.getCurY() - 40);
                	rw1.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rw1);
                	bg.add(rw1);
                	BoardSyncData.spendEnergy(t1.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "RobotWalker2Label":
            	RobotWalker2Label t2 = new RobotWalker2Label(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (t2.getRobotData().getCost() + 200)) {
                    	RobotWalker2Label rw2 = new RobotWalker2Label(bg,1);
                    	rw2.setCurX(t.getCurX());
                    	rw2.setCurY(t.getCurY() - 40);
                    	rw2.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rw2);
                    	bg.add(rw2);
                    	BoardSyncData.spendEnergy((t2.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= t2.getRobotData().getCost()) {
                	RobotWalker2Label rw2 = new RobotWalker2Label(bg);
                	rw2.setCurX(t.getCurX());
                	rw2.setCurY(t.getCurY() - 40);
                	rw2.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rw2);
                	bg.add(rw2);
                	BoardSyncData.spendEnergy(t2.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "RobotWalker3Label":
            	RobotWalker3Label t3 = new RobotWalker3Label(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (t3.getRobotData().getCost() + 200)) {
                    	RobotWalker3Label rw3 = new RobotWalker3Label(bg,1);
                    	rw3.setCurX(t.getCurX());
                    	rw3.setCurY(t.getCurY() - 40);
                    	rw3.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rw3);
                    	bg.add(rw3);
                    	BoardSyncData.spendEnergy((t3.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= t3.getRobotData().getCost()) {
                	RobotWalker3Label rw3 = new RobotWalker3Label(bg);
                	rw3.setCurX(t.getCurX());
                	rw3.setCurY(t.getCurY() - 40);
                	rw3.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rw3);
                	bg.add(rw3);
                	BoardSyncData.spendEnergy(t3.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "RobotWalker4Label":
            	RobotWalker4Label t4 = new RobotWalker4Label(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (t4.getRobotData().getCost() + 200)) {
                    	RobotWalker4Label rw4 = new RobotWalker4Label(bg,1);
                    	rw4.setCurX(t.getCurX());
                    	rw4.setCurY(t.getCurY() - 40);
                    	rw4.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rw4);
                    	bg.add(rw4);
                    	BoardSyncData.spendEnergy((t4.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= t4.getRobotData().getCost()) {
                	RobotWalker4Label rw4 = new RobotWalker4Label(bg);
                	rw4.setCurX(t.getCurX());
                	rw4.setCurY(t.getCurY() - 40);
                	rw4.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rw4);
                	bg.add(rw4);
                	BoardSyncData.spendEnergy(t4.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "RobotWalker5Label":
            	RobotWalker5Label t5 = new RobotWalker5Label(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (t5.getRobotData().getCost() + 200)) {
                    	RobotWalker5Label rw5 = new RobotWalker5Label(bg,1);
                    	rw5.setCurX(t.getCurX());
                    	rw5.setCurY(t.getCurY() - 40);
                    	rw5.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rw5);
                    	bg.add(rw5);
                    	BoardSyncData.spendEnergy((t5.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= t5.getRobotData().getCost()) {
                	RobotWalker5Label rw5 = new RobotWalker5Label(bg);
                	rw5.setCurX(t.getCurX());
                	rw5.setCurY(t.getCurY() - 40);
                	rw5.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rw5);
                	bg.add(rw5);
                	BoardSyncData.spendEnergy(t5.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "SolarBotLabel":
            	SolarBotLabel sb1 = new SolarBotLabel(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (sb1.getRobotData().getCost() + 200)) {
                    	SolarBotLabel sb = new SolarBotLabel(bg,1);
                    	sb.setCurX(t.getCurX());
                    	sb.setCurY(t.getCurY() - 40);
                    	sb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(sb);
                    	bg.add(sb);
                    	BoardSyncData.spendEnergy((sb1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= sb1.getRobotData().getCost()) {
                	SolarBotLabel sb = new SolarBotLabel(bg);
                	sb.setCurX(t.getCurX());
                	sb.setCurY(t.getCurY() - 40);
                	sb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(sb);
                	bg.add(sb);
                	BoardSyncData.spendEnergy(sb1.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "RadioRobotLabel":
            	RadioRobotLabel rrb1 = new RadioRobotLabel(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (rrb1.getRobotData().getCost() + 200)) {
                    	RadioRobotLabel rrb = new RadioRobotLabel(bg,1);
                    	rrb.setCurX(t.getCurX());
                    	rrb.setCurY(t.getCurY() - 40);
                    	rrb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(rrb);
                    	bg.add(rrb);
                    	BoardSyncData.spendEnergy((rrb1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= rrb1.getRobotData().getCost()) {
                	RadioRobotLabel rrb = new RadioRobotLabel(bg);
                	rrb.setCurX(t.getCurX());
                	rrb.setCurY(t.getCurY() - 40);
                	rrb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(rrb);
                	bg.add(rrb);
                	BoardSyncData.spendEnergy(rrb1.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "BlueBotLabel":
            	BlueBotLabel brb1 = new BlueBotLabel(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (brb1.getRobotData().getCost() + 200)) {
                    	BlueBotLabel brb = new BlueBotLabel(bg,1);
                    	brb.setCurX(t.getCurX());
                    	brb.setCurY(t.getCurY() - 40);
                    	brb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(brb);
                    	bg.add(brb);
                    	BoardSyncData.spendEnergy((brb1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= brb1.getRobotData().getCost()) {
                	BlueBotLabel brb = new BlueBotLabel(bg);
                	brb.setCurX(t.getCurX());
                	brb.setCurY(t.getCurY() - 40);
                	brb.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(brb);
                	bg.add(brb);
                	BoardSyncData.spendEnergy(brb1.getRobotData().getCost());
                	t.setDeployable(false);
    	t.setDeployed(true);
            	}
            	break;
        	case "ShipBotLabel":
            	ShipBotLabel s1 = new ShipBotLabel(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (s1.getRobotData().getCost() + 200)) {
                    	ShipBotLabel s = new ShipBotLabel(bg);
                    	s.setCurX(t.getCurX());
                    	s.setCurY(t.getCurY() - 40);
                    	s.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                    	t.setRobot(s);
                    	bg.add(s);
                    	t.setDeployable(true);
                    	t.setDeployed(false);
                    	BoardSyncData.spendEnergy((s1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= s1.getRobotData().getCost()) {
                	ShipBotLabel s = new ShipBotLabel(bg);
                	s.setCurX(t.getCurX());
                	s.setCurY(t.getCurY() - 40);
                	s.setBounds(t.getCurX() - 50, t.getCurY() - 38, 100, 75);
                	t.setRobot(s);
                	bg.add(s);
                	t.setDeployable(true);
                	t.setDeployed(false);
                	BoardSyncData.spendEnergy(s1.getRobotData().getCost());
            	}
            	break;
        	case "TankBotLabel":
            	TankBotLabel tb1 = new TankBotLabel(1);
            	if (t.getWater()) {
                	if (BoardSyncData.getEnergy() >= (tb1.getRobotData().getCost() + 200)) {
                    	TankBotLabel tb = new TankBotLabel(bg);
                    	tb.setCurX(t.getCurX());
                    	tb.setCurY(t.getCurY() - 40);
                    	tb.setBounds(t.getCurX() - 50, tb.getCurY() - 38, 100, 75);
                    	t.setRobot(tb);
                    	bg.add(tb);
                    	t.setDeployable(true);
                    	t.setDeployed(false);
                    	BoardSyncData.spendEnergy((tb1.getRobotData().getCost() + 200));
                    	t.setDeployable(false);
    	t.setDeployed(true);
                	}
            	} else if (BoardSyncData.getEnergy() >= tb1.getRobotData().getCost()) {
                	TankBotLabel tb = new TankBotLabel(bg);
                	tb.setCurX(t.getCurX());
                	tb.setCurY(t.getCurY() - 40);
                	tb.setBounds(t.getCurX() - 50, tb.getCurY() - 38, 100, 75);
                	t.setRobot(tb);
                	bg.add(tb);
                	t.setDeployable(true);
                	t.setDeployed(false);
                	BoardSyncData.spendEnergy(tb1.getRobotData().getCost());
            	}
            	break;
    	}
    		}




    
    
    
    class MyMouseAdapter extends MouseAdapter{
        private RobotLabel RB;
        public MyMouseAdapter(RobotLabel R){RB =R;}
        public void mousePressed(MouseEvent e){
            RB.setSCurX(RB.getAlignmentX());
            RB.setSCurY(RB.getAlignmentY());
            
        }
        public void mouseReleased(MouseEvent e){
            RB.setLocation((int)RB.getSCurX(), (int)RB.getSCurY());
            selectingTile.setVisible(false);
            if(SelectTile.getAble()&&SelectTile.isFocus()){
                Deploy(RB.getClass().getName(),SelectTile);
            }
            SelectTile.setFocus(false);
            validate();
        }
    }
    
    class MyMouseMotionAdapter extends MouseMotionAdapter{
        private RobotLabel RB;
        public MyMouseMotionAdapter(RobotLabel R){RB =R;}
        public void mouseDragged(MouseEvent e){
            
            RB.setCurX(RB.getHorizontalAlignment()+e.getX()-50);
            RB.setCurY(RB.getVerticalAlignment()+e.getY()-38);
            RB.setLocation(RB.getCurX(),RB.getCurY());
            if(RB.getCurY()>147&&RB.getCurY()<243){
                if(RB.getCurX()>-43&&RB.getCurX()<63&&(!BoardSyncData.Board[0][0].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][0].getCurX()-53, BoardSyncData.Board[0][0].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][0];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>63&&RB.getCurX()<169&&(!BoardSyncData.Board[0][1].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][1].getCurX()-53, BoardSyncData.Board[0][1].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][1];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>169&&RB.getCurX()<275&&(!BoardSyncData.Board[0][2].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][2].getCurX()-53, BoardSyncData.Board[0][2].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][2];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>275&&RB.getCurX()<381&&(!BoardSyncData.Board[0][3].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][3].getCurX()-53, BoardSyncData.Board[0][3].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][3];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>381&&RB.getCurX()<487&&(!BoardSyncData.Board[0][4].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][4].getCurX()-53, BoardSyncData.Board[0][4].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][4];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>487&&RB.getCurX()<593&&(!BoardSyncData.Board[0][5].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][5].getCurX()-53, BoardSyncData.Board[0][5].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][5];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>593&&RB.getCurX()<699&&(!BoardSyncData.Board[0][6].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][6].getCurX()-53, BoardSyncData.Board[0][6].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][6];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>699&&RB.getCurX()<805&&(!BoardSyncData.Board[0][7].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][7].getCurX()-53, BoardSyncData.Board[0][7].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][7];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>805&&RB.getCurX()<911&&(!BoardSyncData.Board[0][8].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][8].getCurX()-53, BoardSyncData.Board[0][8].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][8];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>911&&RB.getCurX()<1071&&(!BoardSyncData.Board[0][9].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[0][9].getCurX()-53, BoardSyncData.Board[0][9].getCurY()-49);
                    SelectTile = BoardSyncData.Board[0][9];
                    SelectTile.setFocus(true);
                    
                }
            }
            
            else if(RB.getCurY()>243&&RB.getCurY()<341){
                if(RB.getCurX()>-43&&RB.getCurX()<63&&(!BoardSyncData.Board[1][0].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][0].getCurX()-53, BoardSyncData.Board[1][0].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][0];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>63&&RB.getCurX()<169&&(!BoardSyncData.Board[1][1].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][1].getCurX()-53, BoardSyncData.Board[1][1].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][1];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>169&&RB.getCurX()<275&&(!BoardSyncData.Board[1][2].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][2].getCurX()-53, BoardSyncData.Board[1][2].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][2];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>275&&RB.getCurX()<381&&(!BoardSyncData.Board[1][3].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][3].getCurX()-53, BoardSyncData.Board[1][3].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][3];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>381&&RB.getCurX()<487&&(!BoardSyncData.Board[1][4].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][4].getCurX()-53, BoardSyncData.Board[1][4].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][4];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>487&&RB.getCurX()<593&&(!BoardSyncData.Board[1][5].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][5].getCurX()-53, BoardSyncData.Board[1][5].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][5];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>593&&RB.getCurX()<699&&(!BoardSyncData.Board[1][6].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][6].getCurX()-53, BoardSyncData.Board[1][6].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][6];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>699&&RB.getCurX()<805&&(!BoardSyncData.Board[1][7].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][7].getCurX()-53, BoardSyncData.Board[1][7].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][7];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>805&&RB.getCurX()<911&&(!BoardSyncData.Board[1][8].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][8].getCurX()-53, BoardSyncData.Board[1][8].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][8];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>911&&RB.getCurX()<1071&&(!BoardSyncData.Board[1][9].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[1][9].getCurX()-53, BoardSyncData.Board[1][9].getCurY()-49);
                    SelectTile = BoardSyncData.Board[1][9];
                    SelectTile.setFocus(true);
                    
                }
            }
            else if(RB.getCurY()>341&&RB.getCurY()<439){
                if(RB.getCurX()>-43&&RB.getCurX()<63&&(!BoardSyncData.Board[2][0].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][0].getCurX()-53, BoardSyncData.Board[2][0].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][0];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>63&&RB.getCurX()<169&&(!BoardSyncData.Board[2][1].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][1].getCurX()-53, BoardSyncData.Board[2][1].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][1];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>169&&RB.getCurX()<275&&(!BoardSyncData.Board[2][2].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][2].getCurX()-53, BoardSyncData.Board[2][2].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][2];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>275&&RB.getCurX()<381&&(!BoardSyncData.Board[2][3].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][3].getCurX()-53, BoardSyncData.Board[2][3].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][3];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>381&&RB.getCurX()<487&&(!BoardSyncData.Board[2][4].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][4].getCurX()-53, BoardSyncData.Board[2][4].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][4];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>487&&RB.getCurX()<593&&(!BoardSyncData.Board[2][5].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][5].getCurX()-53, BoardSyncData.Board[2][5].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][5];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>593&&RB.getCurX()<699&&(!BoardSyncData.Board[2][6].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][6].getCurX()-53, BoardSyncData.Board[2][6].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][6];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>699&&RB.getCurX()<805&&(!BoardSyncData.Board[2][7].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][7].getCurX()-53, BoardSyncData.Board[2][7].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][7];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>805&&RB.getCurX()<911&&(!BoardSyncData.Board[2][8].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][8].getCurX()-53, BoardSyncData.Board[2][8].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][8];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>911&&RB.getCurX()<1071&&(!BoardSyncData.Board[2][9].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[2][9].getCurX()-53, BoardSyncData.Board[2][9].getCurY()-49);
                    SelectTile = BoardSyncData.Board[2][9];
                    SelectTile.setFocus(true);
                    
                }
            }
            else if(RB.getCurY()>439&&RB.getCurY()<537){
                if(RB.getCurX()>-43&&RB.getCurX()<63&&(!BoardSyncData.Board[3][0].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][0].getCurX()-53, BoardSyncData.Board[3][0].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][0];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>63&&RB.getCurX()<169&&(!BoardSyncData.Board[3][1].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][1].getCurX()-53, BoardSyncData.Board[3][1].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][1];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>169&&RB.getCurX()<275&&(!BoardSyncData.Board[3][2].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][2].getCurX()-53, BoardSyncData.Board[3][2].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][2];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>275&&RB.getCurX()<381&&(!BoardSyncData.Board[3][3].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][3].getCurX()-53, BoardSyncData.Board[3][3].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][3];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>381&&RB.getCurX()<487&&(!BoardSyncData.Board[3][4].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][4].getCurX()-53, BoardSyncData.Board[3][4].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][4];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>487&&RB.getCurX()<593&&(!BoardSyncData.Board[3][5].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][5].getCurX()-53, BoardSyncData.Board[3][5].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][5];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>593&&RB.getCurX()<699&&(!BoardSyncData.Board[3][6].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][6].getCurX()-53, BoardSyncData.Board[3][6].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][6];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>699&&RB.getCurX()<805&&(!BoardSyncData.Board[3][7].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][7].getCurX()-53, BoardSyncData.Board[3][7].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][7];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>805&&RB.getCurX()<911&&(!BoardSyncData.Board[3][8].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][8].getCurX()-53, BoardSyncData.Board[3][8].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][8];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>911&&RB.getCurX()<1071&&(!BoardSyncData.Board[3][9].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[3][9].getCurX()-53, BoardSyncData.Board[3][9].getCurY()-49);
                    SelectTile = BoardSyncData.Board[3][9];
                    SelectTile.setFocus(true);
                    
                }
            }
            else if(RB.getCurY()>537&&RB.getCurY()<635){
                if(RB.getCurX()>-43&&RB.getCurX()<63&&(!BoardSyncData.Board[4][0].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][0].getCurX()-53, BoardSyncData.Board[4][0].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][0];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>63&&RB.getCurX()<169&&(!BoardSyncData.Board[4][1].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][1].getCurX()-53, BoardSyncData.Board[4][1].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][1];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>169&&RB.getCurX()<275&&(!BoardSyncData.Board[4][2].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][2].getCurX()-53, BoardSyncData.Board[4][2].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][2];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>275&&RB.getCurX()<381&&(!BoardSyncData.Board[4][3].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][3].getCurX()-53, BoardSyncData.Board[4][3].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][3];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>381&&RB.getCurX()<487&&(!BoardSyncData.Board[4][4].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][4].getCurX()-53, BoardSyncData.Board[4][4].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][4];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>487&&RB.getCurX()<593&&(!BoardSyncData.Board[4][5].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][5].getCurX()-53, BoardSyncData.Board[4][5].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][5];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>593&&RB.getCurX()<699&&(!BoardSyncData.Board[4][6].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][6].getCurX()-53, BoardSyncData.Board[4][6].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][6];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>699&&RB.getCurX()<805&&(!BoardSyncData.Board[4][7].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][7].getCurX()-53, BoardSyncData.Board[4][7].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][7];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>805&&RB.getCurX()<911&&(!BoardSyncData.Board[4][8].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][8].getCurX()-53, BoardSyncData.Board[4][8].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][8];
                    SelectTile.setFocus(true);
                    
                }
                else if(RB.getCurX()>911&&RB.getCurX()<1071&&(!BoardSyncData.Board[4][9].getInvaded())){
                    selectingTile.setVisible(true);
                    selectingTile.setLocation(BoardSyncData.Board[4][9].getCurX()-53, BoardSyncData.Board[4][9].getCurY()-49);
                    SelectTile = BoardSyncData.Board[4][9];
                    SelectTile.setFocus(true);
                    
                }
            }
            else{
                selectingTile.setLocation(-200,-200);
                SelectTile.setFocus(false);
            }
        }
}
     
    
}
