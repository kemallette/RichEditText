package com.kemallette.RichEditText.Text;


import android.text.Editable;


public interface ISpan	extends
						SpanTypes{

	public static final int	EXCLUSIVE	= 0;
	public static final int	INCLUSIVE	= 1;


	public void setSpan(final Editable e);


	public int getStartPosition();


	public int getEndPosition();


	public void setStartPosition(int startPos);


	public void setEndPosition(int endPos);


	public void setFlag(int flag);


	public int getFlag();


	public boolean isStartInclusive();


	public boolean isEndInclusive();


	public void setType(int type);


	public int getType();


	public void dump();


	public void removeSpan(Editable text);

}
