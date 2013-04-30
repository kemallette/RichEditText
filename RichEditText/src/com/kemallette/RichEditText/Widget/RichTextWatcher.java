/**
 * 
 */
package com.kemallette.RichEditText.Widget;


import android.text.SpanWatcher;


public interface RichTextWatcher extends
								SpanWatcher{


	/**
	 * Convenience for {@link #beforeReplace(int, int, CharSequence, int, int)}
	 * if no other convenience method is called.
	 * 
	 * @param start
	 *            - starting offset of the text to be replaced.
	 * @param end
	 *            - ending offset of the text to be replaced.
	 * @param repText
	 *            - the text that will replace the current text
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            that will end up replacing the current text
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text
	 *            that will end up replacing the current text
	 */
	public void onBeforeCompose(int start, int end,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #afterReplace(int, int, CharSequence, int, int)}
	 * if no other convenience method is called.
	 * 
	 * 
	 * @param start
	 *            - starting offset of the text replaced.
	 * @param end
	 *            - ending offset of the text replaced.
	 * @param repText
	 *            - the replacement text
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            used
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text
	 *            used
	 */
	public void onAfterCompose(int start, int end,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #beforeReplace(int, int, CharSequence, int, int)}
	 * if start and end are both equal, but not equal to end.
	 * 
	 * @param position
	 *            - where the repText will be inserted
	 * @param repText
	 *            - the replacement text to be used
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            to be used
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text to
	 *            be used
	 */
	public void onBeforeInsert(int position,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #afterReplace(int, int, CharSequence, int, int)}
	 * if start and end are both equal, but not equal to end (that would be an
	 * append).
	 * 
	 * @param position
	 *            - where the repText was inserted
	 * @param repText
	 *            - the replacement text
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            used
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text
	 *            used
	 */
	public void onAfterInsert(int position,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #beforeReplace(int, int, CharSequence, int, int)}
	 * if start and end are both equal and equal to end.
	 * 
	 * @param position
	 *            - offset where repText will be appended.
	 * @param repText
	 *            - the replacement text to be used
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            to be used
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text to
	 *            be used
	 */
	public void onBeforeAppend(int position,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #afterReplace(int, int, CharSequence, int, int)}
	 * if start and end are both equal and equal to end.
	 * 
	 * @param position
	 *            - where the repText was appended
	 * @param repText
	 *            - the replacement text
	 * @param repStart
	 *            - the starting offset of the subsequence of replacement text
	 *            used
	 * @param repEnd
	 *            - the ending offset of the subsequence of replacement text
	 *            used
	 */
	public void onAfterAppend(int position,
								CharSequence repText, int repStart, int repEnd);


	/**
	 * Convenience for {@link #beforeReplace(int, int, CharSequence, int, int)}
	 * if start and end are both 0 and repText is empty.
	 * 
	 * @param start
	 *            - the starting offset of text to be deleted
	 * @param end
	 *            - the ending offset of text to be deleted
	 */
	public void onBeforeDelete(int start, int end);


	/**
	 * Convenience for {@link #afterReplace(int, int, CharSequence, int, int)}
	 * if start and end are both 0 and repText is empty.
	 * 
	 * @param start
	 *            - the starting offset of text deleted
	 * @param end
	 *            - the ending offset of text deleted
	 */
	public void onAfterDelete(int start, int end);


	public void onTextActionCursorMove(int position);


	/**
	 * Called before a replacement. The text between start/end offsets will be
	 * replaced with a subsequence of repText from offset repStart to offset
	 * repEnd.
	 * 
	 * @param start
	 *            - starting offset of text to be replaced
	 * @param end
	 *            - ending offset of text to be replaced
	 * @param repText
	 *            - replacement text to be used
	 * @param repStart
	 *            - starting offset of replacement text to be used
	 * @param repEnd
	 *            - ending offset of replacement text to be used
	 */
	public void
		beforeReplace(final int start, final int end,
						final CharSequence repText, final int repStart,
						final int repEnd);


	/**
	 * Called after a replacement. The text between start/end offsets was
	 * replaced with a subsequence of repText from offset repStart to offset
	 * repEnd.
	 * 
	 * @param start
	 *            - starting offset of text that was replaced
	 * @param end
	 *            - ending offset of text that was replaced
	 * @param repText
	 *            - the replacement text used
	 * @param repStart
	 *            - starting offset of replacement text that was used
	 * @param repEnd
	 *            - ending offset of replacement text that was used
	 */
	public void
		afterReplace(final int start, final int end,
						final CharSequence repText, final int repStart,
						final int repEnd);

}
