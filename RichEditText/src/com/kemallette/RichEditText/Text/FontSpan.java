package com.kemallette.RichEditText.Text;


import android.text.style.TypefaceSpan;


public class FontSpan	extends
						TypefaceSpan implements
									ISpan{


	private int	flag;


	public FontSpan(final int flag,
					final String fontFamily){

		super(fontFamily); // Fontspans constructor sets the font family

		this.flag = flag;
	}


	@Override
	public String getFamily(){

		return super.getFamily();
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
