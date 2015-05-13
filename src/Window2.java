import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Window2 extends JPanel {
	JPanel panel = new JPanel();
	JPanel left = new JPanel(), right = new JPanel(), middle = new JPanel(), bottom = new JPanel();
	JLabel label = new JLabel("CLOCK CYCLE: ");
	JButton next_cycle = new JButton("Next");
	JTextArea text_f = new JTextArea(10,10), text_d = new JTextArea(1,10), text_ex = new JTextArea(2,10), text_alu = new JTextArea(10,10);
	JButton IF = new JButton("IF"), ID = new JButton("ID"), EX = new JButton("EX"), ALU = new JButton("ALU");
	
	public Window2(){
		//---------FIX PANELS AND ADD BUTTONS-----//
		text_f.setEditable(false);
		text_d.setEditable(false);
		text_ex.setEditable(false);
		text_alu.setEditable(false);
		left.setPreferredSize(new Dimension(100, 100));		//fetch
		left.add(IF);
		left.add(text_f);
		right.setPreferredSize(new Dimension(100, 100));	//alu
		right.add(ALU);					
		right.add(text_alu);
		middle.setPreferredSize(new Dimension(100, 200));	//decode, execute
		middle.add(text_d);
		middle.add(ID);
		middle.add(EX);
		middle.add(text_ex);
		bottom.setPreferredSize(new Dimension(300,50));
		bottom.add(label);									//clock cycle
		//next_cycle.addActionListener((ActionListener) this);
		bottom.add(next_cycle);
		//bottom.add(text_f);
		//----FETCH DECODE EXECUTE CYCLE BUTTONS LAYOUTING----//
		IF.setPreferredSize(new Dimension(70,70));
		IF.setBackground(Color.white);
		IF.setFont(new Font("Verdana", Font.BOLD, 30));
		ID.setPreferredSize(new Dimension(70,70));
		ID.setBackground(Color.white);
		ID.setFont(new Font("Verdana", Font.BOLD, 25));
		EX.setPreferredSize(new Dimension(70,70));
		EX.setBackground(Color.white);
		EX.setFont(new Font("Verdana", Font.BOLD, 25));
		ALU.setPreferredSize(new Dimension(70,70));
		ALU.setBackground(Color.white);
		ALU.setFont(new Font("Verdana", Font.BOLD, 15));
		
		
		panel.setPreferredSize(new Dimension(400, 370));
		panel.add(left);
		panel.add(middle);
		panel.add(right);
		panel.add(bottom);
		this.add(panel);
		
		
	}//end of Window1

}
