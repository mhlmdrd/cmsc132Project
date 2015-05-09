import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Window1 extends JPanel implements ActionListener{
	JPanel panel = new JPanel();
	JTextArea text = new JTextArea();
	public Window1(){
		text.setPreferredSize(new Dimension(580,500));
		text.setEditable(false);
		panel.setPreferredSize(new Dimension(500, 570));
		panel.add(text);
		this.add(panel);
		
	}//end of Window1

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
