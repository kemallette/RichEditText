/**
 * 
 */
package com.kemallette.RichEditText.Text;


import android.text.Spanned;
import android.util.Log;

public abstract class BaseSpan	implements
								ISpan{


	int	type,
		flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


	public BaseSpan(final int type,
					final int flag){

		this.type = type;
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


	/***********************************************************
	 * 
	 * DEBUG
	 * 
	 ************************************************************/
	public static void dump(final ISpan mSpan){

		String type = null, flag = null;

		Log.i(	"EDITOR",
				"\n\n----------------------------\n");
		// Log.i( "EDITOR",
		// "Start: "
		// + mSpan.getStartPosition());
		// Log.i( "EDITOR",
		// "End: "
		// + mSpan.getEndPosition());
		switch(mSpan.getType()){
			case BOLD:
				type = "Bold";
				break;
			case ITALIC:
				type = "Italic";
				break;
			case BOLD_ITALIC:
				type = "Bold_Italic";
				break;
			case NORMAL:
				type = "Normal";
				break;
			case STRIKE:
				type = "Strike";
				break;
			case UNDERLINE:
				type = "Underline";
				break;
		}

		switch(mSpan.getFlag()){
			case Spanned.SPAN_EXCLUSIVE_EXCLUSIVE:
				flag = "EXCLUSIVE_EXCLUSIVE";
				break;
			case Spanned.SPAN_EXCLUSIVE_INCLUSIVE:
				flag = "EXCLUSIVE_INCLUSIVE";
				break;
			case Spanned.SPAN_INCLUSIVE_EXCLUSIVE:
				flag = "INCLUSIVE_EXCLUSIVE";
				break;
			case Spanned.SPAN_INCLUSIVE_INCLUSIVE:
				flag = "INCLUSIVE_INCLUSIVE";
				break;
		}
		Log.i(	"EDITOR",
				"Flag: "
					+ flag);
		Log.i(	"EDITOR",
				"Type: "
					+ type);
	}
}
