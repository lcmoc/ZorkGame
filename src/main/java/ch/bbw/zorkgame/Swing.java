package ch.bbw.zorkgame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing {
    public static void main(String[] args) {
        JFrame f=new JFrame("Adventure House");//creating instance of JFrame
        final JTextField tf=new JTextField();
        tf.setBounds(50,50, 150,20);

        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,140,100, 40);//x axis, y axis, width, height
        JButton b1=new JButton("click 2");
        b1.setBounds(130,180,100, 40);//x axis, y axis, width, height

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tf.setText("Welcome to Javatpoint.");
            }
        });

        f.add(b);f.add(tf);
        f.add(b);//adding button in JFrame
        f.add(b1);//adding button in JFrame

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

    }
}
