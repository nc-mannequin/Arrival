/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Creator extends JDialog{
    private JLabel BG;
    private JLabel back;
    MyImageIcon bgimg,backimg;
    Container contentpane = getContentPane();
    
    public Creator(){
        setTitle("Creator");
        setBounds(200,50,1366,768);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        contentpane.setLayout(new BorderLayout());
        addComponent();
    }
    public void addComponent(){
        back = new JLabel("Back");
        backimg = new MyImageIcon("resources/back.png").resize(90, 90);
        back.setIcon(backimg);
        back.setBounds(30,620,80,80);
        
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e){
            setVisible(false);
        }
        });
        BG = new JLabel();
        bgimg = new MyImageIcon("resources/members.jpg").resize(1366, 768);
        BG.setIcon(bgimg);
        BG.setLayout(null);
        BG.add(back);
        contentpane.add(BG,BorderLayout.CENTER);
        validate();
    }
}