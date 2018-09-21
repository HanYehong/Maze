import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Maze extends JDialog implements ActionListener
{
	JButton b[][];
	JPanel jpanel2;
	JPanel jpanel1;
	JButton b1;
	JButton b3,b4;
	JButton b5,b6;
	JButton b7,b8,b9;
	JPanel jpanel3;
	JPanel jpanel4;
	JRadioButton radiobs[];
	int floor[][];
	int wall[][];
	int how1;
	int how2;
	static member mem;
	Color f;
	Color w;
	int sx,sy,ex,ey;
	File file;
	JFileChooser fchooser;
	int Long;
	ImageIcon img1,img2;
	
	public Maze(Frame owner,boolean model,int i,int j)
	{
		super(owner,model);
		this.setTitle("�Զ����Թ�");
		this.setBounds(300, 50, 750, 380);
		GridLayout grid=new GridLayout(8,12);
		this.Long=i*j;
		floor=new int[Long][2];
		wall=new int[Long][2];
		how1=0;
		how2=0;
		jpanel1=new JPanel();
		jpanel2=new JPanel();
		jpanel2.setLayout(new GridLayout(6,1));
		jpanel1.setLayout(grid);
		b=new JButton[i][j];
		for(int k=0;k<i;k++)
			for(int p=0;p<j;p++)
			{
				b[k][p]=new JButton();
				jpanel1.add(b[k][p]);
				b[k][p].addActionListener(this);
			}
		JSplitPane split2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jpanel1,jpanel2);
		split2.setDividerLocation(500);
		this.add(split2);
		JPanel jp=new JPanel();
		JPanel jp3=new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(new JLabel("��ѡ��������ɫ��"));
		jpanel2.add(jp3);
		b1=new JButton("ѡ����ɫ");
		jp.add(b1);
		b1.addActionListener(this);
		jpanel2.add(jp);
		JPanel jp2=new JPanel();
		jp2.setLayout(new GridLayout(1,3));
		jp2.add(new JLabel(""));
		b5=new JButton();
		b5.setBackground(Color.white); 
		b5.setEnabled(false);
		jp2.add(b5);
		jp2.add(new JLabel(""));
		jpanel2.add(jp2);
		JPanel jp4=new JPanel();
		jp4.setLayout(new FlowLayout());
		jp4.add(new JLabel("��ѡ��ǽ�����ɫ��ͼƬ:"));
		jpanel2.add(jp4);
		JPanel jp5=new JPanel();
		b3=new JButton("ѡ����ɫ");
		jp5.add(b3);
		b3.addActionListener(this);
		b4=new JButton("ѡ��ͼƬ");
		b4.addActionListener(this);
		jp5.add(b4);
		jpanel2.add(jp5);
		JPanel jp6=new JPanel();
		jp6.setLayout(new GridLayout(1,3));
		jp6.add(new JLabel(""));
		b6=new JButton();
		b6.setBackground(Color.white);
		b6.setEnabled(false);
		jp6.add(b6);
		jp6.add(new JLabel(""));
		jpanel2.add(jp6);
		String str[]={"���Ƶ���","������","����յ�"};
		JPanel panel=new JPanel();
		jpanel3=new JPanel();
		jpanel3.setLayout(new GridLayout(2,1));
		jpanel3.add(panel);
		this.add(jpanel3,"South");
		ButtonGroup bgroup=new ButtonGroup();
		this.radiobs=new JRadioButton[str.length];
		for(int m=0;m<this.radiobs.length;m++)
		{
			panel.add(this.radiobs[m]=new JRadioButton(str[m]));
			bgroup.add(this.radiobs[m]);
			this.radiobs[m].addActionListener(this);
		}
		this.radiobs[0].setSelected(true);
		JPanel jp7=new JPanel();
		b7=new JButton("ȷ��");
		jp7.add(b7);
		b7.addActionListener(this);
		b8=new JButton("����");
		jp7.add(b8);
		b8.addActionListener(this);
		b9=new JButton("ȡ��");
		jp7.add(b9);
		b9.addActionListener(this);
		jpanel3.add(jp7);
		this.setVisible(true);
		this.file=new File("");
		
	}
	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getSource()==b1)
		{
			f=JColorChooser.showDialog(this,"ѡ����ɫ",Color.BLUE);
			if(f!=null)
			{
				b5.setBackground(f);	
				for(int x=0;x<how1;x++)
				{
					b[floor[x][0]][floor[x][1]].setBackground(f);
				}
			}
		}
		if(ev.getSource()==b3)
		{
			w=JColorChooser.showDialog(this,"ѡ����ɫ",Color.BLUE);
			if(w!=null)
			{
				b6.setBackground(w);
				for(int x=0;x<b.length;x++)
					for(int y=0;y<b[0].length;y++)
					{
						int r=0;
						for(int k=0;k<how1;k++)
						{
							if(x==floor[k][0]&&y==floor[k][1])
								r=1;
						}
						if(r==0)
							b[x][y].setBackground(w);
						if(r==1)
							;
					}
			}
		}
		if(ev.getSource()==b4)
		{
			this.fchooser=new JFileChooser(new File("D:\\ͼƬ",""));
			this.fchooser.setFileFilter(new ExtensionFileFilter("ͼƬ(*.png)","png"));
			int i=this.fchooser.showOpenDialog(this);
			if(i==0)
			{
				this.file=fchooser.getSelectedFile();
				ImageIcon img=new ImageIcon(this.file.getPath());
				b6.setIcon(img);
				for(int x=0;x<b.length;x++)
					for(int y=0;y<b[0].length;y++)
					{
						int r=0;
						for(int k=0;k<how1;k++)
						{
							if(x==floor[k][0]&&y==floor[k][1])
								r=1;
						}
						if(r==0)
							b[x][y].setIcon(img);;
						if(r==1)
							;
					}
			}
		}
		if(ev.getSource()==b7)
		{
			if(b5.getIcon()!=null)
				mem=new member(floor,wall,b5.getIcon(),b6.getIcon(),sx,sy,ex,ey);
			else
				mem=new member(floor,wall,f,w,sx,sy,ex,ey);
			LabyrinthFrame.isSure=true;
			this.dispose();
		}
		if(ev.getSource()==b8)
		{
			for(int i=0;i<b.length;i++)
				for(int j=0;j<b[0].length;j++)
				{
					b[i][j].setBackground(null);
					floor=new int[Long][2];
					wall=new int[Long][2];
				}
		}
		if(ev.getSource()==b9)
		{
			this.dispose();
		}
		for(int i=0;i<b.length;i++)
			for(int j=0;j<b[0].length;j++)
			{
				if(ev.getSource()==b[i][j])
				{
					if(this.radiobs[0].isSelected())
					{
						floor[how1][0]=i;
						floor[how1][1]=j;
						how1++;
						if(b5.getIcon()!=null)
							b[i][j].setIcon(b5.getIcon());
						else
							b[i][j].setBackground(b5.getBackground());
					}
					if(this.radiobs[1].isSelected())
					{
						b[i][j].setText("��");
						sx=i;sy=j;
					}
					if(this.radiobs[2].isSelected())
					{
						b[i][j].setText("��");
						ex=i;ey=j;
					}
				}
			}
	}
}