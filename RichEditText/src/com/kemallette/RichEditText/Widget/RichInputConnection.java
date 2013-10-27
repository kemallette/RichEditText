package com.kemallette.RichEditText.Widget;


import android.util.Log;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;


public class RichInputConnection extends
								InputConnectionWrapper{


	private static final String		TAG		= "RichInputConnection";
	private static final boolean	DEBUG	= false;


	public RichInputConnection(	final InputConnection target,
								final boolean mutable){

		super(	target,
				mutable);

	}


	@Override
	public boolean setComposingText(final CharSequence text,
									final int newCursorPosition){

//		if (DEBUG)
			Log.v(	TAG,
					"setComposingText(CharSequence text, int newCursorPosition)\n"
						+
						"text: "
						+ text
						+ "\n"
						+
						"newCursorPosition: "
						+ newCursorPosition);

		return super.setComposingText(	text,
										newCursorPosition);
	}


	@Override
	public boolean setComposingRegion(final int start, final int end){

//		if (DEBUG)
			Log.d(	TAG,
					"setComposingRegion(int start, int end) \n"
						+
						"start: "
						+ start
						+ "\n"
						+
						"end: "
						+ end);
		return super.setComposingRegion(start,
										end);
	}


	@Override
	public boolean finishComposingText(){

//		if (DEBUG)
			Log.v(	TAG,
					"finishComposingText()");
		return super.finishComposingText();
	}


	@Override
	public boolean commitText(final CharSequence text,
								final int newCursorPosition){

//		if (DEBUG)
			Log.i(	TAG,
					"commitText(CharSequence text, int newCursorPosition)\n"
						+
						"text: "
						+ text
						+ "\n"
						+
						"newCursorPosition: "
						+ newCursorPosition);
		return super.commitText(text,
								newCursorPosition);
	}


	@Override
	public boolean commitCompletion(final CompletionInfo text){

//		if (DEBUG)
			Log.v(	TAG,
					"commitCompletion(CompletionInfo text)\n"
						+
						"text: "
						+ text);
		return super.commitCompletion(text);
	}


	@Override
	public boolean commitCorrection(final CorrectionInfo correctionInfo){

		if (DEBUG)
			Log.v(	TAG,
					"commitCompletion(CorrectionInfo correctionInfo)\n"
						+
						"correctionInfo: "
						+ correctionInfo);
		return super.commitCorrection(correctionInfo);
	}


	@Override
	public boolean beginBatchEdit(){

		if (DEBUG)
			Log.i(	TAG,
					"beginBatchEdit()\n");
		return super.beginBatchEdit();
	}


	@Override
	public boolean endBatchEdit(){

		if (DEBUG)
			Log.i(	TAG,
					"endBatchEdit()\n");
		return super.endBatchEdit();
	}


	@Override
	public CharSequence getTextBeforeCursor(final int n, final int flags){

		if (DEBUG)
			Log.d(	TAG,
					"getTextBeforeCursor(int n, int flags)\n"
						+
						"n: "
						+ n
						+ "\n"
						+ "flags"
						+ flags);
		return super.getTextBeforeCursor(	n,
											flags);
	}


	@Override
	public CharSequence getTextAfterCursor(final int n, final int flags){

		if (DEBUG)
			Log.d(	TAG,
					"getTextAfterCursor(int n, int flags)\n"
						+
						"n: "
						+ n
						+ "\n"
						+ "flags"
						+ flags);
		return super.getTextAfterCursor(n,
										flags);
	}


	@Override
	public CharSequence getSelectedText(final int flags){

		if (DEBUG)
			Log.v(	TAG,
					"getSelectedText(int flags)\n"
						+ "flags"
						+ flags);
		return super.getSelectedText(flags);
	}


	@Override
	public ExtractedText getExtractedText(final ExtractedTextRequest request,
											final int flags){

		if (DEBUG)
			Log.v(	TAG,
					"getExtractedText(ExtractedTextRequest request, int flags)\n"
						+ "request: "
						+ request
						+ "\n"
						+ "flags"
						+ flags);
		return super.getExtractedText(	request,
										flags);
	}


	@Override
	public boolean deleteSurroundingText(final int beforeLength,
											final int afterLength){

		if (DEBUG)
			Log.v(	TAG,
					"deleteSurroundingText(int beforeLength, int afterLength)\n"
						+
						"beforeLength: "
						+ beforeLength
						+ "\n"
						+ "afterLength"
						+ afterLength);
		return super.deleteSurroundingText(	beforeLength,
											afterLength);
	}


	@Override
	public boolean setSelection(final int start, final int end){

		if (DEBUG)
			Log.v(	TAG,
					"setSelection(int start, int end)\n"
						+
						"start: "
						+ start
						+ " \n"
						+ "end"
						+ end);
		return super.setSelection(	start,
									end);
	}

}
