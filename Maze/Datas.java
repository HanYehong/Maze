public class Datas implements java.io.Serializable
{
	public int x,y;
	public boolean isWhite;
	public boolean isWalk;
	public boolean isfork;
	public boolean isExerting;
	
	public Datas(boolean isWhite,int x,int y)
	{
		this.isWhite=isWhite;
		this.x=x;
		this.y=y;
	}
}