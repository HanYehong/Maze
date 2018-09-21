import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

public class LabyrinthFrame extends JFrame implements ActionListener,Runnable{
	protected JLabel jlabel;
	protected JLabel jlabel2;
	protected JButton button;
	protected JButton button2;
	protected JButton button3;
	protected Datas labyrinth[][];
	protected JButton but[][];
	protected Datas start,end;
	protected LinkedStack<Datas> stack;
	protected Thread thread;
	protected Datas data;
	protected Datas last;
	protected FileOutputStream fout;
	protected DataOutputStream objout;
	protected FileInputStream fin;
	protected DataInputStream objin1;
	protected FileOutputStream fout1;
	protected DataOutputStream objout1;
	protected FileInputStream fin1;
	protected DataInputStream objin;
	protected FileOutputStream fout2[];
	protected DataOutputStream objout2[];
	protected FileInputStream fin2[];
	protected DataInputStream objin2[];
/*	protected FileOutputStream fout3[];
	protected DataOutputStream objout3[];
	protected FileInputStream fin3[];
	protected DataInputStream objin3[];*/
	protected int mark;
	protected JComboBox<String> combox;
	protected JRadioButton radiobs[];
	static boolean isSure=false;
	private PrintWriter cout;
	private BufferedReader br;
	Socket socket;
	Socket socket1;
	String road[][];
	int num;
	
