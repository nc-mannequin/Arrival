/*
Nasreeya        Tippayanont     6213128
Puttimait       Viwatthara      6213130
Napat           Cheepmuangman   6213200
Pojanut         Aramvuttanagul  6213205
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Wiki extends JDialog {

    private JLabel BG, back, combolabel;
    MyImageIcon bgimg, backimg, robotlabel, card;
    Container contentpane = getContentPane();
    private JComboBox slot;
    private CardCharacter Card;

    public void addComponent() {
        back = new JLabel();
        backimg = new MyImageIcon("resources/back.png").resize(90, 90);
        back.setIcon(backimg);
        back.setBounds(20, 565, 90, 90);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
        robotlabel = new MyImageIcon("resources/robotlabel.png").resize(350, 70);
        combolabel = new JLabel(robotlabel);
        String[] robotnames = {"Solar Robot", "Radio Robot", "Shooting Robot", "Mercury Robot", "Ship Robot", "Blitz Robot", "Tank Robot", "Blue Robot", "Gollum Robot", "Falcon Robot"};
        slot = new JComboBox(robotnames);
        slot.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String name = slot.getSelectedItem().toString();
                Card.setCard(name);
                Card.setBounds(70, 150, 330, 400);
            }
        });
        combolabel.setBounds(75, 18, 350, 70);
        slot.setBounds(185, 90, 140, 30);
        BG = new JLabel();
        bgimg = new MyImageIcon("resources/cardbg.jpg").resize(480, 720);
        BG.setIcon(bgimg);
        BG.setLayout(null);
        BG.add(back);
        BG.add(combolabel);
        BG.add(slot);
        BG.add(Card = new CardCharacter());
        contentpane.add(BG, BorderLayout.CENTER);
        validate();
    }

    public Wiki() {
        setTitle("Wiki");
        setBounds(500, 80, 480, 720);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        contentpane.setLayout(new BorderLayout());
        addComponent();
    }

    class CardCharacter extends JLabel{
        public CardCharacter(){
            setPreferredSize(new Dimension(330,400));
        }
        public void setCard(String n){
            MyImageIcon card = new MyImageIcon("resources/wiki/"+n+".jpg").resize(330, 400);
            System.out.println(n+".jpg");
            setIcon(card);
            setVisible(true);
            validate();
        }
    }
}
