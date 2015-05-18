import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Window2 extends JPanel implements ActionListener{
	JPanel panel = new JPanel();
	JPanel left = new JPanel(), right = new JPanel(), middle = new JPanel(), bottom = new JPanel();
	JLabel label = new JLabel("CLOCK CYCLE: ");
	JButton next_cycle = new JButton("Next");
	JTextArea text_f = new JTextArea(10,10), text_d = new JTextArea(1,10), text_ex = new JTextArea(2,10), text_alu = new JTextArea(10,10);
	JButton IF = new JButton("IF"), ID = new JButton("ID"), EX = new JButton("EX"), ALU = new JButton("ALU");
	static MainWindow main = new MainWindow();
	static Window3 window3 = new Window3();
	static int next_cnt=0,next_ins=0, length =0; 
	static String[]words;	
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
		middle.add(label);
		bottom.setPreferredSize(new Dimension(300,50));
		bottom.add(label);									//clock cycle
		next_cycle.addActionListener((ActionListener) this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == next_cycle){
			int i=0;
			String space = "  ";
			String[]ins;
			System.out.println(main.PipeLineSize());
			/*if(next_cnt>=main.lines.length){
				JOptionPane.showMessageDialog(null, "NO MORE INSTRUCTIONS!");	//pop-up window
				
			}
			else{*/
				label.setText("CLOCK CYCLE:"+(next_cnt));
				for(i=0; i<main.PipeLineSize();i++){
					System.out.println("hello");
					words =main.PipeLineReader(i).split("");
					System.out.println("I:"+i+":"+words.length);
					if(words.length>next_cnt){
						if(words[next_cnt].equals("F")){
							
							IF.setBackground(Color.pink);
							text_f.setText(space + main.lines[i]);
						}
						if(words[next_cnt].equals("D")){
							ins = main.lines[i].split(" ");
							if(ins[0].equals("ADD")|| ins[0].equals("SUB") || ins[0].equals("INC") || ins[0].equals("DEC") || ins[0].equals("MUL") || ins[0].equals("DIV") || ins[0].equals("AND") || ins[0].equals("OR")|| ins[0].equals("NOT")|| ins[0].equals("XOR")){
								ALU.setBackground(Color.yellow);
								text_alu.setText(space + main.lines[i]);
							}
							ID.setBackground(Color.blue);
							text_d.setText(space + main.lines[i]);
							
						}
						if(words[next_cnt].equals("E")){
							ins = main.lines[i].split(" ");
							if(ins[0].equals("ADD")|| ins[0].equals("SUB") || ins[0].equals("INC") || ins[0].equals("DEC") || ins[0].equals("MUL") || ins[0].equals("DIV") || ins[0].equals("AND") || ins[0].equals("OR")|| ins[0].equals("NOT")|| ins[0].equals("XOR")){
								ALU.setBackground(Color.yellow);
								text_alu.setText(space + main.lines[i]);
							}
							EX.setBackground(Color.green);
							text_ex.setText(space + main.lines[i]);
						}
					}
				}
				next_cnt++;
				
			//}
		
		}//end of if next clock cycle
		
	}

}
