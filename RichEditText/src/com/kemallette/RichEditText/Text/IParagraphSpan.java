package com.kemallette.RichEditText.Text;


public interface IParagraphSpan extends ISpan{
	
	public int getFlagSynonym(int bufferEnd);
	

	public boolean isStartInclusive(int synonymFlag);
	

	public boolean isEndInclusive(int synonymFlag);
}
