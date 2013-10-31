package com.kemallette.RichEditText.Text;


import android.text.style.StyleSpan;


public class TextStyleSpan	extends
							StyleSpan	implements
										ISpan{


	private final int	startPosition, endPosition;
	private int			type;
	private int			flag;


	public TextStyleSpan(	final int type,
							final int startPostion,
							final int endPostion,
							final int flag){

		super(type);
		this.type = type;
		startPosition = startPostion;
		endPosition = endPostion;
		this.flag = flag;
	}


	@Override
	public void setFlag(final int flag){

		this.flag = flag;
	}


	@Override
	public int getFlag(){

		return flag;
	}


	@Override
	public void setType(final int type){

		this.type = type;
	}


	@Override
	public int getType(){

		return type;
	}


}
