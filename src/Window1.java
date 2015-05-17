import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class Window1 extends JPanel implements ActionListener{
	JPanel panel = new JPanel();
	JTextArea text = new JTextArea(30,40);
	int text_v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
	int text_h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
	JScrollPane input_scroller = new JScrollPane(text, text_v, text_h);
	public Window1(){
		text.setPreferredSize(new Dimension(580,500));
		text.setEditable(false);
		panel.setPreferredSize(new Dimension(500, 570));
		panel.add(input_scroller);
		this.add(panel);
		
	}//end of Window1

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
