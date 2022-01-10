/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
public class Data {
    private int game_progress;
    public Data(int gp){game_progress=gp; }
    public void passLv(){game_progress+=1;}
    public int getProgress(){return game_progress;}
    public void resetLv(){game_progress = -1;}
}
class BoardSyncData{  
    public static Tile[][] Board;
    public static MonsterLabel[] FirstMonsterInLine;
    private static int energy,MonsterRemaining,AllMonster,SummonedMonster;
    public static int getEnergy(){return energy;}
    public static void setEnergy(int e){energy =e;}
    public static void spendEnergy(int e){energy -=e;}
    public static int getMonsterRemaining(){return MonsterRemaining;}
    public static int getAllMonster(){return AllMonster;}
    public static int getSummonedMonster(){return SummonedMonster;}
    public static void setMonsterRemaining(int mr){MonsterRemaining = mr;}
    public static void setAllMonster(int mr){AllMonster = mr;}
    public static void setSummonedMonster(int mr){SummonedMonster = mr;}
    public static void SummonMonster(){SummonedMonster+=1;}
    public static synchronized void monsterRemainingDecrease(){MonsterRemaining-=1;}
    public static void generatedBoard(){
        Board = new Tile[5][11];
        for(int i=0;i<5;++i){
            for(int j=0;j<11;++j){
                BoardSyncData.Board[i][j] = new Tile();
                BoardSyncData.Board[i][j].setCur((int)(53+106*(j)), (int)(163+(i*98)));
            }
        }
        FirstMonsterInLine = new MonsterLabel[5];
        FirstMonsterInLine[0] = new MonsterLabel();
        FirstMonsterInLine[1] = new MonsterLabel();
        FirstMonsterInLine[2] = new MonsterLabel();
        FirstMonsterInLine[3] = new MonsterLabel();
        FirstMonsterInLine[4] = new MonsterLabel();
    }

    public static void UpdateMonster(MonsterLabel[] m){
        FirstMonsterInLine[0] = m[0];
        FirstMonsterInLine[1] = m[1];
        FirstMonsterInLine[2] = m[2];
        FirstMonsterInLine[3] = m[3];
        FirstMonsterInLine[4] = m[4];
    }
    synchronized public static void addEnergy(){
    energy+=50;
}
}


class Tile{
    private boolean Deployable;
    private boolean Deployed;
    private boolean Invaded;
    private boolean Water;
    private boolean Graved;
    private boolean Focus;
    private int CurX,CurY;
    private RobotLabel Robot;
    public void setCur(int x,int y){CurX=x; CurY=y;}
    public void setFocus(boolean b){Focus = b;}
    public void setGraved(boolean b){Graved = b;}
    public void setDeployable(boolean b){Deployable = b;}
    public void setDeployed(boolean b){Deployed = b;}
    public void setRobot(RobotLabel RB){Robot=RB;}
    public boolean getAble(){return Deployable;}
    public boolean isDeployed(){return Deployed;}
    public boolean isGraved(){return Graved;}
    public void setInvaded(boolean b) {Invaded = b;}
    public boolean getInvaded() {return Invaded;}
    public void setWater(boolean b) {Water = b;}
    public boolean getWater() {return Water;}
    public boolean isFocus(){return Focus;}
    public int getCurX(){return CurX;}
    public int getCurY(){return CurY;}
    public RobotLabel getRobotLabel(){return Robot;}
    public Tile(){
        Deployable = true;
        Deployed = false;
        Invaded = false;
        Graved = false;
        CurX=0;CurY=0;
        Focus = false;
        Water = false;
    }
}

class TileLabel extends JLabel{
    private MyImageIcon img;
    public TileLabel(String n){
        img = new MyImageIcon(n).resize(106,110);
        setIcon(img);
    }
    
}

class Robot {
    private int range;
    private int dmg;
    private int health;
    private int fire_rate;
    private int order;
    private int cost;
    public int getHealth(){return health;}
    public int getDmg(){return dmg;}
    public int getRange(){return range;}
    public int getCost(){return cost;}
    public int getFireR(){return fire_rate;}
    public void hurt(int d){health-=d;}
    public boolean isDead(){return health<=0;}
    public Robot(int r,int d, int h,int f,int o,int c){
        range = r; dmg= d; health = h; fire_rate = f; order =o; cost =c;
    }
}

class Monster {
    private int speed;
    private int dmg;
    private int health;
    private int order;
    private boolean stunned;
    public Monster(int s,int d, int h,int o){
        speed =s; dmg = d; health =h; order =o;
        stunned = false;
    }
    public void hurt(int d){health-=d;}
    public boolean isDead(){return health<=0;}
    public int getDmg(){return dmg;}
    public int getSpeed(){return speed;}
    public boolean isStunned(){return stunned;}
    public void Stunned(){stunned =true;}
    public void ReStunned(){stunned =false;}
    public void setSpeed(int s){speed = s;}
}

