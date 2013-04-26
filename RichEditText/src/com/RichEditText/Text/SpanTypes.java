package com.RichEditText.Text;


public interface SpanTypes{
	
	
	/***********************************************************
	 * 
	 * Non-Paragraph Spans
	 * 
	 ************************************************************/
	public static final int NORMAL =
	    android.graphics.Typeface.NORMAL;
	public static final int BOLD =
	    android.graphics.Typeface.BOLD;
	public static final int ITALIC =
	    android.graphics.Typeface.ITALIC;
	public static final int BOLD_ITALIC =
	    android.graphics.Typeface.BOLD_ITALIC;
	
	public static final int UNDERLINE = 10;
	public static final int STRIKE = 20;
	public static final int FOREGROUND_COLOR = 30;
	public static final int BACKGROUND_COLOR = 40;
	public static final int SUBSCRIPT = 50;
	public static final int SUPERSCRIPT = 60;
	public static final int IMAGE = 70;
	public static final int FONT = 80;
	
	/***********************************************************
	 * 
	 * Paragraph Spans
	 * 
	 ************************************************************/
	public static final int OL = 530;
	public static final int BULLET = 540;
	
	public static final int ALIGN_RIGHT = 550;
	public static final int ALIGN_CENTER = 560;
	public static final int ALIGN_LEFT = 570;
	
	public static final int ACTION_START_INSERT = 600;
	public static final int ACTION_COMPOSE = 610;
	public static final int ACTION_DELETE = 620;
	public static final int ACTION_APPEND = 6130;
	
	public static final int LEADING_MARGIN = 700;
	public static final int LEADING_MARGIN_UL = 710;
}
