import java.util.*;
import java.util.ArrayList;

public class BoardMessage implements Comparable<BoardMessage>
{
	// Private
	private int sequenceNum;
	private ArrayList<String> messageLines;

	public BoardMessage() 
	{
		messageLines = new ArrayList<>();
	}
	public BoardMessage(int seq) 
	{
		this();
		setSequenceNum(seq);
	}
	
	public void setSequenceNum(int seq) 
	{
		sequenceNum = seq;
	}

	public void setOneLine(String line) 
	{
		messageLines.add(line);
	}

	public int getSequenceNum() 
	{
		return sequenceNum;
	}

	public int getNumLines() 
	{
		return messageLines.size();
	}

	// @Override
	public int compareTo(BoardMessage bm) 
	{
		// NEEDS TO BE IMPLEMENTED
		int bmNum = bm.getSequenceNum();
		if(sequenceNum == bmNum)
			return 0;
		else if(sequenceNum < bmNum)
			return -1;
		else 
			return 1;
	}

	public String toString() 
	{
		// NEEDS TO BE IMPLEMENTED
		String ret = "#" + String.valueOf(sequenceNum) + "\n"; 
		for(String s : messageLines)
			ret = ret + s + "\n";
		return ret;
	}
}
