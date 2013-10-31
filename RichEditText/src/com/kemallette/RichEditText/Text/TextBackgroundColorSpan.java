package com.kemallette.RichEditText.Text;


import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;


/**
 * Color is set by constructor and is immutable. Have to create a new span and
 * apply it to change the bgcolor
 */
public class TextBackgroundColorSpan extends
									BackgroundColorSpan
														implements
														ISpan{

	private final TextPaint	mTextPaint;
	private final int		startPosition, endPosition;
	private int				flag;


	public TextBackgroundColorSpan(	final int startPostion,
									final int endPostion,
									final int flag,
									final int color){

		super(color); // Background color span's constructor sets the color

		startPosition = startPostion;
		endPosition = endPostion;
		this.flag = flag;

		mTextPaint = new TextPaint();
	}


	public int getColor(){

		return super.getBackgroundColor();
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

		return BACKGROUND_COLOR;
	}

}