	public LabyrinthFrame(Datas labyrinth[][],Datas start,Datas end,Socket socket)
	{
		data=start;
		last=start;
		stack=new LinkedStack<Datas>();
		this.start=new Datas(start.isWhite,start.x,start.y);
		this.end=new Datas(end.isWhite,end.x,end.y);
		this.but=new JButton[labyrinth.length][labyrinth[0].length];
		this.labyrinth=labyrinth;
		try {
			fout=new FileOutputStream("E:\\labyrinth.obj");
			objout=new DataOutputStream(fout);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("迷宫");
		this.setBounds(300, 50, 860, 610);
		GridLayout grid=new GridLayout(8,12);
		ImageIcon img2=new ImageIcon("D:\\砖块.png");
		jlabel=new JLabel();
		jlabel.setLayout(new FlowLayout());
		this.getLayeredPane().add(jlabel,"North");
		jlabel.setBounds(0,0,850,40);
		((JPanel)getContentPane()).setOpaque(false);
		jlabel2=new JLabel();
		jlabel2.setBounds(0,40,850,533);
		jlabel2.setLayout(grid);
		this.getLayeredPane().add(jlabel2);
		for(int i=0;i<labyrinth.length;i++)
			for(int j=0;j<labyrinth[0].length;j++)
			{
				but[i][j]=new JButton();
				if(labyrinth[i][j].isWhite==true)
				{
					but[i][j].setBackground(Color.LIGHT_GRAY);
					jlabel2.add(but[i][j]);
					but[i][j].setBorderPainted(false);
				}
				else
				{
					but[i][j].setContentAreaFilled(false);
					jlabel2.add(but[i][j]);
					but[i][j].setIcon(img2);
					but[i][j].setBorderPainted(false);
				}
			}
		JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,this.jlabel,this.jlabel2);
		split.setDividerLocation(40);
		this.add(split);
		try 
		{
	//		fout = new FileOutputStream("E:\\radiobs.char");
	//		objout=new DataOutputStream(fout);
			fin=new FileInputStream("E:\\radiobs.char");
			objin=new DataInputStream(fin);
		//	fout1 = new FileOutputStream("E:\\num.int");
		//	objout1=new DataOutputStream(fout1);
			fin1=new FileInputStream("E:\\num.int");
			objin1=new DataInputStream(fin1);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		try {
		//	objout1.writeInt(4);
			num=objin1.readInt();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		fout2=new FileOutputStream[num-1];
		objout2=new DataOutputStream[num-1];
		
		fin2=new FileInputStream[num-1];
		objin2=new DataInputStream[num-1];
		
	/*	try{
				objout3[0]=new DataOutputStream(fout3[0]=new FileOutputStream("E:\\se1.int"));
				objout3[0].writeInt(1);objout3[0].writeInt(0);objout3[0].writeInt(4);objout3[0].writeInt(11);
				objout3[1]=new DataOutputStream(fout3[1]=new FileOutputStream("E:\\se2.int"));
				objout3[1].writeInt(1);objout3[1].writeInt(0);objout3[1].writeInt(4);objout3[1].writeInt(11);
				objout3[2]=new DataOutputStream(fout3[2]=new FileOutputStream("E:\\se3.int"));
				objout3[2].writeInt(3);objout3[2].writeInt(0);objout3[2].writeInt(0);objout3[2].writeInt(9);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
	//		objout3[h].writeInt(arg0);
	//	fin3=new FileInputStream[num-1];
	//	objin3=new DataInputStream[num-1];
		String str[]=new String[num];
	//	String str[]={"地图1","地图2","地图3","自定义地图"};
		int h;
		for(h=0;h<num-1;h++)
		{
			String s="";
			for(int y=0;y<3;y++)
			{
				try
				{
					s+=objin.readChar()+"";
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			str[h]=s;
		}
		str[h]="自定义地图";
	/*	for(h=0;h<num-1;h++)
		{
			for(int y=0;y<3;y++)
			{
				try
				{
					objout.writeChar(str[h].charAt(y));
				} 
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}*/
		this.combox=new JComboBox<String>(str);
		jlabel.add(new JLabel("地图选择"));
		jlabel.add(this.combox);
		this.combox.addActionListener(this);
		jlabel.add(new JLabel("          "));
		button=new JButton("开始");
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		jlabel.add(button);
		button2=new JButton("停止");
		button2.setContentAreaFilled(false);
		button2.addActionListener(this);
		button2.setEnabled(false);
		jlabel.add(button2);
		button3=new JButton("回放");
		button3.setContentAreaFilled(false);
		button3.addActionListener(this);
		jlabel.add(button3);
		button3.setEnabled(false);
		this.setVisible(true);
		jlabel.add(new JLabel("     模式选择:"));
		String str2[]={"亲自上阵","自动寻路"};
		JPanel panel=new JPanel(new GridLayout(1,2));
		jlabel.add(panel);
		ButtonGroup bgroup=new ButtonGroup();
		this.radiobs=new JRadioButton[str2.length];
		for(int i=0;i<this.radiobs.length;i++)
		{
			panel.add(this.radiobs[i]=new JRadioButton(str2[i]));
			bgroup.add(this.radiobs[i]);
			this.radiobs[i].addActionListener(this);
		}
		this.radiobs[0].setSelected(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		if(this.radiobs[0].isSelected())
		{
			this.button.setEnabled(false);
			this.button2.setEnabled(false);
			this.button3.setEnabled(false);
		}
		else
		{
			this.button.setEnabled(true);
			this.button2.setEnabled(false);
			this.button3.setEnabled(false);
		}
		Toolkit tk=Toolkit.getDefaultToolkit();
		tk.addAWTEventListener(new MyKeyListener(), AWTEvent.KEY_EVENT_MASK);
		this.socket=socket;
		try
		{
			this.cout=new PrintWriter(socket.getOutputStream(),true);
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int c,d,e,f;
		try 
		{
			c=Integer.parseInt(br.readLine());
			d=Integer.parseInt(br.readLine());
			e=c;
			f=d;
			but[c][d].setText("1");
			while(true)
			{
				try
				{
					c=Integer.parseInt(br.readLine());
					d=Integer.parseInt(br.readLine());
					but[e][f].setText("");
					but[c][d].setText("1");
					if(c==this.end.x&&d==this.end.y)
						JOptionPane.showMessageDialog(this,"You Lose!");
					e=c;
					f=d;
				}
				catch (IOException e1) 
				{
					break;
				}
			}
		} 
		catch (NumberFormatException e1) {} 
		catch (IOException e1) {}
	}
	public LabyrinthFrame(Datas labyrinth[][],Datas start,Datas end,String host, int port) throws IOException
	{
		this(labyrinth,start,end,new Socket(host,port));
	}
	
	public void run()
	{
		if(mark==0)
		{
			try 
			{
				fout = new FileOutputStream("E:\\labyrinth.obj");
				objout=new DataOutputStream(fout);
			} 
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			road=Find();
			int i,j=0,a=0,b=0,c=0,d=0;
			
			for(i=0;road[i][0]!=null;i++)
			{
				try
				{
					String s="正确通路（";
					for(j=0;road[i][j]!=null;j++)
					{	
						a=Integer.parseInt(road[i][j]);
						b=Integer.parseInt(road[i][++j]);
						s+="["+a+","+b+"]";
						objout.writeInt(a);
						objout.writeInt(b);
						but[a][b].setText("♀");
						this.cout.println(a);
						this.cout.println(b);
						thread.sleep(500);
						but[a][b].setText("");	
					}
					s+="）";
					System.out.println(s);
				}
				catch(IOException ioex){} 
				catch (InterruptedException e) 
				{
					but[a][b].setText("");
					button3.setEnabled(true);
					button.setEnabled(true);
					button2.setEnabled(false);
					try 
					{
						br.close();
						this.cout.close();
						this.socket.close();
					}		
					catch (IOException e1) 
					{
						e1.printStackTrace();
					};
					break;
				}
			}
			System.out.println("一共有"+i+"条。");
			try
			{
				objout.close();
				fout.close();
				br.close();
				this.cout.close();
				this.socket.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			button3.setEnabled(true);
			button.setEnabled(true);
			button2.setEnabled(false);
		}
		
		if(mark==1)
		{
			int i=0,j=0;
			try
			{
				FileInputStream fin=new FileInputStream("E:\\labyrinth.obj");
				DataInputStream objin=new DataInputStream(fin);
				while(true)
				{
					try
					{
						i=objin.readInt();
						j=objin.readInt();
						this.but[i][j].setText("1");
						thread.sleep(500);
						this.but[i][j].setText("");
					}
					catch(EOFException eofx)
					{
						but[i][j].setText("");
						button3.setEnabled(true);
						button.setEnabled(true);
						button2.setEnabled(false);
						break;
					} 
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						but[i][j].setText("");
						button3.setEnabled(true);
						button.setEnabled(true);
						button2.setEnabled(false);
						break;
					} 
				}
				objin.close();
				fin.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			button3.setEnabled(true);
			button.setEnabled(true);
			button2.setEnabled(false);
		}
	}
	public String[][] Find()
	{	
		String road[][]=new String[100][100];
		for(int i=0;i<labyrinth.length;i++)
			for(int j=0;j<labyrinth[0].length;j++)
				but[i][j].setText("");
		
		for(int i=0;i<labyrinth.length;i++)
			for(int j=0;j<labyrinth[0].length;j++)
			{
				labyrinth[i][j].isWalk=false;
				labyrinth[i][j].isExerting=false;
				labyrinth[i][j].isfork=false;
			}
	
		int cout=0;
		int num=0;
		data=start;
		data.isfork=true;
		last=start;
		stack=new LinkedStack<Datas>();
		
		for(int i=0;i<labyrinth.length;i++)
			for(int j=0;j<labyrinth[0].length;j++)
			{
				if((j!=this.labyrinth[0].length-1&&this.labyrinth[i][j+1].isWalk==false&&this.labyrinth[i][j+1].isWhite==true))
					cout++;
				if(i!=0&&this.labyrinth[i-1][j].isWalk==false&&this.labyrinth[i-1][j].isWhite==true)
					cout++;
				if(j!=0&&this.labyrinth[i][j-1].isWalk==false&&this.labyrinth[i][j-1].isWhite==true)
					cout++;
				if(i!=this.labyrinth.length-1&&this.labyrinth[i+1][j].isWalk==false&&this.labyrinth[i+1][j].isWhite==true)
					cout++;
				if(cout>=3)
					labyrinth[i][j].isfork=true;
				cout=0;
			}
		if(data.y!=this.labyrinth[0].length-1&&this.labyrinth[data.x][data.y+1].isWalk==false&&this.labyrinth[data.x][data.y+1].isWhite==true)
		{
			stack.push(data);
			data.isWalk=true;
			last=data;
			data=this.labyrinth[data.x][data.y+1];
		}
		else if(data.x!=0&&this.labyrinth[data.x-1][data.y].isWalk==false&&this.labyrinth[data.x-1][data.y].isWhite==true)
		{
			stack.push(data);
			data.isWalk=true;
			last=data;
			data=this.labyrinth[data.x-1][data.y];
		}
		else if(data.x!=this.labyrinth.length-1&&this.labyrinth[data.x+1][data.y].isWalk==false&&this.labyrinth[data.x+1][data.y].isWhite==true)
		{
			stack.push(data);
			data.isWalk=true;
			last=data;
			data=this.labyrinth[data.x+1][data.y];
		}
		else if(data.y!=0&&this.labyrinth[data.x][data.y-1].isWalk==false&&this.labyrinth[data.x][data.y-1].isWhite==true)
		{
			stack.push(data);
			data.isWalk=true;
			last=data;
			data=this.labyrinth[data.x][data.y-1];
		}

		while(!stack.isEmpty())
		{
			if(data.x==end.x&&data.y==end.y)
			{
				Datas dataa;
				num++;
				LinkedStack<Datas> stack2=new LinkedStack<Datas>();
				while(stack.isEmpty()==false)
				{
					Datas data1=stack.pop();
					stack2.push(data1);
				}
				int k=0;
				while(!stack2.isEmpty())
				{
					dataa=stack2.pop();
					if(num-1>=0)
						road[num-1][k]=dataa.x+"";
					if(num-1>=0)
					road[num-1][++k]=dataa.y+"";
					k++;
					stack.push(dataa);
				}
				if(num-1>=0)
				{
					road[num-1][k]=end.x+"";
					road[num-1][++k]=end.y+"";
				}
				last=data;
				data=stack.pop();
				data.isWalk=false;
				last=data;
				data=stack.pop();
				data.isWalk=false;
			}
			
			if(data.y!=this.labyrinth[0].length-1&&this.labyrinth[data.x][data.y+1].isWalk==false&&this.labyrinth[data.x][data.y+1].isWhite==true&&!(last.x==data.x&&last.y==(data.y+1)))
			{
				stack.push(data);
				data.isWalk=true;
				last=data;
				data=this.labyrinth[data.x][data.y+1];
			}
			else if(data.x!=0&&this.labyrinth[data.x-1][data.y].isWalk==false&&this.labyrinth[data.x-1][data.y].isWhite==true&&!(last.x==(data.x-1)&&last.y==data.y))
			{
				stack.push(data);
				data.isWalk=true;
				last=data;
				data=this.labyrinth[data.x-1][data.y];
			}
			else if(data.x!=this.labyrinth.length-1&&this.labyrinth[data.x+1][data.y].isWalk==false&&this.labyrinth[data.x+1][data.y].isWhite==true&&!(last.x==(data.x+1)&&last.y==data.y))
			{
				stack.push(data);
				data.isWalk=true;
				last=data;
				data=this.labyrinth[data.x+1][data.y];
			}
			else if(data.y!=0&&this.labyrinth[data.x][data.y-1].isWalk==false&&this.labyrinth[data.x][data.y-1].isWhite==true&&!(last.x==data.x&&last.y==(data.y-1)))
			{
				stack.push(data);
				data.isWalk=true;
				last=data;
				data=this.labyrinth[data.x][data.y-1];
			}	
			else
			{
				if(data.isfork==true)
				{
					Datas temp=stack.peak();
					Datas temp2=stack.peak();
					if(temp2.isExerting==false)
					{
						if(data.x-2>=0&&labyrinth[data.x-2][data.y].isExerting==false)
							labyrinth[data.x-1][data.y].isWalk=false;
						if(data.x+2<labyrinth.length&&labyrinth[data.x+2][data.y].isExerting==false)
							labyrinth[data.x+1][data.y].isWalk=false;
						if(data.y-2>=0&&labyrinth[data.x][data.y-2].isExerting==false)
							labyrinth[data.x][data.y-1].isWalk=false;
						if(data.y+2<labyrinth[0].length&&labyrinth[data.x][data.y+2].isExerting==false)
							labyrinth[data.x][data.y+1].isWalk=false;
						data.isExerting=false;
					}
					else
					{
						labyrinth[data.x-1][data.y].isWalk=false;
						labyrinth[data.x+1][data.y].isWalk=false;
						if(data.y-1>0)
							labyrinth[data.x][data.y-1].isWalk=false;
						labyrinth[data.x][data.y+1].isWalk=false;
						data.isExerting=false;
					}
					stack.push(temp2);
					stack.push(temp);
				}
				last=data;
				last.isWalk=false;
				data=stack.pop();
				while(data.isfork==false)
				{
					last=data;
					last.isWalk=false;
					data=stack.pop();	
					
				}
				last.isWalk=true;
				data.isExerting=true;
			}
		}
		return road;
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getSource()==button)
		{
			this.mark=0;
			button3.setEnabled(false);
			button.setEnabled(false);
			button2.setEnabled(true);
			thread=new Thread(this);
			thread.start();
		}		
		if(ev.getSource()==button2)
		{
			button3.setEnabled(true);
			button.setEnabled(true);
			button2.setEnabled(false);
			thread.interrupt();
		}
		if(ev.getSource()==button3)
		{
			button3.setEnabled(false);
			button.setEnabled(false);
			button2.setEnabled(true);
			this.mark=1;
			thread=new Thread(this);
			thread.start();
		}
		if(ev.getSource() instanceof JRadioButton)
		{
			if(this.radiobs[0].isSelected())
			{
				this.button.setEnabled(false);
				this.button2.setEnabled(false);
				this.button3.setEnabled(false);
				try
				{
					fout=new FileOutputStream("E:\\labyrinth.obj");
					objout=new DataOutputStream(fout);
					
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				} 
					
			}
			if(this.radiobs[1].isSelected())
			{
				this.button.setEnabled(true);
				this.button2.setEnabled(false);
				this.button3.setEnabled(false);
			}
		}
		if(ev.getSource()==this.combox)
		{
			int k=this.combox.getSelectedIndex();
			if(k!=this.combox.getItemCount()-1)
			{
				try {
					fin2[k]=new FileInputStream("E:\\maze"+(k+1)+".boolean");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				objin2[k]=new DataInputStream(fin2[k]);
				this.but=new JButton[this.labyrinth.length][this.labyrinth[0].length];
				ImageIcon img2=new ImageIcon("D:\\砖块.png");
				this.jlabel2.removeAll();
				boolean bool[][]={
						{false,false,false,false,false,false,false,false,false,false,false,false},
						{true,true,false,true,true,true,true,true,true,true,true,false},
						{false,true,false,true,false,false,false,true,false,false,false,false},
						{false,true,true,true,true,true,true,true,true,true,true,false},
						{false,true,false,false,false,false,false,true,false,false,true,true},
						{false,true,true,true,true,true,true,true,false,false,true,false},
						{false,true,false,false,false,false,false,true,true,true,true,false},
						{false,false,false,false,false,false,false,false,false,false,false,false}};
				for(int i=0;i<8;i++)
					for(int j=0;j<12;j++)
					{
						try
						{
						//	labyrinth[i][j].isWhite=bool[i][j];
							labyrinth[i][j].isWhite=objin2[k].readBoolean();
						//	objout2[k].writeBoolean(bool[i][j]);
						} 
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				for(int i=0;i<labyrinth.length;i++)
					for(int j=0;j<labyrinth[0].length;j++)
					{
						if(labyrinth[i][j].isWhite==true)
						{
							but[i][j]=new JButton();
							but[i][j].setBackground(Color.LIGHT_GRAY);
							jlabel2.add(but[i][j]);
							but[i][j].setBorderPainted(false);
						}
						else
						{
							but[i][j]=new JButton();
							but[i][j].setContentAreaFilled(false);
							jlabel2.add(but[i][j]);
							but[i][j].setIcon(img2);
						but[i][j].setBorderPainted(false);
						}
					}
		/*		try {
					fout3=new FileOutputStream[num];
					
					objout3=new DataOutputStream[num];
					fin3[k]=new FileInputStream("E:\\se"+(k+1)+".int");
					objin3[k]=new DataInputStream(fin3[k]);
					this.start.x=objin3[k].readInt();
					this.start.y=objin3[k].readInt();
					this.end.x=objin3[k].readInt();
					this.end.y=objin3[k].readInt();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();*/
				
				
			}
		else
		{
				num++;
				System.out.println(num);
				fout2=new FileOutputStream[num];
				objout2=new DataOutputStream[num];
				fin2=new FileInputStream[num];
				objin2=new DataInputStream[num];
			/*	fout3=new FileOutputStream[num];
				objout3=new DataOutputStream[num];
				fin3=new FileInputStream[num];
				objin3=new DataInputStream[num];
				
				try {
					fout3[k]=new FileOutputStream("E:\\se"+(k+1)+".int");
					objout3[k]=new DataOutputStream(fout3[k]);
				objout3[k].writeInt(Maze.mem.sx);
				objout3[k].writeInt(Maze.mem.sy);
				objout3[k].writeInt(Maze.mem.ex);
				objout3[k].writeInt(Maze.mem.ey);
			
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				try {
					fout2[k]=new FileOutputStream("E:\\maze"+(k+1)+".boolean");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				objout2[k]=new DataOutputStream(fout2[k]);
				Maze maze=new Maze(this,true,8,12);
				if(this.isSure==true)
				{
					for(int i=0;i<labyrinth.length;i++)
						for(int j=0;j<labyrinth[0].length;j++)
						{
							labyrinth[i][j].isWhite=false;
							labyrinth[i][j].isWalk=false;
							labyrinth[i][j].isfork=false;
							labyrinth[i][j].isExerting=false;
						}
					for(int i=0;Maze.mem.floor[i][0]!=0;i++)
					{
						labyrinth[Maze.mem.floor[i][0]][Maze.mem.floor[i][1]].isWhite=true;
					}
					this.jlabel2.removeAll();
					for(int i=0;i<labyrinth.length;i++)
						for(int j=0;j<labyrinth[0].length;j++)
						{
							try {
								objout2[k].writeBoolean(labyrinth[i][j].isWhite);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(labyrinth[i][j].isWhite==true)
							{
								but[i][j]=new JButton();
								but[i][j].setBackground(Maze.mem.f);
								jlabel2.add(but[i][j]);
								but[i][j].setBorderPainted(false);
							}
							else
							{
								but[i][j]=new JButton();
								if(Maze.mem.w==null)
									but[i][j].setIcon(Maze.mem.img2);
								else
									but[i][j].setBackground(Maze.mem.w);
							//	but[i][j].setContentAreaFilled(false);
								jlabel2.add(but[i][j]);
							//	but[i][j].setIcon(img2);
							but[i][j].setBorderPainted(false);
							}
						}
					start.x=Maze.mem.sx;
					start.y=Maze.mem.sy;
					end.x=Maze.mem.ex;
					end.y=Maze.mem.ey;
					this.combox.insertItemAt("地图"+(num-1), num-2);
					this.combox.setSelectedIndex(num-2);
					String str[]=new String[num];
					try 
					{
						fout = new FileOutputStream("E:\\radiobs.char");
						objout=new DataOutputStream(fout);
				//		fin=new FileInputStream("E:\\radiobs.char");
				//		objin=new DataInputStream(fin);
						fout1 = new FileOutputStream("E:\\num.int");
						objout1=new DataOutputStream(fout1);
				//		fin1=new FileInputStream("E:\\num.int");
				//		objin1=new DataInputStream(fin1);
						objout1.writeInt(num);
					} 
					catch (FileNotFoundException e1) 
					{
						e1.printStackTrace();
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					int h;
					for(h=0;h<num-1;h++)
					{
						for(int y=0;y<3;y++)
						{
							try
							{
								objout.writeChar(this.combox.getItemAt(h).charAt(y));
							} 
							catch (IOException e1)
							{
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Datas  data[][]=new Datas[8][12];
		
		boolean bool[][]={
				{false,false,false,false,false,false,false,false,false,false,false,false},
				{true,true,false,true,true,true,true,true,true,true,true,false},
				{false,true,false,true,false,false,false,true,false,false,false,false},
				{false,true,true,true,true,true,true,true,true,true,true,false},
				{false,true,false,false,false,false,false,true,false,false,true,true},
				{false,true,true,true,true,true,true,true,false,false,true,false},
				{false,true,false,false,false,false,false,true,true,true,true,false},
				{false,false,false,false,false,false,false,false,false,false,false,false}};
	
		for(int i=0;i<8;i++)
			for(int j=0;j<12;j++)
			{
				data[i][j]=new Datas(bool[i][j],i,j);
			}
		try {
			LabyrinthFrame laby=new LabyrinthFrame(data,data[1][0],data[4][11],"127.0.0.1",2001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class MyKeyListener implements AWTEventListener{
	public void eventDispatched(AWTEvent event)
	{
		if(event.getClass()==KeyEvent.class)
		{
			KeyEvent keyEvent=(KeyEvent)event;
			if(keyEvent.getID()==KeyEvent.KEY_PRESSED)
			{
				keyPressed(keyEvent);
			}
			else if(keyEvent.getID()==KeyEvent.KEY_RELEASED)
			{
				keyReleased(keyEvent);
			}
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			System.out.println("↑");
			if(data.x!=0&&LabyrinthFrame.this.labyrinth[data.x-1][data.y].isWhite==true)
			{
				LabyrinthFrame.this.but[data.x][data.y].setText("");
				data.x=data.x-1;
				data.y=data.y;
				LabyrinthFrame.this.but[data.x][data.y].setText("1");
				LabyrinthFrame.this.cout.println(data.x);
				LabyrinthFrame.this.cout.println(data.y);
					try {
						objout.writeInt(data.x);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						objout.writeInt(data.y);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(data.x==end.x&&data.y==end.y)
				{
					JOptionPane.showMessageDialog(LabyrinthFrame.this,"You Win!");
					LabyrinthFrame.this.but[data.x][data.y].setText("");
					data=start;
					last=start;
					button.setEnabled(false);
					button2.setEnabled(false);
					button3.setEnabled(true);
					radiobs[0].setSelected(true);
					try {
						objout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						fout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			System.out.println("←");

			if(data.y!=0&&labyrinth[data.x][data.y-1].isWhite==true)
			{
				LabyrinthFrame.this.but[data.x][data.y].setText("");
				data.x=data.x;
				data.y=data.y-1;
				LabyrinthFrame.this.but[data.x][data.y].setText("1");
				LabyrinthFrame.this.cout.println(data.x);
				LabyrinthFrame.this.cout.println(data.y);
					try {
						objout.writeInt(data.x);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						objout.writeInt(data.y);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(data.x==end.x&&data.y==end.y)
				{
					JOptionPane.showMessageDialog(LabyrinthFrame.this,"You Win!");
					LabyrinthFrame.this.but[data.x][data.y].setText("");
					data=start;
					last=start;
					button.setEnabled(false);
					button2.setEnabled(false);
					button3.setEnabled(true);
					radiobs[0].setSelected(true);
					try {
						objout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						fout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(data.y!=LabyrinthFrame.this.labyrinth[0].length-1&&LabyrinthFrame.this.labyrinth[data.x][data.y+1].isWhite==true)
			{
				LabyrinthFrame.this.but[data.x][data.y].setText("");
				data.x=data.x;
				data.y=data.y+1;
				LabyrinthFrame.this.but[data.x][data.y].setText("1");
				LabyrinthFrame.this.cout.println(data.x);
				LabyrinthFrame.this.cout.println(data.y);
					try {
						objout.writeInt(data.x);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						objout.writeInt(data.y);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(data.x==end.x&&data.y==end.y)
				{
					JOptionPane.showMessageDialog(LabyrinthFrame.this,"You Win!");
					LabyrinthFrame.this.but[data.x][data.y].setText("");
					data=start;
					last=start;
					button.setEnabled(false);
					button2.setEnabled(false);
					button3.setEnabled(true);
					radiobs[0].setSelected(true);
					try {
						objout.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						fout.close();
					} catch (IOException e1) {
		
						e1.printStackTrace();
					}
				}
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			System.out.println("↓");
			if(data.x!=labyrinth.length-1&&labyrinth[data.x+1][data.y].isWhite==true)
			{
				LabyrinthFrame.this.but[data.x][data.y].setText("");
				data.x=data.x+1;
				data.y=data.y;
				LabyrinthFrame.this.but[data.x][data.y].setText("1");
				LabyrinthFrame.this.cout.println(data.x);
				LabyrinthFrame.this.cout.println(data.y);
					try {
						objout.writeInt(data.x);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						objout.writeInt(data.y);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if(data.x==end.x&&data.y==end.y)
				{
					JOptionPane.showMessageDialog(LabyrinthFrame.this,"You Win!");
					LabyrinthFrame.this.but[data.x][data.y].setText("");
					data=start;
					last=start;
					button.setEnabled(false);
					button2.setEnabled(false);
					button3.setEnabled(true);
					radiobs[0].setSelected(true);
					try {
						objout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						fout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
	}
}