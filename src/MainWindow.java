import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainWindow extends JFrame {
	static Window1 Window1 = new Window1();
	static Window2 Window2 = new Window2();
	static Window3 Window3 = new Window3();
	static Window4 Window4 = new Window4();
	static Window5 Window5 = new Window5();
	static Window6 Window6 = new Window6();
	static Window7 Window7 = new Window7();
	static File userFile;
	static String [] r = new String[7]; //REGISTERS r[0]-r[7]
	static int line_cnt =0;
	public static void main(String[] args){
		int flag = 1;
		JFileChooser choosy = new JFileChooser();
		int returnValue = choosy.showOpenDialog(choosy);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
		userFile = choosy.getSelectedFile();
        }
		if(flag ==1){ //a file has been chosen
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(userFile));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}//end of 1st try catch
			 String line = null;
			 try {
				//get the size for array  
				while ((line = br.readLine()) != null) {
					//System.out.println("hi");
					String input_row = line; //save each line in an array
					String[] words = input_row.split(" ");
					int index1 =0;
					int index2 =0;
					//first word
					if(words[0].equals("SAVE")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index2==-1)&&(index1!=8)&&(index2!=8)){		//register - immediate
							r[index1] = words[2];
							System.out.println("R"+index1+r[index1]);
						}
						else if((index1!=-1)&&(index2!=-1)&&(index1!=8)&&(index2!=8)){	//register-register
							r[index1] = r[index2];
							System.out.println("R"+index1+r[index1]);
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
						}
					}//end of if SAVE
					line_cnt++;
				}//end of while loop
			 }catch (IOException e1) {
					e1.printStackTrace();
			 }//end of 2nd try catch
		}
		/*
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JDesktopPane desktop = new JDesktopPane();
		//JInternalFrame window1 = new JInternalFrame("WINDOW1", true,true,true,true);
		//desktop.add(window1);
		//window1.setBounds(50, 50, 500, 500);
		//window1.setVisible(true);
		
		//1st window
		JInternalFrame canvas1 = new JInternalFrame("WINDOW1", true,true,true,true);
		canvas1.add(Window1);
		canvas1.setVisible(true);
		canvas1.setBounds(35, 35, 400, 350);
		
		//2nd window
		JInternalFrame canvas2 = new JInternalFrame("WINDOW2", true,true,true,true);
		canvas2.add(Window2);
		canvas2.setVisible(true);
		canvas2.setBounds(35, 35, 400, 350);
		
		//3rd window
		JInternalFrame canvas3 = new JInternalFrame("WINDOW3", true,true,true,true);
		canvas3.add(Window3);
		canvas3.setVisible(true);
		canvas3.setBounds(35, 35, 400, 350);
		
		//4th window
		JInternalFrame canvas4 = new JInternalFrame("WINDOW4", true,true,true,true);
		canvas4.add(Window4);
		canvas4.setVisible(true);
		canvas4.setBounds(35, 35, 400, 350);
		
		//5th window
		JInternalFrame canvas5 = new JInternalFrame("WINDOW5", true,true,true,true);
		canvas5.add(Window5);
		canvas5.setVisible(true);
		canvas5.setBounds(35, 35, 400, 350);
		
		//5th window
		JInternalFrame canvas6 = new JInternalFrame("WINDOW6", true,true,true,true);
		canvas6.add(Window6);
		canvas6.setVisible(true);
		canvas6.setBounds(35, 35, 400, 350);
				
		desktop.add(canvas1);
		desktop.add(canvas2);
		desktop.add(canvas3);
		desktop.add(canvas4);
		desktop.add(canvas5);
		desktop.add(canvas6);
		
		
		frame.add(desktop);
		frame.setPreferredSize(new Dimension(1500, 1500));
		frame.setVisible(true);
		
		*/
		
	}//end of main function
	
	public static int FindRegister(String word){
		if(word.equals("r0")){
			return 0;
		}
		if(word.equals("r1")){
			return 1;
		}
		if(word.equals("r2")){
			return 2;
		}
		if(word.equals("r3")){
			return 3;
		}
		if(word.equals("r4")){
			return 4;
		}
		if(word.equals("r5")){
			return 5;
		}
		if(word.equals("r6")){
			return 6;
		}
		if(word.equals("r7")){
			return 7;
		}
		if(word.equals("mar0") || word.equals("mar1")){
			return 8;
		}
		
		else{
			return -1;
		}
		
		
		
		
	}//end of function

}
