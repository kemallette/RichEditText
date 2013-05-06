package com.kemallette.RichEditText.Text;


import org.apache.commons.lang3.ArrayUtils;

import android.text.Editable;
import android.text.Spanned;
import android.util.Log;


public class SpanUtil	implements
						SpanTypes{


	public static boolean isParagraphSpanType(int type){

		switch(type){
			case BULLET:
				return true;
			case OL:
				return true;
			case LEADING_MARGIN:
				return true;
		}

		return false;
	}


	public static ISpan[] isSpansCoveringRange(ISpan[] mSpans,
												int type, int start, int end){

		ISpan[] mCoveringSpans = null;

		if (mSpans != null)
			for (ISpan mSpan : mSpans)
				if (mSpan.getType() == type
					&& mSpan.getStartPosition() <= start
					&& mSpan.getEndPosition() >= end)
					mCoveringSpans =
										ArrayUtils.add(	mCoveringSpans,
														mSpan);

		return mCoveringSpans;
	}


	public static void setSpanStartExclusive(ISpan mSpan){

		if (mSpan.getFlag() == Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
			mSpan.setFlag(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		else
			mSpan.setFlag(Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

	}


	public static void setSpanEndExclusive(ISpan mSpan){

		if (mSpan.getFlag() == Spanned.SPAN_INCLUSIVE_INCLUSIVE)
			mSpan.setFlag(Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		else
			mSpan.setFlag(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}


	public static void setSpanStartInclusive(ISpan mSpan){

		if (mSpan.getFlag() == Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
			mSpan.setFlag(Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		else
			mSpan.setFlag(Spanned.SPAN_INCLUSIVE_INCLUSIVE);
	}


	public static void setSpanEndInclusive(ISpan mSpan){

		if (mSpan.getFlag() == Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
			mSpan.setFlag(Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		else
			mSpan.setFlag(Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
	}


	public static boolean isCombinable(ISpan span1, ISpan span2){

		if (span1.getFlag() == Spanned.SPAN_PARAGRAPH
			|| span2.getFlag() == Spanned.SPAN_PARAGRAPH)
			return false;

		if (span1.getStartPosition() <= span2.getStartPosition()
			&& span1.getEndPosition() > span2.getStartPosition())
			return true;

		if (span2.getStartPosition() <= span1.getStartPosition()
			&& span2.getEndPosition() > span1.getStartPosition())
			return true;

		if (span1.getStartPosition() == span2.getEndPosition()
			&& (span1.isStartInclusive() || span2.isEndInclusive()))
			return true;

		if (span2.getStartPosition() == span1.getEndPosition()
			&& (span1.isEndInclusive() || span2.isStartInclusive()))
			return true;

		return false;
	}


	public static ISpan[] isCombinable(ISpan[] mSpans){

		if (mSpans.length < 2)
			return null;
		else{
			for (ISpan mSpan : mSpans)
				for (ISpan span : mSpans)
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


	public static boolean isStartInclusive(ISpan mSpan){

		return false;
	}


	public static boolean isEndInclusive(ISpan mSpan){

		return false;
	}


	public static boolean isStartInclusive(ISpan mSpan, int bufferEnd){

		return false;
	}


	public static boolean isEndInclusive(ISpan mSpan, int bufferEnd){

		return false;
	}


	public static void reApplySpan(final ISpan mSpan,
									Editable editor){

		if (mSpan != null){
			editor.removeSpan(mSpan);
			mSpan.setSpan(editor);
		}
	}


	public static void printSpans(ISpan[] mSpans){


		if (mSpans != null
			&& mSpans.length > 0){

			Log.i(	"EDITOR",
					"\n\n----------------------------\n"
						+ "PRINTING SPANS\n");

			for (ISpan mSpan : mSpans){
				String type = "";
				String action = "";
				String flag = "";

				Log.i(	"EDITOR",
						"Start: "
							+ mSpan.getStartPosition());
				Log.i(	"EDITOR",
						"End: "
							+ mSpan.getEndPosition());
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
