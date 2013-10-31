package com.kemallette.RichEditText.Text;


import android.text.Spanned;
import android.text.style.BulletSpan;


public class BulletListSpan	extends
							BulletSpan	implements
										IParagraphSpan{


	private final boolean	isFirstBullet	= false;
	private int				color;
	private final float		density;


	public BulletListSpan(	final int color,
							final float density,
							final int gapWidth){

		super(	gapWidth,
				color);
		this.density = density;

	}


	@Override
	public void setFlag(final int flag){

		// Do nothing - must be paragraph span

	}


	@Override
	public int getFlag(){

		return Spanned.SPAN_PARAGRAPH;
	}


	@Override
	public void setType(final int type){

	}


	@Override
	public int getType(){

		return SpanTypes.BULLET;
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
