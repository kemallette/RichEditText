package com.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;


public class BulletListSpan extends BulletSpan implements
    IParagraphSpan{
	
	
	private boolean isFirstBullet = false;
	private int startPosition, endPosition, color;
	private float density;
	

	public BulletListSpan(final int startPostion,
	    final int endPostion, float density, int color,
	    int gapWidth) {

		super(gapWidth, color);
		
		startPosition = startPostion;
		endPosition = endPostion;
		this.density = density;
		
	}
	

	@Override
	public void setSpan(final Editable e) {

		if (startPosition < 0)
			startPosition = 0;
		
		if (!(e.length() < startPosition))
			if (startPosition > endPosition)
				Log.e("SPAN",
				      "StartPosition was after End Position - couldn't set.");
			else
				e.setSpan(this, startPosition, endPosition,
				          Spanned.SPAN_PARAGRAPH);
		else
			Log.e("SPAN",
			      "DID NOT SET: Start position past EditText length.");
		
	}
	

	@Override
	public int getStartPosition() {

		return startPosition;
	}
	

	@Override
	public int getEndPosition() {

		return endPosition;
	}
	

	@Override
	public void setStartPosition(final int startPos) {

		startPosition = startPos;
		
	}
	

	@Override
	public void setEndPosition(final int endPos) {

		endPosition = endPos;
	}
	

	@Override
	public void setFlag(final int flag) {

		// Do nothing - must be paragraph span
		
	}
	

	@Override
	public int getFlag() {

		return Spanned.SPAN_PARAGRAPH;
	}
	

	@Override
	public void setType(final int type) {

	}
	

	@Override
	public int getType() {

		return SpanTypes.BULLET;
	}
	

	@Override
	public void removeSpan(Editable text) {

		text.removeSpan(this);
	}
	

	@Override
	public int getFlagSynonym(int bufferEnd) {

		if (startPosition == bufferEnd
		    && endPosition == bufferEnd)
			return Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
		else if (startPosition < endPosition
		    && endPosition == bufferEnd)
			return Spanned.SPAN_INCLUSIVE_INCLUSIVE;
		else
			return Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
		

	}
	

	@Override
	public boolean isStartInclusive() {

		return false;
	}
	

	@Override
	public boolean isEndInclusive() {

		return false;
	}
	

	@Override
	public boolean isStartInclusive(int synonymFlag) {

		if (synonymFlag == Spanned.SPAN_INCLUSIVE_EXCLUSIVE
		        || synonymFlag == Spanned.SPAN_INCLUSIVE_INCLUSIVE)
			return true;
		else
			return false;
	}
	

	@Override
	public boolean isEndInclusive(int synonymFlag) {

		if (synonymFlag == Spanned.SPAN_EXCLUSIVE_INCLUSIVE
		        || synonymFlag == Spanned.SPAN_INCLUSIVE_INCLUSIVE)
			return true;
		else
			return false;
	}
	

	@Override
	public void dump() {

		Span.dump(this);
	}
	

	// @Override
	// public void drawLeadingMargin(Canvas c, Paint p, int x,
	// int dir, int top, int baseline, int bottom,
	// CharSequence text, int start, int end, boolean first,
	// Layout l) {
	//
	// TODO: Implement different bullet shapes and whatnot
	// This is the source code: so c.drawBlah etc etc..
	//
	// if (((Spanned) text).getSpanStart(this) == start) {
	// 82 Paint.Style style = p.getStyle();
	// 83 int oldcolor = 0;
	// 84
	// 85 if (mWantColor) {
	// 86 oldcolor = p.getColor();
	// 87 p.setColor(mColor);
	// 88 }
	// 89
	// 90 p.setStyle(Paint.Style.FILL);
	// 91
	// 92 c.drawCircle(x + dir * BULLET_RADIUS, (top + bottom) / 2.0f,
	// 93 BULLET_RADIUS, p);
	// 94
	// 95 if (mWantColor) {
	// 96 p.setColor(oldcolor);
	// 97 }
	// 98
	// 99 p.setStyle(style);
	//
	//
	// 100 }
	//
	// super.drawLeadingMargin(c, p, x - 10, dir, top,
	// baseline, bottom, text, start,
	// end, first, l);
	// }
	
}