class RobotLabel extends JLabel{
    private Robot robotdata;
    private boolean Choice;
    private int CurX,CurY;
    private float SCurX,SCurY;
    public JLabel bg;
    public int getCurX(){return CurX;}
    public void setCurX(int x){CurX+=x;}
    public int getCurY(){return CurY;}
    public void setCurY(int x){CurY+=x;}
    public float getSCurX(){return SCurX;}
    public void setSCurX(float x){SCurX=x;}
    public float getSCurY(){return SCurY;}
    public void setSCurY(float x){SCurY=x;}
    public Robot getRobot(){return robotdata;}
    public RobotLabel(Robot r){robotdata = r;}
    public void copy(RobotLabel RB){CurX = RB.CurX; CurY = RB.CurY; SCurX=RB.SCurX; SCurY = RB.SCurY; robotdata=RB.robotdata;}
    public boolean inrange(int range){
        
        
        switch(getCurY()){
            case 123:{if(BoardSyncData.FirstMonsterInLine[0]!=null) {return BoardSyncData.FirstMonsterInLine[0].getX()-this.getCurX()<=range*106;}else{break;}}
            case 221:{if(BoardSyncData.FirstMonsterInLine[1]!=null) {return BoardSyncData.FirstMonsterInLine[1].getX()-this.getCurX()<=range*106;}else{break;}}
            case 319:{if(BoardSyncData.FirstMonsterInLine[2]!=null) {return BoardSyncData.FirstMonsterInLine[2].getX()-this.getCurX()<=range*106;}else{break;}}
            case 417:{if(BoardSyncData.FirstMonsterInLine[3]!=null) {return BoardSyncData.FirstMonsterInLine[3].getX()-this.getCurX()<=range*106;}else{break;}}
            case 515:{if(BoardSyncData.FirstMonsterInLine[4]!=null) {return BoardSyncData.FirstMonsterInLine[4].getX()-this.getCurX()<=range*106;}else{break;}}
        }
        return false;
    }
    public void Shoot(int type){
        switch(type){
            case 0: {defaultFiring(this,0); break;}
            case 1: {new EnergyLabel(this); break;}
            case 2: {shockingFire(this); break;}
            case 3: {defaultFiring(this,2); break;}
        }
        

}
    void defaultFiring(RobotLabel RB,int type){
        Thread BThread = new Thread(){
            public void run(){
                int CurX,CurY;
                CurX = RB.CurX;
                CurY = RB.CurY;

                BulletLabel BLB = new BulletLabel(type);
                BLB.setBounds(CurX, CurY+15, 75, 50);
                bg.add(BLB);
                while(CurX<1366){
                    CurX+=2;
                    BLB.setLocation(CurX, CurY+15);
                    int row;
                    row = (CurY-163+40)/98;
                    if(BoardSyncData.FirstMonsterInLine[row]!=null&&BLB.getBounds().intersects(BoardSyncData.FirstMonsterInLine[row].getBounds())){
                        BLB.setVisible(false);
                        bg.remove(BLB);
                        BoardSyncData.FirstMonsterInLine[row].getML().hurt(RB.getRobot().getDmg());
                        break;
                    }
                    repaint();
                    try{Thread.sleep(10);}catch(Exception e){}
                }
                BLB.setVisible(false);
                bg.remove(BLB);       
            }
        };
        BThread.start();
    }
    void shockingFire(RobotLabel RB){
        Thread BThread = new Thread(){
            public void run(){
                int CurX,CurY;
                CurX = RB.CurX;
                CurY = RB.CurY;

                BulletLabel BLB = new BulletLabel(1);
                BLB.setBounds(CurX, CurY, 530, 75);
                bg.add(BLB);
                validate();
                repaint();
                
                int row;
                row = (CurY-163+40)/98;
                if(BoardSyncData.FirstMonsterInLine[row]!=null&&BLB.getBounds().intersects(BoardSyncData.FirstMonsterInLine[row].getBounds())){
                        BoardSyncData.FirstMonsterInLine[row].getML().Stunned();}
                try{Thread.sleep(2000);}catch(Exception e){}
                BLB.setVisible(false);
                bg.remove(BLB);
                validate();
                repaint();
            }
        };
        BThread.start();
    }
}
class BulletLabel extends JLabel{
    private MyImageIcon img;
    public BulletLabel(int type){
        switch(type){
            case 0:{img = new MyImageIcon("resources/Robot/ef_ball1.png").resize(50, 30); break;}
            case 1:{img = new MyImageIcon("resources/Robot/ef_light.png").resize(530, 75); break;}
            case 2:{img = new MyImageIcon("resources/Robot/ef_shoot.png").resize(75, 50); break;}
        }
        
        setHorizontalAlignment(JLabel.CENTER);
        setIcon(img);
    }
}
class RobotWalker1Label extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RobotWalker1Label(int i){
    	super( new Robot(11, 5,20, 5, 1,200));
    	if(i==0){Img = new MyImageIcon("resources/Robot/1robotshoot.png").resize(100, 75);}
    	else{Img = new MyImageIcon("resources/Robot/ShootingPrice.png").resize(100, 75);}
    	setIcon(Img);
	}
	public RobotWalker1Label(JLabel b){
    	super( new Robot(11, 5,20, 5, 1,200));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1robotshoot.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){

                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(2500);}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RobotWalker1Label(JLabel b, int i){
    	super( new Robot(11, 5,20, 5, 1,200));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/RobotWalker1-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){

                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(2500);}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class RobotWalker2Label extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RobotWalker2Label(int i){
    	super( new Robot(10, 10,40, 30, 1,300));
    	if(i==0){Img = new MyImageIcon("resources/Robot/1roborball.png").resize(100, 75);}
    	else{Img = new MyImageIcon("resources/Robot/MercuryPrice.png").resize(100, 75);}
   	 
    	setIcon(Img);
	}
	public RobotWalker2Label(JLabel b){
    	super( new Robot(10, 10,40, 30, 1,300));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1roborball.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}
            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RobotWalker2Label(JLabel b,int i){
    	super( new Robot(10, 10,40, 30, 1,300));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1roborball-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class RobotWalker3Label extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RobotWalker3Label(int i){
    	super( new Robot(10, 20,60, 50, 1,400));
    	if(i==0){Img = new MyImageIcon("resources/Robot/2robotdoubleshoot.png").resize(100, 75);}
    	else{Img = new MyImageIcon("resources/Robot/BlitzPrice.png").resize(100, 75);}
    	setIcon(Img);
	}
	public RobotWalker3Label(JLabel b){
    	super( new Robot(10, 20,60, 50, 1,400));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/2robotdoubleshoot.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
                	if(inrange(getRobotData().getRange())){Shoot(3); try{Thread.sleep(500);}catch(Exception e){}Shoot(3);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}
            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RobotWalker3Label(JLabel b, int i){
    	super( new Robot(10, 20,60, 50, 1,400));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/2robotdoubleshoot-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
                	if(inrange(getRobotData().getRange())){Shoot(3); try{Thread.sleep(500);}catch(Exception e){}Shoot(3);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class RobotWalker4Label extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RobotWalker4Label(int i){
    	super( new Robot(10, 30,70, 30, 1,500));
    	if(i==0){Img = new MyImageIcon("resources/Robot/3robotball.png").resize(100, 75);}
    	else{Img = new MyImageIcon("resources/Robot/GollumPrice.png").resize(100, 75);}
    	setIcon(Img);
	}
	public RobotWalker4Label(JLabel b){
    	super( new Robot(10, 30,70, 30, 1,500));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/3robotball.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
                	if(inrange(getRobotData().getRange())){Shoot(3);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RobotWalker4Label(JLabel b, int i){
    	super( new Robot(10, 30,70, 30, 1,500));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/3robotball-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){

                	if(inrange(getRobotData().getRange())){Shoot(3);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class RobotWalker5Label extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RobotWalker5Label(int i){
    	super( new Robot(10, 40,80, 30, 1,500));
    	if(i==0){Img = new MyImageIcon("resources/Robot/4doubleshoot.png").resize(100, 75);}
    	else{Img = new MyImageIcon("resources/Robot/FalconPrice.png").resize(100, 75);}
   	 
    	setIcon(Img);
	}
	public RobotWalker5Label(JLabel b){
    	super( new Robot(10, 40,80, 30, 1,500));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/4doubleshoot.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){

                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RobotWalker5Label(JLabel b, int i){
    	super( new Robot(10, 40,80, 30, 1,500));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/4doubleshoot-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){

                	if(inrange(getRobotData().getRange())){Shoot(0);}
                	try{Thread.sleep(100000/getRobotData().getFireR());}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
}




class ShipBotLabel extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public ShipBotLabel(int i){
    	super( new Robot(11, 100,1, 1, 5,800));
    	if(i==0)Img = new MyImageIcon("resources/Robot/1robotship.png").resize(100, 75);
    	else Img = new MyImageIcon("resources/Robot/ShipPrice.png").resize(100, 75);
    	setIcon(Img);
	}
	public ShipBotLabel(JLabel b){
    	super( new Robot(11, 100,1,1, 5,800));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1robotship.png").resize(100, 75);
    	setIcon(Img);
    	setVisible(false);
    	Thread t = new Thread(){
        	public void run(){
            	ShipBotLabel SBL = new ShipBotLabel(0);
            	int x,y;
            	x = getCurX();
            	y = getCurY();
            	SBL.setBounds(x,y,100,75);
            	bg.add(SBL);
            	while(x<1366){

                	x+=5;
                	switch(y){
                    	case 123:{if(BoardSyncData.FirstMonsterInLine[0]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[0].getBounds())){BoardSyncData.FirstMonsterInLine[0].getML().hurt(100); SBL.setVisible(false); x=1367; } break;}
                    	case 221:{if(BoardSyncData.FirstMonsterInLine[1]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[1].getBounds())){BoardSyncData.FirstMonsterInLine[1].getML().hurt(100); SBL.setVisible(false); x=1367; } break;}
                    	case 319:{if(BoardSyncData.FirstMonsterInLine[2]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[2].getBounds())){BoardSyncData.FirstMonsterInLine[2].getML().hurt(100); SBL.setVisible(false); x=1367; } break;}
                    	case 417:{if(BoardSyncData.FirstMonsterInLine[3]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[3].getBounds())){BoardSyncData.FirstMonsterInLine[3].getML().hurt(100); SBL.setVisible(false); x=1367; } break;}
                    	case 515:{if(BoardSyncData.FirstMonsterInLine[4]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[4].getBounds())){BoardSyncData.FirstMonsterInLine[4].getML().hurt(100); SBL.setVisible(false); x=1367; } break;}
                	}
               	 
                	SBL.setLocation(x,y);
                	validate(); repaint();
                	try{Thread.sleep(10);}catch(Exception e){}
               	 
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class TankBotLabel extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public TankBotLabel(int i){
    	super( new Robot(11, 100,1, 1, 7,1200));
    	if(i==0)Img = new MyImageIcon("resources/Robot/2robotship.png").resize(100, 75);
    	else Img = new MyImageIcon("resources/Robot/TankPrice.png").resize(100, 75);
    	setIcon(Img);
	}
	public TankBotLabel(JLabel b){
    	super( new Robot(11, 100,1,1, 7,1200));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/2robotship.png").resize(100, 75);
    	setIcon(Img);
    	setVisible(false);
    	Thread t = new Thread(){
        	public void run(){
            	TankBotLabel SBL = new TankBotLabel(0);
            	int x,y;
            	x = getCurX();
            	y = getCurY();
            	SBL.setBounds(x,y,100,75);
            	bg.add(SBL);
            	while(x<1366){
                	x+=5;
                	switch(y){
                    	case 123:{if(BoardSyncData.FirstMonsterInLine[0]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[0].getBounds())){BoardSyncData.FirstMonsterInLine[0].getML().hurt(100);   } break;}
                    	case 221:{if(BoardSyncData.FirstMonsterInLine[1]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[1].getBounds())){BoardSyncData.FirstMonsterInLine[1].getML().hurt(100);  } break;}
                    	case 319:{if(BoardSyncData.FirstMonsterInLine[2]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[2].getBounds())){BoardSyncData.FirstMonsterInLine[2].getML().hurt(100);  } break;}
                    	case 417:{if(BoardSyncData.FirstMonsterInLine[3]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[3].getBounds())){BoardSyncData.FirstMonsterInLine[3].getML().hurt(100);  } break;}
                    	case 515:{if(BoardSyncData.FirstMonsterInLine[4]!=null&&SBL.getBounds().intersects(BoardSyncData.FirstMonsterInLine[4].getBounds())){BoardSyncData.FirstMonsterInLine[4].getML().hurt(100); } break;}
                	}
                	SBL.setLocation(x,y);
                	validate(); repaint();
                	try{Thread.sleep(10);}catch(Exception e){}

            	}
            	clear();
        	}
    	};
    	t.start();
	}
}

class BlueBotLabel extends RobotLabel{
	protected MyImageIcon Img;

    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public BlueBotLabel(int i){
    	super( new Robot(0, 0,75, 0, 4,200));
    	if(i==0)Img = new MyImageIcon("resources/Robot/4blue.png").resize(100, 75);
    	else Img = new MyImageIcon("resources/Robot/BluePrice.png").resize(100, 75);
    	setIcon(Img);
	}
	public BlueBotLabel(JLabel b){
    	super( new Robot(0, 0,75, 0, 4,200));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/4blue.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
               	 
                	try{Thread.sleep(250);}catch(Exception e){}
       	 
            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public BlueBotLabel(JLabel b, int i){
    	super( new Robot(0, 0,75, 0, 4,200));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/4blue-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
               	 
                	try{Thread.sleep(250);}catch(Exception e){}
       	 
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}
class RadioRobotLabel extends RobotLabel{
	protected MyImageIcon Img;
	
    
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); validate(); repaint();}
	public RadioRobotLabel(int i){
    	super( new Robot(0, 0,50, 10, 3,300));
    	if(i ==0)Img = new MyImageIcon("resources/Robot/1radiorobot.png").resize(100, 75);
    	else Img = new MyImageIcon("resources/Robot/RadioPrice.png").resize(100, 75);
    	setIcon(Img);
	}
	public RadioRobotLabel(JLabel b){
    	super( new Robot(5, 0,50, 10, 3,300));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1radiorobot.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
               	 
                	if(inrange(getRobotData().getRange())){Shoot(2);}
                	try{Thread.sleep(1000);}catch(Exception e){}
               	 
            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public RadioRobotLabel(JLabel b, int i){
    	super( new Robot(5, 0,50, 10, 3,300));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1radiorobot-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	while(!getRobotData().isDead()){
               	 
                	if(inrange(getRobotData().getRange())){Shoot(2);}
                	try{Thread.sleep(1000);}catch(Exception e){}
               	 
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}
class SolarBotLabel extends RobotLabel{

	protected MyImageIcon Img;
	
	public Robot getRobotData(){return getRobot();}
	public void clear(){bg.remove(this); repaint(); validate(); }
	public SolarBotLabel(int i){
    	super( new Robot(0, 0,20, 0, 2,100));
    	if(i==0)Img = new MyImageIcon("resources/Robot/1solarbot.png").resize(100, 75);
    	else Img = new MyImageIcon("resources/Robot/SolarPrice.png").resize(100, 75);
    	setIcon(Img);
	}
	public SolarBotLabel(JLabel b){
    	super( new Robot(0, 0,20, 0, 2,100));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1solarbot.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	int i=0;
            	while(!getRobotData().isDead()){
               	 
                	if(i%100==0){Shoot(1); i-=100;}
                	try{Thread.sleep(50);}catch(Exception e){}
                	++i;
            	}
            	clear();
        	}
    	};
    	t.start();
	}
	public SolarBotLabel(JLabel b, int i){
    	super( new Robot(0, 0,20, 0, 2,100));
    	bg = b;
    	Img = new MyImageIcon("resources/Robot/1solarbot-base.png").resize(100, 75);
    	setIcon(Img);
    	Thread t = new Thread(){
        	public void run(){
            	int i=0;
            	while(!getRobotData().isDead()){
               	 
                	if(i%100==0){Shoot(1); i-=100;}
                	try{Thread.sleep(50);}catch(Exception e){}
                	++i;
            	}
            	clear();
        	}
    	};
    	t.start();
	}
}


class MonsterLabel extends JLabel {
    private Monster monsterdata;
    private int CurX,CurY;
    public Monster getML(){return monsterdata;}
    public int getCurX(){return CurX;}
    public void setCurX(int x){CurX=x;}
    public int getCurY(){return CurY;}
    public MonsterLabel(){
        monsterdata = new Monster(10,10,10,1); 
    }
    public MonsterLabel(Monster M){
        monsterdata = M;
    }
    
}

class MonsterIILabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public MonsterIILabel(int r){
    	super(new Monster(2,5,40,1));
   	Act = new MyImageIcon[21];
   	Act[0] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_007.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_008.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_009.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_010.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_014.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_015.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/MonsterII/4_enemies_1_attack_016.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_009.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_011.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_013.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_015.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_017.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/MonsterII/4_enemies_1_die_019.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_000.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_004.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_008.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_012.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_014.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_016.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/MonsterII/4_enemies_1_run_018.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
            	int TargetX=0;
            	int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	
            	while (!getM().isDead()&&CurX>-50) {
                	if(!getM().isStunned()){
                	if (!contract) {
                    	
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -= getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 13;
                    	}
                    	                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                   	 
                   	 
                	} else {
                    	if (cursor >= 6) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); cursor = 13; contract=false; validate(); repaint();}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                	}
                	else { ++stunTimer; if(stunTimer>=50){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<6;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class MonsterIIILabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public MonsterIIILabel(int r){
    	super(new Monster(2,8,40,2));
   	Act = new MyImageIcon[21];
   	Act[0] = new MyImageIcon("resources/MonsterIII/8_enemies_1_attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/MonsterIII/8_enemies_1_attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/MonsterIII/8_enemies_1_attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/MonsterIII/8_enemies_1_attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/MonsterIII/8_enemies_1_attack_008.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_002.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_008.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_010.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_012.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_014.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_016.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/MonsterIII/8_enemies_1_die_018.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_000.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_004.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_006.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_008.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_012.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_014.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_016.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/MonsterIII/8_enemies_1_run_018.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 12;
            	int CurX,CurY;
            	int TargetX=0;
            	int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	
            	while (!getM().isDead()&&CurX>-50) {
                	if(!getM().isStunned()){
                	if (!contract) {
                    	
                   	 
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -= getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor > 19) {
                        	cursor = 12;
                    	}
                    	                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                   	 
                   	 
                	} else {
                    	if (cursor >= 5) {
                        	cursor = 0;
                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); cursor = 12; contract=false; validate(); repaint();}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                	}
                	else { ++stunTimer; if(stunTimer>=50){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[5+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class RedwithnobrainLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public RedwithnobrainLabel(int r){
   	super(new Monster(2,10,50,3));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_008.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_010.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_014.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_attack_016.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_000.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_006.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_010.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_012.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_014.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_die_016.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_000.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_004.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_006.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_010.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_014.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_016.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/Redwithnobrain/1_enemies_1_run_018.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
                int stunTimer=0;
            	int TargetX=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	
            	while (!getM().isDead()&&CurX>-50) {
                    if(!getM().isStunned()){
                	if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -= getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor > 19) {
                        	cursor = 13;
                    	}
                                      	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor >= 7) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=13;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                        }
                	else { ++stunTimer; if(stunTimer>=40){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<6;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class GreenWithBrainLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public GreenWithBrainLabel(int r){
   	super(new Monster(2,10,50,4));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_008.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_attack_010.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_000.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_002.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_004.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_008.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_012.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_014.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_016.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_die_019.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_000.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_004.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_010.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_014.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_016.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/GreenWithBrain/2_enemies_1_run_018.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 14;
            	int CurX,CurY;
            	int TargetX=0;
                int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	
            	while (!getM().isDead()&&CurX>-50) {
                    if(!getM().isStunned()){
                	if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -= getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 14;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor >= 6) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=14;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                        }
                	else { ++stunTimer; if(stunTimer>=40){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<8;++i){setIcon(Act[6+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class GirlWraithLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public GirlWraithLabel(int r){
   	super(new Monster(3,12,60,5));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_008.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/GirlWraith/Wraith_02_Attack_010.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_000.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_002.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_004.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_006.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_008.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_010.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/GirlWraith/Wraith_02_Dying_012.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_000.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_002.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_004.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_006.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_008.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_010.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/GirlWraith/Wraith_02_Moving Forward_011.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
            	int TargetX=0;
                int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	
            	while (!getM().isDead()&&CurX>-50) {
                	if(!getM().isStunned()){
                	if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(50);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 13;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor >= 5) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=13;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                        }
                	else { ++stunTimer; if(stunTimer>=30){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[6+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
            	        	}
       	 
    	};
    	t.start();
   	 
  	 
	}
}
class UnicornWraithLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public UnicornWraithLabel(int r){
   	super(new Monster(3,15,60,6));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_007.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_008.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Attack_010.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_000.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_002.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_004.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_006.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_008.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_010.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Dying_012.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_000.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_002.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_004.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_006.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_008.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/UnicornWraith/Wraith_01_Moving Forward_010.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 14;
            	int CurX,CurY;
            	int TargetX=0;
                int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	while (!getM().isDead()&&CurX>-50) {    
                if(!getM().isStunned()){
                	if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(50);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 14;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor >= 7) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=14;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                        }
                	else { ++stunTimer; if(stunTimer>=30){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class MonsterIVLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public MonsterIVLabel(int r){
    	super(new Monster(2,15,60,1));
   	Act = new MyImageIcon[21];
   	Act[0] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_001.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_008.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_009.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/MonsterIV/6_enemies_1_attack_010.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_013.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_014.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_016.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_017.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_018.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/MonsterIV/6_enemies_1_die_019.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_001.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_002.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_003.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_004.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_005.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_006.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/MonsterIV/6_enemies_1_run_007.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
            	int TargetX=0;
            	int stunTimer=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	while (!getM().isDead()&&CurX>-50) {
                	if(!getM().isStunned()){
                	if (!contract) {              	 
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 13;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                   	 
                   	 
                	} else {
                    	if (cursor >= 7) {
                        	cursor = 0;
                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); cursor = 13; contract=false; validate(); repaint();}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                	}
                	else { ++stunTimer; if(stunTimer>=45){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<6;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class BonieTrollLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public BonieTrollLabel(int r,int c){
    	super(new Monster(10,10,60,2));
   	Act = new MyImageIcon[21];
   	Act[0] = new MyImageIcon("resources/BonieTroll/ATTAK_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/BonieTroll/ATTAK_001.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/BonieTroll/ATTAK_002.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/BonieTroll/ATTAK_003.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/BonieTroll/ATTAK_004.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/BonieTroll/ATTAK_005.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/BonieTroll/ATTAK_006.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/BonieTroll/DIE_000.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/BonieTroll/DIE_001.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/BonieTroll/DIE_002.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/BonieTroll/DIE_003.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/BonieTroll/DIE_004.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/BonieTroll/DIE_005.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/BonieTroll/DIE_006.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/BonieTroll/RUN_000.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/BonieTroll/RUN_001.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/BonieTroll/RUN_002.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/BonieTroll/RUN_003.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/BonieTroll/RUN_004.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/BonieTroll/RUN_005.png").resize(100, 75);
   	Act[20] = new MyImageIcon("resources/BonieTroll/RUN_006.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 14;
            	int CurX,CurY;
            	int TargetX=0;
            	int stunTimer=0;
            	CurX = BoardSyncData.Board[r][c].getCurX();
            	CurY = 135+(98*r);
            	while (!getM().isDead()) {
                	if(!getM().isStunned()){
                	if (!contract) {
                   	 
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -= getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 20) {
                        	cursor = 14;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                   	 
                   	 
                	} else {
                    	if (cursor >= 7) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); cursor = 14; contract=false; validate(); repaint();}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                	}
                	else { ++stunTimer; if(stunTimer>=50){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}

class BigTrollTreeLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public BigTrollTreeLabel(int r){
   	super(new Monster(1,20,360,1));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/BigTrollTree/Attack_000.png").resize(200, 150);
   	Act[1] = new MyImageIcon("resources/BigTrollTree/Attack_001.png").resize(200, 150);
   	Act[2] = new MyImageIcon("resources/BigTrollTree/Attack_002.png").resize(200, 150);
   	Act[3] = new MyImageIcon("resources/BigTrollTree/Attack_003.png").resize(200, 150);
   	Act[4] = new MyImageIcon("resources/BigTrollTree/Attack_004.png").resize(200, 150);
   	Act[5] = new MyImageIcon("resources/BigTrollTree/Attack_005.png").resize(200, 150);
   	Act[6] = new MyImageIcon("resources/BigTrollTree/Attack_006.png").resize(200, 150);
   	Act[7] = new MyImageIcon("resources/BigTrollTree/Dead_002.png").resize(200, 150);
   	Act[8] = new MyImageIcon("resources/BigTrollTree/Dead_003.png").resize(200, 150);
   	Act[9] = new MyImageIcon("resources/BigTrollTree/Dead_005.png").resize(200, 150);
   	Act[10] = new MyImageIcon("resources/BigTrollTree/Dead_006.png").resize(200, 150);
   	Act[11] = new MyImageIcon("resources/BigTrollTree/Dead_007.png").resize(200, 150);
   	Act[12] = new MyImageIcon("resources/BigTrollTree/Dead_009.png").resize(200, 150);
   	Act[13] = new MyImageIcon("resources/BigTrollTree/Run_000.png").resize(200, 150);
   	Act[14] = new MyImageIcon("resources/BigTrollTree/Run_003.png").resize(200, 150);
   	Act[15] = new MyImageIcon("resources/BigTrollTree/Run_004.png").resize(200, 150);
   	Act[16] = new MyImageIcon("resources/BigTrollTree/Run_005.png").resize(200, 150);
   	Act[17] = new MyImageIcon("resources/BigTrollTree/Run_006.png").resize(200, 150);
   	Act[18] = new MyImageIcon("resources/BigTrollTree/Run_008.png").resize(200, 150);
   	Act[19] = new MyImageIcon("resources/BigTrollTree/Run_009.png").resize(200, 150);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
                int stunTimer=0;
            	int TargetX=0;
            	CurX = 1300;
            	CurY = 65+(98*r);
            	while (!getM().isDead()&&CurX>-50) {
                if(!getM().isStunned()){	
                    if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(150);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 13;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor > 6) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=13;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                    }
                	else { ++stunTimer; if(stunTimer>=30){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<6;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class WillowTreeLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public WillowTreeLabel(int r){
   	super(new Monster(1,25,1200,2));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/WillowTree/6_animation_attack_000.png").resize(200, 150);
   	Act[1] = new MyImageIcon("resources/WillowTree/6_animation_attack_002.png").resize(200, 150);
   	Act[2] = new MyImageIcon("resources/WillowTree/6_animation_attack_004.png").resize(200, 150);
   	Act[3] = new MyImageIcon("resources/WillowTree/6_animation_attack_006.png").resize(200, 150);
   	Act[4] = new MyImageIcon("resources/WillowTree/6_animation_attack_007.png").resize(200, 150);
   	Act[5] = new MyImageIcon("resources/WillowTree/6_animation_attack_008.png").resize(200, 150);
   	Act[6] = new MyImageIcon("resources/WillowTree/6_animation_attack_009.png").resize(200, 150);
   	Act[7] = new MyImageIcon("resources/WillowTree/6_animation_attack_012.png").resize(200, 150);
   	Act[8] = new MyImageIcon("resources/WillowTree/6_animation_hurt_002.png").resize(200, 150);
   	Act[9] = new MyImageIcon("resources/WillowTree/6_animation_hurt_004.png").resize(200, 150);
   	Act[10] = new MyImageIcon("resources/WillowTree/6_animation_hurt_006.png").resize(200, 150);
   	Act[11] = new MyImageIcon("resources/WillowTree/6_animation_hurt_008.png").resize(200, 150);
   	Act[12] = new MyImageIcon("resources/WillowTree/6_animation_hurt_010.png").resize(200, 150);
   	Act[13] = new MyImageIcon("resources/WillowTree/6_animation_walk_001.png").resize(200, 150);
   	Act[14] = new MyImageIcon("resources/WillowTree/6_animation_walk_002.png").resize(200, 150);
   	Act[15] = new MyImageIcon("resources/WillowTree/6_animation_walk_004.png").resize(200, 150);
   	Act[16] = new MyImageIcon("resources/WillowTree/6_animation_walk_009.png").resize(200, 150);
   	Act[17] = new MyImageIcon("resources/WillowTree/6_animation_walk_012.png").resize(200, 150);
   	Act[18] = new MyImageIcon("resources/WillowTree/6_animation_walk_016.png").resize(200, 150);
   	Act[19] = new MyImageIcon("resources/WillowTree/6_animation_walk_018.png").resize(200, 150);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 13;
            	int CurX,CurY;
            	int TargetX=0;
                int stunTimer=0;
            	CurX = 1300;
            	CurY = 65+(98*r);
            	while (!getM().isDead()&&CurX>-50) {
                if(!getM().isStunned()){	
                    if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(150);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 13;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	}
                	else {
                    	if (cursor > 7) {
                        	cursor = 0;
                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=13;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                    }
                	else { ++stunTimer; if(stunTimer>=25){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<5;++i){setIcon(Act[8+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class VioletaLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public VioletaLabel(int r){
   	super(new Monster(3,15,80,1));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_000.png").resize(100, 75);
   	Act[1] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_002.png").resize(100, 75);
   	Act[2] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_004.png").resize(100, 75);
   	Act[3] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_006.png").resize(100, 75);
   	Act[4] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_007.png").resize(100, 75);
   	Act[5] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_009.png").resize(100, 75);
   	Act[6] = new MyImageIcon("resources/Violeta/Wraith_03_Attack_011.png").resize(100, 75);
   	Act[7] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_000.png").resize(100, 75);
   	Act[8] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_004.png").resize(100, 75);
   	Act[9] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_006.png").resize(100, 75);
   	Act[10] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_008.png").resize(100, 75);
   	Act[11] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_010.png").resize(100, 75);
   	Act[12] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_012.png").resize(100, 75);
   	Act[13] = new MyImageIcon("resources/Violeta/Wraith_03_Dying_014.png").resize(100, 75);
   	Act[14] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_000.png").resize(100, 75);
   	Act[15] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_002.png").resize(100, 75);
   	Act[16] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_004.png").resize(100, 75);
   	Act[17] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_006.png").resize(100, 75);
   	Act[18] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_008.png").resize(100, 75);
   	Act[19] = new MyImageIcon("resources/Violeta/Wraith_03_Moving Forward_010.png").resize(100, 75);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 14;
                int stunTimer=0;
            	int CurX,CurY;
            	int TargetX=0;
            	CurX = 1300;
            	CurY = 135+(98*r);
            	while (!getM().isDead()&&CurX>-50) {
                if(!getM().isStunned()){	
                    if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(50);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 14;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor > 6) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=14;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                    }
                	else { ++stunTimer; if(stunTimer>=40){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}
class BigTrollStreetLabel extends MonsterLabel{
	protected MyImageIcon[] Act;
	protected boolean contract;
	public Monster getM(){return getML();}
	void clear(){setVisible(false); getParent().remove(this); validate();}
	public BigTrollStreetLabel(int r){
   	super(new Monster(1,30,360,1));
   	Act = new MyImageIcon[20];
   	Act[0] = new MyImageIcon("resources/BigTrollStreet/Attack_000.png").resize(200, 150);
   	Act[1] = new MyImageIcon("resources/BigTrollStreet/Attack_001.png").resize(200, 150);
   	Act[2] = new MyImageIcon("resources/BigTrollStreet/Attack_002.png").resize(200, 150);
   	Act[3] = new MyImageIcon("resources/BigTrollStreet/Attack_003.png").resize(200, 150);
   	Act[4] = new MyImageIcon("resources/BigTrollStreet/Attack_004.png").resize(200, 150);
   	Act[5] = new MyImageIcon("resources/BigTrollStreet/Attack_005.png").resize(200, 150);
   	Act[6] = new MyImageIcon("resources/BigTrollStreet/Attack_006.png").resize(200, 150);
   	Act[7] = new MyImageIcon("resources/BigTrollStreet/Dead_000.png").resize(200, 150);
   	Act[8] = new MyImageIcon("resources/BigTrollStreet/Dead_002.png").resize(200, 150);
   	Act[9] = new MyImageIcon("resources/BigTrollStreet/Dead_004.png").resize(200, 150);
   	Act[10] = new MyImageIcon("resources/BigTrollStreet/Dead_005.png").resize(200, 150);
   	Act[11] = new MyImageIcon("resources/BigTrollStreet/Dead_006.png").resize(200, 150);
   	Act[12] = new MyImageIcon("resources/BigTrollStreet/Dead_007.png").resize(200, 150);
   	Act[13] = new MyImageIcon("resources/BigTrollStreet/Dead_008.png").resize(200, 150);
   	Act[14] = new MyImageIcon("resources/BigTrollStreet/Run_000.png").resize(200, 150);
   	Act[15] = new MyImageIcon("resources/BigTrollStreet/Run_002.png").resize(200, 150);
   	Act[16] = new MyImageIcon("resources/BigTrollStreet/Run_003.png").resize(200, 150);
   	Act[17] = new MyImageIcon("resources/BigTrollStreet/Run_004.png").resize(200, 150);
   	Act[18] = new MyImageIcon("resources/BigTrollStreet/Run_005.png").resize(200, 150);
   	Act[19] = new MyImageIcon("resources/BigTrollStreet/Run_009.png").resize(200, 150);
   	setHorizontalAlignment(JLabel.CENTER);
   	setIcon(Act[0]);
   	contract = false;
    	Thread t = new Thread() {
        	public void run() {
            	int cursor = 14;
            	int CurX,CurY;
                int stunTimer=0;
            	int TargetX=0;
            	CurX = 1300;
            	CurY = 65+(98*r);
            	while (!getM().isDead()&&CurX>-50) {
                if(!getM().isStunned()){	
                    if (!contract) {
                    	setIcon(Act[cursor]);
                    	setCurX(CurX);
                    	CurX -=getM().getSpeed();
                    	setLocation(CurX, CurY);
                    	try {
                        	Thread.sleep(150);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                    	if (cursor >= 19) {
                        	cursor = 14;
                    	}
                    	                   	 
                    	switch(r){
                        	case 0 :{
                                    	                                    	if(BoardSyncData.Board[0][10].isDeployed()&&CurX<=(BoardSyncData.Board[0][10].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[0][9].isDeployed()&&CurX<=(BoardSyncData.Board[0][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[0][8].isDeployed()&&CurX<=(BoardSyncData.Board[0][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[0][7].isDeployed()&&CurX<=(BoardSyncData.Board[0][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[0][6].isDeployed()&&CurX<=(BoardSyncData.Board[0][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[0][5].isDeployed()&&CurX<=(BoardSyncData.Board[0][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[0][4].isDeployed()&&CurX<=(BoardSyncData.Board[0][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[0][3].isDeployed()&&CurX<=(BoardSyncData.Board[0][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[0][2].isDeployed()&&CurX<=(BoardSyncData.Board[0][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[0][1].isDeployed()&&CurX<=(BoardSyncData.Board[0][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[0][0].isDeployed()&&CurX<=(BoardSyncData.Board[0][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[0][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                                	}
                        	case 1 :{
                                    	if(BoardSyncData.Board[1][10].isDeployed()&&CurX<=(BoardSyncData.Board[1][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[1][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[1][9].isDeployed()&&CurX<=(BoardSyncData.Board[1][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[1][8].isDeployed()&&CurX<=(BoardSyncData.Board[1][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[1][7].isDeployed()&&CurX<=(BoardSyncData.Board[1][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[1][6].isDeployed()&&CurX<=(BoardSyncData.Board[1][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[1][5].isDeployed()&&CurX<=(BoardSyncData.Board[1][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[1][4].isDeployed()&&CurX<=(BoardSyncData.Board[1][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[1][3].isDeployed()&&CurX<=(BoardSyncData.Board[1][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[1][2].isDeployed()&&CurX<=(BoardSyncData.Board[1][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[1][1].isDeployed()&&CurX<=(BoardSyncData.Board[1][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[1][0].isDeployed()&&CurX<=(BoardSyncData.Board[1][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[1][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 2 :{
                                    	if(BoardSyncData.Board[2][10].isDeployed()&&CurX<=(BoardSyncData.Board[2][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[2][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[2][9].isDeployed()&&CurX<=(BoardSyncData.Board[2][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[2][8].isDeployed()&&CurX<=(BoardSyncData.Board[2][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[2][7].isDeployed()&&CurX<=(BoardSyncData.Board[2][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[2][6].isDeployed()&&CurX<=(BoardSyncData.Board[2][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[2][5].isDeployed()&&CurX<=(BoardSyncData.Board[2][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[2][4].isDeployed()&&CurX<=(BoardSyncData.Board[2][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[2][3].isDeployed()&&CurX<=(BoardSyncData.Board[2][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[2][2].isDeployed()&&CurX<=(BoardSyncData.Board[2][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[2][1].isDeployed()&&CurX<=(BoardSyncData.Board[2][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[2][0].isDeployed()&&CurX<=(BoardSyncData.Board[2][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[2][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 3 :{
                                    	if(BoardSyncData.Board[3][10].isDeployed()&&CurX<=(BoardSyncData.Board[3][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[3][9].isDeployed()&&CurX<=(BoardSyncData.Board[3][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[3][8].isDeployed()&&CurX<=(BoardSyncData.Board[3][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[3][7].isDeployed()&&CurX<=(BoardSyncData.Board[3][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[3][6].isDeployed()&&CurX<=(BoardSyncData.Board[3][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[3][5].isDeployed()&&CurX<=(BoardSyncData.Board[3][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[3][4].isDeployed()&&CurX<=(BoardSyncData.Board[3][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[3][3].isDeployed()&&CurX<=(BoardSyncData.Board[3][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[3][2].isDeployed()&&CurX<=(BoardSyncData.Board[3][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[3][1].isDeployed()&&CurX<=(BoardSyncData.Board[3][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[3][0].isDeployed()&&CurX<=(BoardSyncData.Board[3][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[3][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                        	case 4 :{
                                    	if(BoardSyncData.Board[4][10].isDeployed()&&CurX<=(BoardSyncData.Board[4][10].getCurX()-25)&&CurX>=(BoardSyncData.Board[3][10].getCurX()-25)){contract = true; TargetX=10;}
                                    	else if(BoardSyncData.Board[4][9].isDeployed()&&CurX<=(BoardSyncData.Board[4][9].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][9].getCurX()-25)){contract = true; TargetX=9;}
                                    	else if(BoardSyncData.Board[4][8].isDeployed()&&CurX<=(BoardSyncData.Board[4][8].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][8].getCurX()-25)){contract = true; TargetX=8;}
                                    	else if(BoardSyncData.Board[4][7].isDeployed()&&CurX<=(BoardSyncData.Board[4][7].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][7].getCurX()-25)){contract = true; TargetX=7;}
                                    	else if(BoardSyncData.Board[4][6].isDeployed()&&CurX<=(BoardSyncData.Board[4][6].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][6].getCurX()-25)){contract = true; TargetX=6;}
                                    	else if(BoardSyncData.Board[4][5].isDeployed()&&CurX<=(BoardSyncData.Board[4][5].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][5].getCurX()-25)){contract = true; TargetX=5;}
                                    	else if(BoardSyncData.Board[4][4].isDeployed()&&CurX<=(BoardSyncData.Board[4][4].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][4].getCurX()-25)){contract = true; TargetX=4;}
                                    	else if(BoardSyncData.Board[4][3].isDeployed()&&CurX<=(BoardSyncData.Board[4][3].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][3].getCurX()-25)){contract = true; TargetX=3;}
                                    	else if(BoardSyncData.Board[4][2].isDeployed()&&CurX<=(BoardSyncData.Board[4][2].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][2].getCurX()-25)){contract = true; TargetX=2;}
                                    	else if(BoardSyncData.Board[4][1].isDeployed()&&CurX<=(BoardSyncData.Board[4][1].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][1].getCurX()-25)){contract = true; TargetX=1;}
                                    	else if(BoardSyncData.Board[4][0].isDeployed()&&CurX<=(BoardSyncData.Board[4][0].getCurX()+25)&&CurX>=(BoardSyncData.Board[4][0].getCurX()-25)){contract = true; TargetX=0;}
                                    	else {contract = false;}
                                    	break;
                        	}
                    	}
                	} else {
                    	if (cursor > 6) {
                        	cursor = 0;
                        	                        	BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().hurt(getM().getDmg());
                        	if(BoardSyncData.Board[r][TargetX].getRobotLabel().getRobot().isDead()){BoardSyncData.Board[r][TargetX].setDeployable(true);BoardSyncData.Board[r][TargetX].setDeployed(false); contract=false; repaint(); cursor=14;}
                        	                    	}
                    	setIcon(Act[cursor]);
                    	try {
                        	Thread.sleep(100);
                    	} catch (Exception e) {
                    	}
                    	++cursor;
                   	 
                	}
                    }
                	else { ++stunTimer; if(stunTimer>=30){getM().ReStunned(); stunTimer=0;} try {Thread.sleep(100);} catch (Exception e) {}  }
            	}
            	for(int i=0;i<7;++i){setIcon(Act[7+i]); try {Thread.sleep(100);} catch (Exception e) {}}
            	BoardSyncData.monsterRemainingDecrease();
            	clear();
        	}
    	};
    	t.start();
   	 
  	 
	}
}

class Obstrucle3Label extends JLabel{
    protected MyImageIcon Img;
    public void clear(){setVisible(false); getParent().remove(this); validate();}
    public Obstrucle3Label(JLabel bg,int r,int c){
        Img = new MyImageIcon("resources/Obstrucle3/rip1.png").resize(70, 95);
        setIcon(Img);
        setVisible(true);
        
        this.setBounds(BoardSyncData.Board[r][c].getCurX(),BoardSyncData.Board[r][c].getCurY(),100,75);
        bg.add(this);
        validate();
    }
    public Obstrucle3Label(JLabel bg,int r,int c,ArrayList<MonsterLabel> MonstArr){
        Thread t = new Thread(){
                public void run(){
                    Obstrucle3Label OBST = new Obstrucle3Label(bg,r,c);
                    try{Thread.sleep(20000);}
                    catch(Exception e){}
                    BoardSyncData.Board[r][c].setGraved(false);
                    BoardSyncData.Board[r][c].setDeployable(true);
                    BoardSyncData.Board[r][c].setDeployed(false);
                    BonieTrollLabel Monster = new BonieTrollLabel(r,c);
                    Monster.setBounds(BoardSyncData.Board[r][c].getCurX(),BoardSyncData.Board[r][c].getCurY(),100,75);
                    MonstArr.add(Monster);
                    bg.add(Monster);
                    OBST.clear();
                    
                    
                }
        };
        t.start();
    }
}


class WindLabel extends JLabel{
    protected MyImageIcon Img;
    public void clear(){try{getParent().remove(this); validate(); repaint();} catch(Exception e){}}
    public WindLabel(){
        Img = new MyImageIcon("resources/wind.png").resize(100,75);
        setIcon(Img);
    }
    
    public WindLabel(JLabel bg,int r){
        Thread t =new Thread(){
            public void run(){
                WindLabel wind = new WindLabel();
                setVisible(true);
                int x = 53 ,y = 135+98*r;
                wind.setBounds(x,y,100,75);
                bg.add(wind);
                while(x<1366){
                    x+=10;
                    for (int j = 0; j < 11; ++j) {
                            if (BoardSyncData.Board[r][j].getRobotLabel() != null&&wind.getBounds().intersects(BoardSyncData.Board[r][j].getRobotLabel().getBounds())) {
                                BoardSyncData.Board[r][j].setDeployable(true);
                                BoardSyncData.Board[r][j].setDeployed(false);
                                BoardSyncData.Board[r][j].getRobotLabel().getRobot().hurt(100);
                            }
                        }
                    wind.setLocation(x,y);
                    validate(); repaint();
                    try{Thread.sleep(100);}catch(Exception e){}
                }
                
                try{clear();} catch(Exception e){}
                
            }
        };
        t.start();
        
    }
}


class EnergyLabel extends JLabel{
    private MyImageIcon img;
    private boolean active;
    private int CurX,CurY;
    public EnergyLabel(RobotLabel RL){
        active = true;
        img = new MyImageIcon("resources/Robot/power.png").resize(100, 75);
        setIcon(img);
        setHorizontalAlignment(JLabel.CENTER);
        CurX = RL.getCurX()-30;
        CurY = RL.getCurY();
        this.setBounds(CurX, CurY, 100, 100);
        
        RL.bg.add(this);
        Thread t = new Thread(){
        public void run(){
            
            for(int i=0;i<100;++i){
                if(i<50){CurY-=1;}
                if(i>50){CurY+=1;}
                setLocation(CurX,CurY);
                repaint();
                try{Thread.sleep(10);}catch(Exception e){break;}
            }
            try{if(active)Thread.sleep(1500);}catch(Exception e){}
            active = false;
        }
        };
        t.start();
        this.addMouseListener(new MouseAdapter(){
        public void mouseEntered(MouseEvent e){
           BoardSyncData.addEnergy();
           active = false;
            t.interrupt();
        }
        });
        try{t.join();}catch(Exception e){}
        this.setVisible(false);
        RL.bg.remove(this);
    }
    public EnergyLabel(){
        
        img = new MyImageIcon("resources/Robot/power.png").resize(100, 75);
        setIcon(img);
        setHorizontalAlignment(JLabel.CENTER);
        
    }
}
