import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Window5 extends JPanel {
	JPanel panel = new JPanel();
	JTextArea text = new JTextArea();
	public Window5(){
		text.setPreferredSize(new Dimension(380,300));
		text.setEditable(false);
		panel.setPreferredSize(new Dimension(400, 370));
		panel.add(text);
		this.add(panel);
		
		
	}//end of Window1
}
