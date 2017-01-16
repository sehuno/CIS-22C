import java.util.*;

public class Music implements Comparable<Music>
{
	private String title = "";
	private String composer = "";
	private int opus;
	
	public Music(){}
	
	public Music(String ti, String co, int op)
	{
		title = ti;
		composer = co;
		opus = op;
	}
	
	public String getTitle(){ return title; }
	
	public String getComposer(){ return composer; }
	
	public int getOpus(){ return opus; }
	
	public void setTitle(String newTitle){ title = newTitle; }
	
	public void setComposer(String newComposer){ composer = newComposer; }
	
	public void setOpus(int newOpus){ opus = newOpus; }
	
	public boolean equals(Object obj)
	{
		Music other = (Music)obj;
		return title.equals(other.title) && composer.equals(other.composer);
		//return title.compareTo(other.title)==0 && composer.compareTo(other.composer)==0;
	}

	public int compareTo(Music song)
	{
		if(title.compareTo(song.getTitle() < 0)
			return -10;
		else if(title.compareTo(song.getTitle()) == 0) 
			return 0;
		else
			return 10;
	}
}
