/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation.tool;

/**
 *
 * @author Vinay
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;

public class AutomationTool extends JFrame implements ActionListener
{
    JButton jButton1=new JButton("go");
    JButton jButton2=new JButton("open");
    JButton jButton3=new JButton("run");
    JTextArea jTextArea=new JTextArea("");
    JTextArea helpInfo = new JTextArea("");
    JTextField jFileName = new JTextField("");
    JLabel jLabel = new JLabel("Remote");
    JLabel prompt = new JLabel("Commands:");
    JLabel usage = new JLabel("Usage:");
    JLabel jFileNameLabel = new JLabel("Command file:");
    FileDialog fileopen=new FileDialog(this);//it open the file window
    String commands[] = new String[10000];
    int numberCommands;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem item1 = new JMenuItem("GetXY Point");
    JMenuItem item2 = new JMenuItem("Recordwords");
    JMenuItem item3 = new JMenuItem("Recordmouse");
    /**
     * @param args the command line arguments
     */

    public static void main(String args[])
    {
        new AutomationTool();
    }
	
    AutomationTool()
    {
	jButton1.addActionListener(this);
	jButton2.addActionListener(this);
	jButton3.addActionListener(this);
	getContentPane().setLayout(null);
        
        menuBar.add(menu);
        
        /*item1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        
        this.setJMenuBar(menuBar);
        this.setVisible(true);
        
        jTextArea.setEditable(true);//in this field user enter commands so it is editable
	jTextArea.setFont(new Font("Times Roman", Font.BOLD, 13));
	jTextArea.setLineWrap(true);//To get new line when enter new command
	jTextArea.setWrapStyleWord(true);//To get new line when enter new command
	getContentPane().add(jButton1);
	getContentPane().add(jButton2);
	getContentPane().add(jButton3);
	getContentPane().add(jTextArea);
	getContentPane().add(jLabel);
	getContentPane().add(jFileName);
	getContentPane().add(prompt);
        getContentPane().add(helpInfo);
        getContentPane().add(usage);
        getContentPane().add(jFileNameLabel);
        jLabel.setBounds(30, 0, 120, 60);
        jButton1.setBounds(145, 612, 50, 30);
	jButton2.setBounds(200, 612, 65, 30);
	jButton3.setBounds(30, 612, 100, 30);
        jLabel.setFont(new Font("Times Roman", Font.BOLD, 23));
        prompt.setBounds(20, 50, 100, 20);
        usage.setBounds(150, 50, 100, 20);
        jTextArea.setBounds(20, 70, 120, 533);
        jFileNameLabel.setBounds(150, 567, 90, 20);
	jFileName.setBounds(150, 587, 110, 20);
        helpInfo.setBounds(150, 70, 135, 500);
        helpInfo.setEditable(false);
        helpInfo.setText("Run a process \n    f:notepad.exe,filename\n"
		    +  "Type text:\n    t:abc\n    t:ALTDN"
            +  "\n    t:ALTUP"
            +  "\n    t:CTRLDN"
            +  "\n    t:CTRLUP"
            +  "\n    t:TAB"
            + "\n    t:ENTER"
            + "\n    t:ESCAPE"
			+ "\n    t:BACKSPACE"
			+ "\n    t:Functional Keys"
			+ "\n    t:SPACE"
			+ "\n    t:->  t:<-  t:>  t:<\n"
			+ "web access:"
			+ "\n    u:www.xyz.com\n"
			+ "mail composing window"
			+ "\n    e: "
            + "\nMove mouse:\n    m:x,y\n"
            + "Left Click:\n    l:\n"
            + "Right Click:\n    r:\n"
            + "Wait n m.s's:\n    w:n\n"
            + "Cap screen:\n    s:n,delay\n"
            + "Beep:\n    b:n");
		setTitle("Remote");
        setSize(315,714);
        setVisible(true);
	this.addWindowListener(new WindowAdapter()//for closing the tool when press close button
	{
            @Override
            public void windowClosing(WindowEvent e)
            {
		System.exit(0);
            }
        });
	}
	
        @Override
	public void actionPerformed(ActionEvent e)//when press enter buuton
	{
            if(e.getSource() == item1)
            {
                GetScreenPoint g= new GetScreenPoint();
                try
                {
                    g.getpoint();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
            if(e.getSource() == item2)
            {
                RecordData R= new RecordData();
                try
                {
                    R.getScreen();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
            if(e.getSource() == item3)
            {
                RecordMouse M= new RecordMouse();
                try
                {
                    M.getScreen();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
            }
            if(e.getSource()== jButton1)
            {
                int i=0;
		try
		{
                    setVisible(false);
                    Robot robot = new Robot();
                    robot.delay(500);
                    if(jTextArea.getText().equals("") && jFileName.getText().equals("")) // check no command or file entered or not
                    {
                        JOptionPane.showMessageDialog(null,"Error: enter any command or file");
			setVisible(true);
			return;
                    }
                    else if (!jFileName.getText().equals(""))//check filename entered
                    {
                        BufferedReader bufferedFile = new BufferedReader(new FileReader(jFileName.getText()));//for reading file
			int commandIndex = 0;
			String inline = "";
			while((inline = bufferedFile.readLine()) != null)//untill end of file
			{
                            commands[commandIndex++] = inline;
			}
			numberCommands = commandIndex;
                    }
                    else
                    {
			commands = jTextArea.getText().split("\n");//taking lines
			numberCommands = commands.length;//no of commands
                    }
                    for (int loopIndex = 0; loopIndex < numberCommands;loopIndex++)
                    {
			String operation = commands[loopIndex].substring(0, 1);// first  character
			String data = commands[loopIndex].substring(2);//remaing string
			switch(operation.toCharArray()[0]) 
			{
                            case 'f'://prcess running
				String pn[]=data.split(",");//split the 2 parts process,file
				int t=pn.length;
				if(t==1)//only proces
				{
                                    ProcessBuilder pb=new ProcessBuilder(pn[0]);
                                    pb.start();
				}
				else//both process and file
				{
                                    ProcessBuilder pb=new ProcessBuilder(pn[0],pn[1]);
                                    pb.start();
				}
				break;
                            case 't'://text operations
				if(data.equals("ALTDN"))
				{
                                    robot.keyPress(KeyEvent.VK_ALT);
				}
				else if(data.equals("ALTUP"))
				{
                                    robot.keyRelease(KeyEvent.VK_ALT);
				}
				if(data.equals("CTRLDN"))
				{
                                    robot.keyPress(KeyEvent.VK_CONTROL);
				}
                                else if(data.equals("CTRLUP"))
				{
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
				}
				else if(data.equals("ENTER")||data.equals("Enter"))
				{
                                    robot.keyPress(KeyEvent.VK_ENTER); 
                                    robot.keyRelease(KeyEvent.VK_ENTER);
				}
				else if(data.equals("TAB")||data.equals("Tab"))
				{
                                    robot.keyPress(KeyEvent.VK_TAB);
                                    robot.keyRelease(KeyEvent.VK_TAB);
				}
                                else if(data.equals("ESCAPE")||data.equals("Escape"))
				{
                                    robot.keyPress(KeyEvent.VK_ESCAPE);
                                    robot.keyRelease(KeyEvent.VK_ESCAPE);
				}
				else if(data.equals("DELETE")||data.equals("Delete"))
				{
                                    robot.keyPress(KeyEvent.VK_DELETE);
                                    robot.keyRelease(KeyEvent.VK_DELETE);
				}
				else if(data.equals("BACKSPACE")||data.equals("Backspace"))
				{
                                    robot.keyPress(KeyEvent.VK_BACK_SPACE);
                                    robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				}
				else if(data.equals("F1"))
				{
                                    robot.keyPress(KeyEvent.VK_F1);
                                    robot.keyRelease(KeyEvent.VK_F1);
				}
				else if(data.equals("F2"))
				{
                                    robot.keyPress(KeyEvent.VK_F2);
                                    robot.keyRelease(KeyEvent.VK_F2);
				}
				else if(data.equals("F3"))
				{
                                    robot.keyPress(KeyEvent.VK_F3);
                                    robot.keyRelease(KeyEvent.VK_F3);
				}
				else if(data.equals("F4"))
				{
                                    robot.keyPress(KeyEvent.VK_F4);
                                    robot.keyRelease(KeyEvent.VK_F4);
				}
				else if(data.equals("F5"))
				{
                                    robot.keyPress(KeyEvent.VK_F5);
                                    robot.keyRelease(KeyEvent.VK_F5);
				}
				else if(data.equals("F6"))
				{
                                    robot.keyPress(KeyEvent.VK_F6);
                                    robot.keyRelease(KeyEvent.VK_F6);
				}
				else if(data.equals("F7"))
				{
                                    robot.keyPress(KeyEvent.VK_F7);
                                    robot.keyRelease(KeyEvent.VK_F7);
				}
				else if(data.equals("F8"))
				{
                                    robot.keyPress(KeyEvent.VK_F8);
                                    robot.keyRelease(KeyEvent.VK_F8);
				}
				else if(data.equals("F9"))
				{
                                    robot.keyPress(KeyEvent.VK_F9);
                                    robot.keyRelease(KeyEvent.VK_F9);
				}
				else if(data.equals("F10"))
				{
                                    robot.keyPress(KeyEvent.VK_F10);
                                    robot.keyRelease(KeyEvent.VK_F10);
				}
				else if(data.equals("F11"))
				{
                                    robot.keyPress(KeyEvent.VK_F11);
                                    robot.keyRelease(KeyEvent.VK_F11);
				}
				else if(data.equals("F12"))
				{
                                    robot.keyPress(KeyEvent.VK_F12);
                                    robot.keyRelease(KeyEvent.VK_F12);
				}
				else if(data.equals("<-")||data.equals("Left"))
				{
                                    robot.keyPress(KeyEvent.VK_LEFT);
                                    robot.keyRelease(KeyEvent.VK_LEFT);
				}
                                else if(data.equals("->")||data.equals("Right"))
				{
                                    robot.keyPress(KeyEvent.VK_RIGHT);
                                    robot.keyRelease(KeyEvent.VK_RIGHT);
								
				}
				else if(data.equals(">")||data.equals("Up"))
				{
                                    robot.keyPress(KeyEvent.VK_UP);
                                    robot.keyRelease(KeyEvent.VK_UP);
				}
				else if(data.equals("<")||data.equals("Down"))
				{
                                    robot.keyPress(KeyEvent.VK_DOWN);
                                    robot.keyRelease(KeyEvent.VK_DOWN);
				}
                                else if(data.equals("SPACE")||data.equals("Space"))
				{
                                    robot.keyPress(KeyEvent.VK_SPACE);
                                    robot.keyRelease(KeyEvent.VK_SPACE);
				}
				else
				{
                                    char chars[] = data.toCharArray();
                                    for(int charIndex = 0;charIndex < chars.length;charIndex++)
                                    {
					if(chars[charIndex] >= 'a' && chars[charIndex] <= 'z')
					{
                                            robot.keyPress((int)chars[charIndex]- ('a' -'A'));
                                            robot.keyRelease((int)chars[charIndex]- ('a' -'A'));
					}
					else if(chars[charIndex] >= 'A' && chars[charIndex] <= 'Z')
					{
                                            robot.keyPress(KeyEvent.VK_SHIFT);
                                            robot.keyPress((int)chars[charIndex]);
                                            robot.keyRelease((int) chars[charIndex]);
                                            robot.keyRelease(KeyEvent.VK_SHIFT);
					}
					else
					{
                                            robot.keyPress((int)chars[charIndex]);
                                            robot.keyRelease((int)chars[charIndex]);
                                            robot.delay(100);
					}
                                    }
				}
				break;
						
				case 'm'://moving mouse
                                    String coords[] = data.split(",");
                                    int x = Integer.parseInt(coords[0]);
                                    int y = Integer.parseInt(coords[1]);
                                    robot.mouseMove(x, y);
                                    robot.delay(500);
				break;
				case 'l'://single click
                                    robot.mousePress(InputEvent.BUTTON1_MASK); 
                                    robot.delay(1000);
                                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
				break;
				case 'r'://right click
                                    robot.mousePress(InputEvent.BUTTON3_MASK);
                                    robot.delay(1000);
                                    robot.mouseRelease(InputEvent.BUTTON3_MASK);
				break; 
				case 'w'://delay 
                                    int numberSeconds = Integer.parseInt(data);
                                    robot.delay(numberSeconds);
				break;
				case 's'://image
                                    String oo[] = data.split(",");
                                    int n = Integer.parseInt(oo[0]);
                                    int d = Integer.parseInt(oo[1]);
                                    for(i=0;i<n;i++)
                                    {
                                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(screenSize));
					File outputFile = new File("cap"+i+".png");
					ImageIO.write(bufferedImage,"PNG",outputFile);
					robot.delay(d);
                                    }
				break;
				case 'b'://beep sound
                                    for(i=0;i<Integer.parseInt(data);i++)
                                    {
					Toolkit.getDefaultToolkit().beep();
					robot.delay(300);
                                    }
				break; 
				case 'u'://Launches the user default browser to display a URI (unified resource index)
                                    Desktop.getDesktop().browse(new URI(data));
				break;
				case 'e'://Launches the mail composing window of the user default mail client.
                                    Desktop.getDesktop().mail();
				break;
				default: 
                                    //JOptionPane.showMessageDialog(null,"command is Wrong","cmd",JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
                }
                    setVisible(true);
            }
            else if(e.getSource() == jButton2)//open button
            {
		fileopen.setVisible(true);
		jFileName.setText(fileopen.getDirectory() + "\\" + fileopen.getFile());	
            }
            else if(e.getSource() == jButton3)//run button
            {
                fileopen.setVisible(true);
		try
		{	
                    //Desktop.getDesktop().open(new File(fileopen.getDirectory() + "\\" + fileopen.getFile()));
                    if(fileopen.getFile().equals("")||fileopen.getDirectory().equals(""))
                    {
                        ProcessBuilder pb=new ProcessBuilder(fileopen.getDirectory() + "\\" + fileopen.getFile());
                    }
                    else
                    {
			ProcessBuilder pb=new ProcessBuilder(fileopen.getDirectory() + "\\" + fileopen.getFile());
			pb.start();
                    }
		}
		catch (Exception ex)
		{
                    JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
		}
            }
	}	
}
