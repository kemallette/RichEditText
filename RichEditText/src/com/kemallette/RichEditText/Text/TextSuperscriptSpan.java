package com.kemallette.RichEditText.Text;


import android.text.style.SuperscriptSpan;


public class TextSuperscriptSpan extends
								SuperscriptSpan
												implements
												ISpan{


	int	startPosition, endPosition, flag;


	public TextSuperscriptSpan(	final int startPostion,
								final int endPostion,
								final int flag){

		super();

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

		// Do nothing, we're an underline type
	}


	@Override
	public int getType(){

		return SUPERSCRIPT;
	}

}
