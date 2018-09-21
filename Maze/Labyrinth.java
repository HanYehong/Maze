import java.net.*;
import java.io.*;
public class Labyrinth {
	public Labyrinth(int port) throws IOException
	{
		ServerSocket server=new ServerSocket(port);
		Socket client=server.accept();
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
		new LabyrinthFrame(data,data[1][0],data[4][11],client);
		server.close();
	}

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		try {
			new Labyrinth(2001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
