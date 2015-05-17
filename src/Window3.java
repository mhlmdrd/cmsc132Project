import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Window3 extends JPanel {
	JPanel panel = new JPanel();
	JTextArea text = new JTextArea();	
	LinkedList<String> cycles = new LinkedList<String>();
	public Window3(){
		text.setPreferredSize(new Dimension(380,300));
		text.setEditable(false);
		panel.setPreferredSize(new Dimension(400, 370));
		panel.add(text);
		this.add(panel);		
	}//end of Window3
	
	public void newPipeline(int instruction_count, String type){
		String[] arithmethic_cycle = {"F","D","D","E","E"};
		String[] normal_cycle = {"F","D","E"};
		String pipeline = "";
		String prev_pipeline[];
		int j = instruction_count, index = 0;
		
		/*Add spaces/indent before each FDE*/
		for(int i = 0; i < instruction_count; i++) pipeline += " ";
		
		if(instruction_count != 0 ){
			//fetch previous FDE then compare each to current FDE
			prev_pipeline = cycles.get(instruction_count-1).split("(?!^)");
			if(type.equals("arithmetic")){ // if Arithmetic FDDEE
				while(j < cycles.get(instruction_count-1).length() && index < 5){
					if(prev_pipeline[j].equals(arithmethic_cycle[index])){ pipeline += "S"; }
					else {pipeline += arithmethic_cycle[index]; index++; }
					j++;
				} 
				while(index < 5){ pipeline += arithmethic_cycle[index]; index++;}
			}else{ //else FDE
				while(j < cycles.get(instruction_count-1).length() && index < 3){
					if(prev_pipeline[j].equals(normal_cycle[index])){ pipeline += "S"; }
					else {pipeline += normal_cycle[index]; index++; }
					j++;
				}while(index < 3){ pipeline += normal_cycle[index]; index++;}
			}			 
		}else if (instruction_count == 0){
			if(type.equals("arithmethic")) pipeline += "FDDEE";
			else pipeline += "FDE";
		}
		
		cycles.add(instruction_count, pipeline);	
	}
	
	public String getPipeline(int index){
		return cycles.get(index);
	}
}
