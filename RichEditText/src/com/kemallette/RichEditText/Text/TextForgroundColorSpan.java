package com.kemallette.RichEditText.Text;


import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;


/**
 * Color is set by constructor and is immutable. Have to create a new span and
 * apply it to change the fgcolor
 */
public class TextForgroundColorSpan	extends
									ForegroundColorSpan
														implements
														ISpan{

	private final int		startPosition, endPosition;
	private int				flag;
	private final TextPaint	mTextPaint;


	public TextForgroundColorSpan(	final int startPostion,
									final int endPostion,
									final int flag,
									final int color){

		super(color); // Foreground color span's constructor sets the color

		startPosition = startPostion;
		endPosition = endPostion;
		this.flag = flag;

		mTextPaint = new TextPaint();
	}


	public int getColor(){

		return super.getForegroundColor();
	}


	public void setColor(final int color){

		mTextPaint.bgColor = color;
		updateDrawState(mTextPaint);
		super.updateDrawState(mTextPaint);
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

		// do nothing
	}


	@Override
	public int getType(){

		return FOREGROUND_COLOR;
	}


}
