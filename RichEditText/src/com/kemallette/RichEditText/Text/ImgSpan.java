package com.kemallette.RichEditText.Text;


import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Image is set by constructor and is immutable. Have to create a new span and
 * apply it to change the fgcolor
 */

public class ImgSpan extends
					ImageSpan	implements
								ISpan{

	private int	flag;


	public ImgSpan(
					final int flag,
					final Drawable image){

		super(image); // Imgspan's constructor sets the image bitmap
		this.flag = flag;
	}


	@Override
	public Drawable getDrawable(){

		return super.getDrawable();
	}


	@Override
	public String getSource(){

		return super.getSource();
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

		return IMAGE;
	}


}
