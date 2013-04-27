/**
 * 
 */
package com.kemallette.RichEditText.Text;


import android.text.SpannableStringBuilder;
import android.util.Log;

import com.kemallette.RichEditText.Widget.RichTextWatcher;


public class RichTextStringBuilder extends
    SpannableStringBuilder{
	
	private static final int COMPOSING = 1;
	private static final int INSERTING = 2;
	private static final int APPENDING = 3;
	private static final int DELETING = 4;
	

	private RichTextWatcher mWatcher;
	

	public RichTextStringBuilder(final CharSequence source,
	    final RichTextWatcher mWatcher) {

		super(source);
		
		this.mWatcher = mWatcher;
	}
	

	/************************************************************
	 * 
	 * All operations are actually conviences for replacements of some type
	 * 
	 * note that with replace, if a span is completely engulfed by the
	 * replacement, it will be removed - we need to add it back as long as
	 * there's still text there
	 * 
	 *************************************************************/
	@Override
	public SpannableStringBuilder replace(final int start,
	    final int end, final CharSequence repText,
	    final int repStart, final int repEnd) {

		int action;
		

		mWatcher.beforeReplace(start, end, repText, repStart,
		                       repEnd);
		mWatcher.onTextActionCursorMove(start);
		
		if (start == end && start != length()) {
			Log.i("EDITOR",
			      "\n**********************************\n"
			          + "Replacement is inserting"
			          + "\n**********************************\n");
			
			action = INSERTING;
			
			mWatcher.onBeforeInsert(end, repText, repStart,
			                        repEnd);
		} else if (repText.toString().equals("")
		    && repStart == 0 && repEnd == 0) {
			action = DELETING;
			
			Log.i("EDITOR",
			      "\n**********************************\n"
			          + "Replacement is Deleting"
			          + "\n**********************************\n");
			
			mWatcher.onBeforeDelete(start, end);
		} else if (length() != 0 && start == length()
		    && end == length()) {
			
			action = APPENDING;
			
			Log.i("EDITOR",
			      "\n**********************************\n"
			          + "Replacement is Appending"
			          + "\n**********************************\n");
			
			mWatcher.onBeforeAppend(end, repText, repStart,
			                        repEnd);
		} else {
			action = COMPOSING;
			
			Log.i("EDITOR",
			      "\n**********************************\n"
			          + "Replacement is Composing"
			          + "\n**********************************\n");
			

			mWatcher.onBeforeCompose(start, end, repText,
			                         repStart, repEnd);
		}
		
		SpannableStringBuilder mString =
		    super.replace(start, end, repText, repStart, repEnd); // This is where the action happens in super
		
		mWatcher.afterReplace(start, end, repText, repStart,
		                      repEnd);
		mWatcher.onTextActionCursorMove(end);
		
		switch(action){
			case COMPOSING:
				mWatcher.onAfterCompose(start, end, repText,
				                        repStart, repEnd);
				break;
			case APPENDING:
				mWatcher.onAfterAppend(end, repText, repStart,
				                       repEnd);
				break;
			case INSERTING:
				mWatcher.onAfterInsert(end, repText, repStart,
				                       repEnd);
				break;
			case DELETING:
				mWatcher.onAfterDelete(start, end);
				break;
			
		}
		

		return mString;
		
	}
	

	@Override
	public void removeSpan(Object what) {

		super.removeSpan(what);
		
		mWatcher.onSpanRemoved(this, what, getSpanStart(what),
		                       getSpanEnd(what));
	}
	

	@Override
	public void setSpan(Object what, int start, int end,
	    int flags) {

		
		super.setSpan(what, start, end, flags);
		
		mWatcher.onSpanAdded(this, what, start, end);
	}
}
