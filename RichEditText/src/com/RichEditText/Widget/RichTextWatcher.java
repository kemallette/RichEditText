/**
 * 
 */
package com.RichEditText.Widget;


import android.text.SpanWatcher;


/**
 * @author Kyle Mallette
 * 
 *         Created: Nov 8, 2011
 * 
 */
public interface RichTextWatcher extends SpanWatcher{
	
	
	public void onBeforeCompose(int start, int end,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onAfterCompose(int start, int end,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onBeforeInsert(int position,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onAfterInsert(int position,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onBeforeAppend(int endPosition,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onAfterAppend(int endPosition,
	    CharSequence repText, int repStart, int repEnd);
	

	public void onBeforeDelete(int start, int end);
	

	public void onAfterDelete(int start, int end);
	

	public void onTextActionCursorMove(int position);
	

	public void
	    beforeReplace(final int start, final int end,
	        final CharSequence tb, final int tbstart,
	        final int tbend);
	

	public void
	    afterReplace(final int start, final int end,
	        final CharSequence tb, final int tbstart,
	        final int tbend);
	
}
