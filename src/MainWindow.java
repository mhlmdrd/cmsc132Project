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
	static String [] r = new String[8]; 				//REGISTERS r[0]-r[7]
	static String [] binary_code = new String[18]; 		//code for the instruction
	static String [] instruction_code = new String[18]; //code for the register
	static String flagCMP = new String();
	static String PC = new String();					//Program Counter (PC)
	static String IR = new String();					//Instruction Register (IR)
	static String zeroFlag = new String();				//Zero Flag
	static String signFlag = new String();				//Sign Flag
	static String mar0="0",mar1="0";			 		//MAR[0]-MAR[1]
	static int line_cnt =1;
	public static void main(String[] args){
		flagCMP = "0000 0000"; // updated if CMP instruction is called
		signFlag = zeroFlag = "0000";
		int flag = 1;
		JFileChooser choosy = new JFileChooser();
		int returnValue = choosy.showOpenDialog(choosy);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
		userFile = choosy.getSelectedFile();
        }
		if(flag ==1){ //a file has been chosen
			//---------------------INITIALIZE INSTRUCTION CODES------------------------//
			instruction_code[0] = "0000 0000";			//LD
			instruction_code[1] = "0000 0001";			//STR
			instruction_code[2] = "0000 0010";			//SV
			instruction_code[3] = "0000 0011";			//INC
			instruction_code[4] = "0000 0100";			//DEC
			instruction_code[5] = "0000 0101";			//ADD
			instruction_code[6] = "0000 0110";			//SUB
			instruction_code[7] = "0000 0111";			//MUL
			instruction_code[8] = "0000 1000";			//DIV
			instruction_code[9] = "0000 1001";			//CMP
			instruction_code[10] = "0000 1010";			//AND
			instruction_code[11] = "0000 1011";			//OR
			instruction_code[12] = "0000 1100";			//NOT
			instruction_code[13] = "0000 1101";			//XOR
			instruction_code[14] = "0000 1110";			//JE
			instruction_code[15] = "0000 1111";			//JG
			instruction_code[16] = "0001 0000";			//JL
			instruction_code[17] = "0001 0001";			//JMP
			
			//---------------------INITIALIZE REGISTER CODE/NUMBER------------------------//
			binary_code[0] = "0000 0000";				//r0
			binary_code[1] = "0000 0001";				//r1
			binary_code[2] = "0000 0010";				//r2
			binary_code[3] = "0000 0011";				//r3
			binary_code[4] = "0000 0100";				//r4
			binary_code[5] = "0000 0101";				//r5
			binary_code[6] = "0000 0110";				//r6
			binary_code[7] = "0000 0111";				//r7
			binary_code[8] = "0000 1000";				//mar0
			binary_code[9] = "0000 1001";				//mar1
			
			//-------------------------USER INTERFACE-----------------------------//
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			JDesktopPane desktop = new JDesktopPane();
			
			//1st window
			JInternalFrame canvas1 = new JInternalFrame("WINDOW1", true,true,true,true);
			canvas1.add(Window1);
			canvas1.setVisible(true);
			canvas1.setBounds(70, 70, 600, 550);
			
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
			
			desktop.add(canvas1);
			desktop.add(canvas2);
			desktop.add(canvas3);
			
			frame.add(desktop);
			frame.setPreferredSize(new Dimension(1500, 1500));
			frame.setVisible(true);
			
			
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
					int result =0;
					//-------------------------LOAD------------------------------------//
					if(words[0].equals("LD")){
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						if((index1!=-1)&&((index2==8)||(index2==9))){//register-mar
							if(index2==8){
								r[index1] = mar0;
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[0]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
							}
							if(index2==9){
								r[index1] = mar1;
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[0]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF LOAD
					
					//-------------------------STORE------------------------------------//
					if(words[0].equals("STR")){
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						if((index2!=-1)&&((index1==8)||(index1==9))){//mar-register
							if(index1==8){
								mar0 = r[index2];
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[1]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
							}
							if(index1==9){
								mar1 = r[index2];
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[1]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
							}
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF STORE
					
					//-----------------------INC-------------------------//					
					if(words[0].equals("INC")){
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						if((index1!=-1)){//register
							result = Integer.parseInt(r[index1])+1;
							r[index1] = Integer.toString(result);
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[2]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF INC
					
					//-----------------------DEC-------------------------//
					
					if(words[0].equals("DEC")){
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						if((index1!=-1)){//register
							result = Integer.parseInt(r[index1])-1;
							r[index1] = Integer.toString(result);
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[3]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF DEC
					
					
					
					//-------------------------SAVE------------------------------------//
					//first word
					if(words[0].equals("SV")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index2==-1)&&(index1!=8)&&(index2!=8)&&(index1!=9)&&(index2!=9)){		//register - immediate
							r[index1] = words[2];
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[4]+" "+binary_code[index1]+"  "+line+"\n");
							
						}
						else if((index1!=-1)&&(index2!=-1)&&(index1!=8)&&(index2!=8)&&(index1!=9)&&(index2!=9)){	//register-register
							r[index1] = r[index2];
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[4]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
							
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//end of if SAVE
					
					
					//-------------------------ADD------------------------------------//
					//first word
					if(words[0].equals("ADD")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1])+ Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1])+ Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									
								}
								if(index2==9){
									result = Integer.parseInt(r[index1])+ Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									
								}
							}//END OF IF MAR
							
							//-----------------------------------BEA HERE ------------------------------//
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1])+ Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0)+ Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1)+ Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0)+ Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1)+ Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1)+ Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1)+ Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0)+ Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1)+ Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[5]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if ADD
					
					//-------------------------SUBTRACT------------------------------------//
					//first word
					if(words[0].equals("SUB")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1])- Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1])- Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2==9){
									result = Integer.parseInt(r[index1])- Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1])- Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+line+"\n");
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0)- Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1)- Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0)- Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1)+ Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1)- Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1)- Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									
									
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0)- Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1)- Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[6]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if SUBTRACT

					//-------------------------MULTIPLY------------------------------------//
					//first word
					if(words[0].equals("MUL")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1])* Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1])* Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									
								}
								if(index2==9){
									result = Integer.parseInt(r[index1])* Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1])* Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0)* Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1)* Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0)* Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1)* Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1)* Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1)* Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0)* Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1)* Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[7]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if MULTIPLY

					//-------------------------DIVIDE------------------------------------//
					//first word
					if(words[0].equals("DIV")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								if (Integer.parseInt(r[index2]) != 0) {
									result = Integer.parseInt(r[index1])/ Integer.parseInt(r[index2]);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								else{
									System.out.println("Division by zero is undefined.");
								}
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									if (Integer.parseInt(mar0) != 0) {
										result = Integer.parseInt(r[index1]) / Integer.parseInt(mar0);
										r[index1] = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else{
										System.out.println("Division by zero is undefined.");
									}
								}
								if(index2==9){
									if (Integer.parseInt(mar1) != 0) {
										result = Integer.parseInt(r[index1]) / Integer.parseInt(mar1);
										r[index1] = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else{
										System.out.println("Division by zero is undefined.");
									}		
									
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								if (Integer.parseInt(words[2]) != 0) {
									result = Integer.parseInt(r[index1]) / Integer.parseInt(words[2]);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+line+"\n");
								}
								else{
									System.out.println("Division by zero is undefined.");
								}								
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if (Integer.parseInt(r[index2]) != 0) {
									if(index1 == 8){
										result = Integer.parseInt(mar0) / Integer.parseInt(r[index2]);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									if(index2 == 8){
										result = Integer.parseInt(mar1) / Integer.parseInt(r[index2]);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								else{
									System.out.println("Division by zero is undefined.");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(Integer.parseInt(mar0) != 0){
									if(index2==8){							//MAR-mar0
										if(index1==8){						//mar0-mar0
											result = Integer.parseInt(mar0)/ Integer.parseInt(mar0);
											mar0 = Integer.toString(result);
											Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
										}
										else if(index1==9){					//mar1-mar0
											result = Integer.parseInt(mar1)/ Integer.parseInt(mar0);
											mar1 = Integer.toString(result);
											Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
										}										
									}
									if(index2==9){							//MAR-mar1
										if(index1==8){						//mar0-mar1
											result = Integer.parseInt(mar1)/ Integer.parseInt(mar0);
											mar0 = Integer.toString(result);
											Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
										}
										else if(index1==9){					//mar1-mar1
											result = Integer.parseInt(mar1)/ Integer.parseInt(mar0);
											mar1 = Integer.toString(result);
											Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
										}					
									}
								}
								else{
									System.out.println("Division by zero is undefined.");
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if (Integer.parseInt(words[2]) != 0) {
									if(index1==8){						//mar0-immediate
										result = Integer.parseInt(mar0)/ Integer.parseInt(words[2]);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-immediate
										result = Integer.parseInt(mar1)/ Integer.parseInt(words[2]);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[8]+" "+binary_code[index1]+" "+line+"\n");
									}									
								}
								else{
									System.out.println("Division by zero is undefined.");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if DIVIDE

					//-----------------------CMP-------------------------//
					
					if(words[0].equals("CMP")){
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand1 is a register/immediate 
						if((index1!=-1) && (index2!=-1)){//register
							if (index1 == index2) flagCMP = "0000 0000";
							else if (index1 > index2) flagCMP = "0000 0001";
							else flagCMP = "0000 0010";
							System.out.println("flagCMP = "+ flagCMP);							
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[9]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF CMP
					
					//-------------------------BITWISE AND------------------------------------//
					//first word
					if(words[0].equals("AND")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1]) & Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1]) & Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2==9){
									result = Integer.parseInt(r[index1]) & Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1]) & Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0) & Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1) & Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0) & Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1) & Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1)& Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1) & Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}																	
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0) & Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+line+"\n");
									System.out.println(mar0);
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1) & Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[10]+" "+binary_code[index1]+" "+line+"\n");
									System.out.println(mar1);
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if BITWISE AND

					//-------------------------BITWISE OR------------------------------------//
					//first word
					if(words[0].equals("OR")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1]) | Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1]) | Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2==9){
									result = Integer.parseInt(r[index1]) | Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1]) | Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0) | Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1) | Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0) | Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1) | Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1)| Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1) | Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}								
									
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0) | Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1) | Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[11]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
					}//end of if BITWISE OR
					
					
					//-----------------------------BITWISE NOT------------------------------------//
					//first word
					if(words[0].equals("NOT")){
						index1 = FindRegister(words[1]);
						index2 = FindRegister(words[2]);
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = ~Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = ~Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2==9){
									result = ~Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = ~Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = ~Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = ~Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = ~Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = ~Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = ~Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = ~Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}																	
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){						//mar - immediate
								if(index1==8){						//mar0-immediate
									result = ~Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = ~Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[12]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
							}
						}//DESTINATION = MAR
						
						if(index1==-1){ System.out.println("ERROR at line: "+line_cnt); break;}	//ERROR
						
					}//end of NOT
					
					//------------------------------BITWISE XOR---------------------------------------//
					//first word
					if(words[0].equals("XOR")){
						
						index1 = FindRegister(words[1]);		//see if operand1 is a register/immediate 
						index2 = FindRegister(words[2]);		//see if operand2 is a register/immediate
						
						if((index1!=-1)&&(index1!=8) &&(index1!=9)){//register - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//register-register
								result = Integer.parseInt(r[index1]) ^ Integer.parseInt(r[index2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								
							}//END OF IF REGISTER
							else if(index2!=-1){						//register-mar
								if(index2==8){
									result = Integer.parseInt(r[index1]) ^ Integer.parseInt(mar0);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2==9){
									result = Integer.parseInt(r[index1]) ^ Integer.parseInt(mar1);
									r[index1] = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR
							else if(index2==-1){						//register - immediate
								result = Integer.parseInt(r[index1]) ^ Integer.parseInt(words[2]);
								r[index1] = Integer.toString(result);
								Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+line+"\n");
								
							}//END OF IF IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = REGISTER
						
						if((index1==8) ||(index1==9)){//MAR - immediate/REG/MAR
							if((index2!=-1)&&(index2!=8)&&(index2!=9)){//mar-register
								if(index1 == 8){
									result = Integer.parseInt(mar0) ^ Integer.parseInt(r[index2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
								if(index2 == 8){
									result = Integer.parseInt(mar1) ^ Integer.parseInt(r[index2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
								}
							}//END OF IF MAR-REGISTER
							else if(index2!=-1){						//mar-mar
								if(index2==8){							//MAR-mar0
									if(index1==8){						//mar0-mar0
										result = Integer.parseInt(mar0) ^ Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar0
										result = Integer.parseInt(mar1) ^ Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}									
								}
								if(index2==9){							//MAR-mar1
									if(index1==8){						//mar0-mar1
										result = Integer.parseInt(mar1) ^ Integer.parseInt(mar0);
										mar0 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}
									else if(index1==9){					//mar1-mar1
										result = Integer.parseInt(mar1) ^ Integer.parseInt(mar0);
										mar1 = Integer.toString(result);
										Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+binary_code[index2]+" "+line+"\n");
									}								
									
								}
							}//END OF IF MAR-MAR
							else if(index2==-1){					//mar - immediate
								if(index1==8){						//mar0-immediate
									result = Integer.parseInt(mar0) ^ Integer.parseInt(words[2]);
									mar0 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+line+"\n");
								}
								else if(index1==9){					//mar1-immediate
									result = Integer.parseInt(mar1) ^ Integer.parseInt(words[2]);
									mar1 = Integer.toString(result);
									Window1.text.setText(Window1.text.getText()+"    "+instruction_code[13]+" "+binary_code[index1]+" "+line+"\n");
								}
							}//END OF IF MAR-IMMEDIATE
							else{
								System.out.println("ERROR at line: "+line_cnt);				//ERROR
								break;
							}
						}//DESTINATION = MAR
						
						if(index1==-1) { System.out.println("ERROR at line: "+line_cnt); break;}		//ERROR
					}//end of if BITWISE XOR
					
					//-----------------------------------JE------------------------------------------//
					
					if(words[0].equals("JE")){						
						index1 = FindRegister(words[1]);		//see if operand1 is a MAR
						if((index1==8)||(index1==9)){//MAR
							if(zeroFlag == "0000"){
								if(index1==8){ PC = mar0; System.out.println("MAR0 = "+mar0);}
								else if(index1==9){ PC = mar1; System.out.println("MAR1 = "+mar1);}
							}
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[14]+" "+binary_code[index1]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF JE
					
					//-----------------------------------JG------------------------------------------//
					
					if(words[0].equals("JG")){						
						index1 = FindRegister(words[1]);		//see if operand1 is a MAR
						if((index1==8)||(index1==9)){//MAR
							if((zeroFlag == "0000")&&(signFlag=="0000")){
								if(index1==8){ PC = mar0; System.out.println("MAR0 = "+mar0);}
								else if(index1==9){ PC = mar1; System.out.println("MAR1 = "+mar1);}
							}
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[15]+" "+binary_code[index1]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF JG
					
					//-----------------------------------JL------------------------------------------//
					
					if(words[0].equals("JL")){						
						index1 = FindRegister(words[1]);		//see if operand1 is a MAR
						if((index1==8)||(index1==9)){//MAR
							if(signFlag=="0000"){
								if(index1==8){ PC = mar0; System.out.println("MAR0 = "+mar0);}
								else if(index1==9){ PC = mar1; System.out.println("MAR1 = "+mar1);}
							}
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[16]+" "+binary_code[index1]+" "+line+"\n");
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF JL
					
					//-----------------------------------JMP------------------------------------------//
					
					if(words[0].equals("JMP")){						
						index1 = FindRegister(words[1]);		//see if operand1 is a MAR
						if((index1==8)||(index1==9)){//MAR
							if(index1==8){ PC = mar0; System.out.println("MAR0 = "+mar0);}
							else if(index1==9){ PC = mar1; System.out.println("MAR1 = "+mar1);}			
							Window1.text.setText(Window1.text.getText()+"    "+instruction_code[17]+" "+binary_code[index1]+" "+line+"\n");			
						}
						else{
							System.out.println("ERROR at line: "+line_cnt);				//ERROR
							break;
						}
					}//END OF JG
					
					line_cnt++; 							//LINE COUNTER
				}//end of while loop
			 }catch (IOException e1) {
					e1.printStackTrace();
			 }//end of 2nd try catch
		}
		
		
		/*
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
		*/	
		
	//	desktop.add(canvas4);
	//	desktop.add(canvas5);
	//	desktop.add(canvas6);
		
		
		
	
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
		if(word.equals("mar0")){
			return 8;
		}
		if(word.equals("mar1")){
			return 9;
		}
		
		else{
			return -1;
		}		
		
	}//end of FindRegister

}
