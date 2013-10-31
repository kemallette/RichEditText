package com.kemallette.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;
import android.util.Log;

/*************************************************************
 * 
 * Here's a convenient binary conversion cheat sheet for {@link Spanned} and
 * ISpan helpers.
 * 
 * 
 * <pre>
 * __________________________________________________________________________
 * 
 * 		Name / Synonym				  					Decimal	  	  Binary
 * __________________________________________________________________________
 * 
 * 						~~FROM SPANNED~~
 * 
 * SPANNED_EXCLUSIVE_EXCLUSIVE / SPANNED_POINT_MARK		33			0011 0001
 * SPANNED_EXCLUSIVE_INCLUSIVE / SPANNED_POINT_POINT	34			0010 0010
 * SPANNED_INCLUSIVE_EXCLUSIVE / SPANNED_MARK_MARK		17			0001 0001
 * SPANNED_INCLUSIVE_INCLUSIVE / SPANNED_MARK_POINT		18			0001 0010
 * 
 * SPANNED_PARAGRAPH / SPANNED_POINT_MARK_MASK			51			0011 0011
 * 
 * SPANNED_COMPOSING									256	   0001 0000 0000
 * SPANNED_INTERMEDIATE									512	   0010 0000 0000
 * 
 * 
 * 						~~HELPERS~~
 * 
 * MARK													1				 0001
 * POINT												2				 0010
 * PARAGRAPH											3				 0011
 * 
 * START_SHIFT											4				 
 * 
 * START_MASK											240			1111 0000
 * END_MASK												15				 1111
 * 
 * 
 * Using bit manipulation on span flags to determine start and end behavior
 *  
 * ------------------------------------------------------------------- 
 * EX 1: Getting a span's start behavior 
 * -------------------------------------------------------------------
 *    
 *    final int startFlag = (spanFlag & START_MASK) >> START_SHIFT;
 * 
 *    
 *     1. Assign flag to result of a logical AND operation of 
 *        span's flag and START_MASK
 *    
 *     						    (start) (end)
 *     			span.getFlag():  SSSS    EEEE
 *     								   &
 *     			START_MASK:      1111    0000
 * 
 *     
 *     2. Right shift flag by 4 bits to leave just the four bits 
 *        describing the span's start behavior 
 *        
 *        		SSSS EEEE >> 4  -->  SSSS
 *           
 *    3. Compare with POINT (EXCLUSIVE), MARK (INCLUSIVE) 
 * 	  and PARAGRAPH (POINT_MARK_MASK) 
 * 
 * 			if (startflag == POINT) // start behavior is EXCLUSIVE 
 *                                  // and could also be PARAGRAPH
 * 
 * 		Caution! You have to use either the end bits or both the 
 * 			   start and end bits to distinguish between a span with 
 * 			   start behavior POINT or PARAGRAPH 
 * 
 * 
 * To get bits describing end behavior: 
 * 
 * 		final int endFlag = (spanFlag & END_MASK);
 * </pre>
 *************************************************************/
public class SpanUtil	implements
						SpanTypes{

	private static final String	TAG			= "SpanUtil";

	/*
	 * Span flag bit manipulation helpers
	 */
	public static final int		MARK		= 1;			// .... 0001
	public static final int		POINT		= 2;			// .... 0010
	public static final int		EXCLUSIVE	= POINT;
	public static final int		INCLUSIVE	= MARK;
	public static final int		PARAGRAPH	= 3;			// .... 0011

	public static final int		START_SHIFT	= 4;

	public static final int		START_MASK	= 0xF0;		// 1111 0000
	public static final int		END_MASK	= 0x0F;		// .... 1111


	public static void setSpan(final Object span,
								int startPosition,
								final int endPosition,
								final Editable e,
								final int flag){

		if (startPosition < 0)
			startPosition = 0;

		if (!(e.length() < startPosition))
			if (startPosition > endPosition)
				Log.e(	"SPAN",
						"StartPosition was after End Position - couldn't set.");
			else
				e.setSpan(	span,
							startPosition,
							endPosition,
							flag);
		else
			Log.e(	"SPAN",
					"DID NOT SET: Start position past EditText length.");

	}


	public static boolean isParagraphFlagged(final ISpan span){

		// SPAN_POINT_MARK_MASK = SPANNED_PARAGRAPH
		return (span.getFlag() & Spanned.SPAN_POINT_MARK_MASK) == Spanned.SPAN_PARAGRAPH;
	}


	public static boolean isStartInclusive(final ISpan span){

		if (isParagraphFlagged(span))
			return false;

		final int flag = (span.getFlag() & START_MASK) >> START_SHIFT;

		return flag == INCLUSIVE;
	}


	public static boolean isEndInclusive(final ISpan span){

		final int endFlag = (span.getFlag() & END_MASK);

		return endFlag == INCLUSIVE;
	}


	public static boolean isStartExclusive(final ISpan span){

		return !isStartInclusive(span);
	}


	public static boolean isEndExclusive(final ISpan span){

		return !isEndInclusive(span);
	}


	public static boolean isCombinable(final ISpan span1, final ISpan span2){

		return false;
	}


	public static ISpan[] isCombinable(final ISpan[] mSpans){

		if (mSpans.length < 2)
			return null;
		else{
			for (final ISpan mSpan : mSpans)
				for (final ISpan span : mSpans)
					if (mSpan.getType() == span.getType()
						&& !mSpan.equals(span)
						&& SpanUtil.isCombinable(	mSpan,
													span))
						return new ISpan[] {
											mSpan,
											span };

			return null;
		}
	}


	public static void printSpans(final ISpan[] mSpans){


		if (mSpans != null
			&& mSpans.length > 0){

			Log.i(	"EDITOR",
					"\n\n----------------------------\n"
						+ "PRINTING SPANS\n");

			for (final ISpan mSpan : mSpans){
				String type = "";
				final String action = "";
				String flag = "";

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
					case UNDERLINE:
						type = "Underline";
						break;
					case STRIKE:
						type = "Strike";
						break;
					case SUPERSCRIPT:
						type = "superscript";
						break;
					case SUBSCRIPT:
						type = "Subscript";
						break;
					case IMAGE:
						type = "Image";
						break;
					case BACKGROUND_COLOR:
						type = "Background Color";
						break;
					case FOREGROUND_COLOR:
						type = "Foreground Color";
						break;
					case FONT:
						type = "FontFamily";
						break;
					case BULLET:
						type = "BulletList";
						break;
					case OL:
						type = "OrderedList";
						break;
					case LEADING_MARGIN:
						type = "Leading Margin";
						break;
					case LEADING_MARGIN_UL:
						type = "UL Leading Margin";
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

					case Spanned.SPAN_PARAGRAPH:
						flag = "PARAGRAPH";
						break;
				}
				Log.i(	"EDITOR",
						"Type: "
							+ type);
				Log.i(	"EDITOR",
						"Action: "
							+ action);
				Log.i(	"EDITOR",
						"Flag: "
							+ flag);
			}
			Log.i(	"EDITOR",
					"----------------------------\n\n\n");
		}else
			Log.i(	"EDITOR",
					"\n\n----------------------------\n"
						+ "NO SPANS TO PRINT \n\n\n");
	}
}
