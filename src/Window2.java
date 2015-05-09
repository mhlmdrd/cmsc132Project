import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Window2 extends JPanel {
	JPanel panel = new JPanel();
	JTextArea text = new JTextArea();
	JButton IF = new JButton("IF"), ID = new JButton("ID"), EX = new JButton("EX"), ALU = new JButton("ALU");
	public Window2(){
		text.setPreferredSize(new Dimension(380,300));
		text.setEditable(false);
		panel.setPreferredSize(new Dimension(400, 370));
		panel.add(text);
		this.add(panel);
		
		
	}//end of Window1
}
