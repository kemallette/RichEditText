package com.kemallette.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;


public interface ISpan	extends
						SpanTypes{

	/*************************************************************
	 * Here's a convenient binary conversion cheat sheet
	 * for {@link Spanned} and ISpan helpers.
	 * 
	 * 
	 * <pre>
	 * ___________________________________________________________
	 * 
	 * 		Name / Synonym				  Decimal	  	  Binary
	 * ___________________________________________________________
	 * 
	 * 						~~FROM SPANNED~~
	 * 
	 * EXCLUSIVE_EXCLUSIVE / POINT_MARK		33			0011 0001
	 * EXCLUSIVE_INCLUSIVE / POINT_POINT	34			0010 0010
	 * INCLUSIVE_EXCLUSIVE / MARK_MARK		17			0001 0001
	 * INCLUSIVE_INCLUSIVE / MARK_POINT		18			0001 0010
	 * 
	 * PARAGRAPH / POINT_MARK_MASK			51			0011 0011
	 * 
	 * COMPOSING							256	   0001 0000 0000
	 * INTERMEDIATE							512	   0010 0000 0000
	 * 
	 * 
	 * 						~~HELPERS~~
	 * 
	 * MARK									1				 0001
	 * POINT								2				 0010
	 * PARAGRAPH							3				 0011
	 * 
	 * START_SHIFT							4				 0100
	 * 
	 * START_MASK							240			1111 0000
	 * END_MASK								15				 1111
	 * 
	 * </pre>
	 * 
	 * TODO: Example docs - would make a great blog post, too
	 * 
	 * START_SHIFT - Use with '>>' (right shift) to move the four bits
	 * describing the span start all the way to the right enabling comparison to
	 * helpers
	 * 
	 * <pre>
	 * Example:
	 *  int flagsStart = (Spanned.SPAN_EXCLUSIVE_EXCLUSIVE & START_MASK) >> START_SHIFT;
	 *         if (flagsStart == PARAGRAPH) {}
	 * 
	 * EXCLUSIVE_EXCLUSIVE & start
	 * 
	 * END_MASK - Use '&' to compare bits describing the span end with helpers:
	 * MARK, POINT, or PARAGRAPH
	 * 
	 *************************************************************/


	public static final int	MARK		= 1;	// .... 0001
	public static final int	POINT		= 2;	// .... 0010
	public static final int	PARAGRAPH	= 3;	// .... 0011
	public static final int	START_SHIFT	= 4;	// .... 0100

	public static final int	START_MASK	= 0xF0; // 1111 0000
	public static final int	END_MASK	= 0x0F; // .... 1111


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
