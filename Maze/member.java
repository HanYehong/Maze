import java.awt.Color;

import javax.swing.Icon;

public class member
{
	int floor[][];
	int wall[][];
	Color f;
	Color w;
	int sx,sy,ex,ey;
	Icon img1,img2;
	public member(int floor[][],int wall[][],Color f,Color w,int sx,int sy,int ex,int ey)
	{
		this.floor=floor;
		this.wall=wall;
		this.f=f;
		this.w=w;
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
	}
	public member(int floor[][],int wall[][],Icon img1,Icon img2,int sx,int sy,int ex,int ey)
	{
		this.floor=floor;
		this.wall=wall;
		this.img1=img1;
		this.img2=img2;
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
	}
}