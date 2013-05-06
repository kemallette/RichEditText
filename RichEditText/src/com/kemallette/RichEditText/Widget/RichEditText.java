package com.kemallette.RichEditText.Widget;


import org.apache.commons.lang3.ArrayUtils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView.BufferType;
import android.widget.ToggleButton;

import com.kemallette.RichEditText.R;
import com.kemallette.RichEditText.HoloColorPicker.ColorPicker.OnColorChangedListener;
import com.kemallette.RichEditText.HoloColorPicker.ColorPickerDialog;
import com.kemallette.RichEditText.Text.BulletListSpan;
import com.kemallette.RichEditText.Text.FontSpan;
import com.kemallette.RichEditText.Text.IParagraphSpan;
import com.kemallette.RichEditText.Text.ISpan;
import com.kemallette.RichEditText.Text.ImgSpan;
import com.kemallette.RichEditText.Text.RichTextStringBuilder;
import com.kemallette.RichEditText.Text.SpanTypes;
import com.kemallette.RichEditText.Text.SpanUtil;
import com.kemallette.RichEditText.Text.StrikeSpan;
import com.kemallette.RichEditText.Text.TextBackgroundColorSpan;
import com.kemallette.RichEditText.Text.TextForgroundColorSpan;
import com.kemallette.RichEditText.Text.TextStyleSpan;
import com.kemallette.RichEditText.Text.TextSubscriptSpan;
import com.kemallette.RichEditText.Text.TextSuperscriptSpan;
import com.kemallette.RichEditText.Text.UnderliningSpan;
import com.kemallette.RichEditText.Validations.Validator;


public class RichEditText	extends
							RelativeLayout	implements
											OnClickListener,
											OnCheckedChangeListener,
											RichTextWatcher,
											TextWatcher,
											SelectionChangeListener,
											SpanTypes{

	// Replaces the default edit text factory that produces editables
	// which our custom editable and listener
	public class TextFactory extends
							Editable.Factory{


		@Override
		public Editable newEditable(final CharSequence source){

			return new RichTextStringBuilder(	source,
												mSpanWatcher);
		}

	}

	private static final String		TAG						= "RichEditText";

	private String					fontName				= null;

	private boolean					isUserSelectingRange	= false;
	private boolean					showFormattingOptions	= false;
	private boolean					showClearButton			= true;

	private int						bulletColor				= android.R.color.transparent;
	private int						fgColor					= android.R.color.transparent,
		bgColor = Color.LTGRAY;


	private int						selectionStart, selectionEnd,
									selectionLength, selectionPosition;
	/**
	 * This is also the end position of a selection
	 */
	private int						cursorPosition;
	private int						bulletMarginWidth		= 5;

	private float					density;

	private ISpan[]					appliedSpans, ajacentSpans;

	// Styling Buttons
	private Button					btnSuperScript, btnSubScript, btnFgPicker,
									btnBgPicker;

	// Styling Toggles
	private ToggleButton			tgBold, tgItalic, tgUnderline,
									tgStrikethrough, tgBgColor, tgFgColor;

	// List Toggles
	private ToggleButton			tgBullet, tgOl;

	// Alignment Toggles
	private ToggleButton			tgAlignRight, tgAlignLeft, tgAlignCenter;
	private ToggleButton			tgTest;

	private ToggleButton[]			mToggles;

	private String					mFontFamily;
	private Drawable				mDrawable;


	private RichEditTextField		mEt;
	private ImageView				mClearButton;
	private ViewGroup				mFormattingLayout;
	private ColorPickerDialog		fgColorPicker, bgColorPicker;
	private EditTextValidator		editTextValidator;

	private final RichTextWatcher	mSpanWatcher			= this;


	public RichEditText(Context context){

		this(	context,
				null);

		throw new RuntimeException("This constructor isn't supported. Surely you have SOME attributes?");
	}


	public RichEditText(Context context,
						AttributeSet attrs){

		this(	context,
				attrs,
				-1);

	}


	public RichEditText(Context context,
						AttributeSet attrs,
						int defStyle){

		super(	context,
				attrs,
				defStyle);

		if (!isInEditMode()){
			editTextValidator = new DefaultEditTextValidator(	mEt,
																attrs,
																context);

			density = WidgetUtil.getScreenDensity(getContext());

			initViews(attrs);
			initAttributes(attrs);
		}
	}


	@Override
	protected void onRestoreInstanceState(Parcelable state){

		Bundle mBundle = (Bundle) state;
		if (mBundle != null
			&& !mBundle.isEmpty()){
			fgColor = mBundle.getInt("fgColor");
			bgColor = mBundle.getInt("bgColor");
			super.onRestoreInstanceState(mBundle.getParcelable("instanceState"));
			return;
		}

		super.onRestoreInstanceState(state);
	}


	@Override
	protected Parcelable onSaveInstanceState(){

		// TODO: include toggle button states
		Bundle mBundle = new Bundle();
		mBundle.putInt(	"fgColor",
						fgColor);
		mBundle.putParcelable(	"instanceState",
								super.onSaveInstanceState());
		mBundle.putInt(	"bgColor",
						bgColor);


		return mBundle;
	}


	/***********************************************************
	 * 
	 * Selection change listener
	 * 
	 * 
	 ************************************************************/

	@Override
	public void onSelectionChanged(int selStart, int selEnd){

		Log.i(	"EDITOR",
				"-----------------------------------\n "
					+ "Selection Changed \n"
					+ "Start: "
					+ selStart
					+ "\nEnd: "
					+ selEnd
					+ "\n----------------------------------- ");


		if (selEnd
			- selStart != 0){ // User Selected a range

			selectionStart = selStart;
			selectionEnd = selEnd;
			selectionLength = selEnd
								- selStart;
			isUserSelectingRange = true;
		}else{

			isUserSelectingRange = false;
			selectionPosition = selEnd; // User Selected a point

			if (showFormattingOptions)
				updateToggledStates(selectionPosition);
		}
	}


	/************************************************************
	 * 
	 * Rich Text Watcher
	 * 
	 * 
	 * All operations are actually conveniences for replacements of some type
	 * 
	 * note that with replace, if a span is completely engulfed by the
	 * replacement, it will be removed
	 * 
	 *************************************************************/

	/*
	 * Caution! This is called for any kind of input - call methods here very
	 * sparingly
	 */
	@Override
	public void beforeReplace(int start,
								int end,
								CharSequence tb,
								int tbstart,
								int tbend){

		isUserSelectingRange = false;
	}


	/*
	 * Caution! This is called for any kind of input - call methods here very
	 * sparingly
	 */
	@Override
	public void afterReplace(int start,
								int end,
								CharSequence tb,
								int tbstart,
								int tbend){

		isUserSelectingRange = false;
	}


	@Override
	public void onBeforeInsert(int position,
								CharSequence repText,
								int repStart,
								int repEnd){

		getAppliedSpans(position);
	}


	@Override
	public void onAfterInsert(int position,
								CharSequence repText,
								int repStart,
								int repEnd){

		int insertionLength = repEnd
								- repStart;

		cursorPosition = position
							+ insertionLength;

		if (insertionLength != 0)
			updateAppliedSpans(	position,
								cursorPosition);
		else
			Log.e(	"EDITOR",
					"Insertion was called, but it's a replacement of \"\". Deletion should have been called");
	}


	@Override
	public void onBeforeAppend(int endPosition,
								CharSequence repText,
								int repStart,
								int repEnd){

		getAppliedSpans(endPosition);
	}


	@Override
	public void onAfterAppend(int position,
								CharSequence repText,
								int repStart,
								int repEnd){

		int insertionLength = repEnd
								- repStart;

		cursorPosition = position;

		if (insertionLength != 0)
			updateAppliedSpans(	position,
								position
									+ insertionLength);
		else
			Log.e(	"EDITOR",
					"Append was called, but it's a replacement of \"\". Deletion should have been called");
	}


	/*
	 * Remember that: - When we replace, we apply whatever is toggled and update
	 * applied spans accordingly
	 */
	@Override
	public void onBeforeCompose(int start,
								int end,
								CharSequence repText,
								int repStart,
								int repEnd){

		getAppliedSpans(start,
						end);
	}


	@Override
	public void onAfterCompose(int start,
								int end,
								CharSequence repText,
								int repStart,
								int repEnd){

		int insertionLength = repEnd
								- repStart;

		cursorPosition = end;

		if (insertionLength != 0)
			updateAppliedSpans(	start,
								start
									+ insertionLength);
		else
			Log.e(	"EDITOR",
					"Compose was called, but it's a replacement of \"\". Deletion should have been called");
	}


	/*
	 * Remember that: - Delete is replacing start - end with nothing - repText =
	 * ""
	 */
	@Override
	public void onBeforeDelete(int start, int end){

	}


	@Override
	public void onAfterDelete(int start, int end){

		cursorPosition = Selection.getSelectionEnd(mEt.getText());
	}


	@Override
	public void onTextActionCursorMove(int position){

	}


	/***********************************************************
	 * 
	 * BaseSpan Watcher implementation from RichTextWatcher
	 * 
	 ************************************************************/

	@Override
	public void onSpanAdded(Spannable text, Object what, int start, int end){

		if (what != null
			&& what.getClass() != null){

			// Log.i("EDITOR",
			// "BaseSpan Added: " + what.getClass()
			// .getName() + "\n\n");
		}
	}


	@Override
	public void onSpanChanged(Spannable text,
								Object what,
								int ostart,
								int oend,
								int nstart,
								int nend){


	}


	@Override
	public void onSpanRemoved(Spannable text, Object what, int start, int end){

		if (what != null
			&& what.getClass() != null){
		}
		// if (what instanceof ISpan) {
		// Log.i("EDITOR",
		// "BaseSpan Removed: " + what.getClass()
		// .getName() + "\n\n");
		// }


	}


	/************************************************************
	 * 
	 * Text Changed Listener
	 * 
	 *************************************************************/
	@Override
	public void afterTextChanged(final Editable s){


	}


	@Override
	public void beforeTextChanged(final CharSequence s,
									final int start,
									final int count,
									final int after){


	}


	@Override
	public void onTextChanged(final CharSequence s,
								final int start,
								final int before,
								final int count){


	}


	/***********************************************************
	 * 
	 * On Click Listener
	 * 
	 ************************************************************/
	@Override
	public void onClick(View v){

		int id = v.getId();
		if (id == R.id.sup){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	SUPERSCRIPT,
							selectionStart,
							selectionEnd,
							true);
			}
		}else if (id == R.id.sub){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	SUBSCRIPT,
							selectionStart,
							selectionEnd,
							true);
			}
		}else if (id == mClearButton.getId())
			mEt.setText("");
		else if (id == R.id.bgColorPicker)
			showBgColorPicker();
		else if (id == R.id.fgColorPicker)
			showFgColorPicker();
	}


	/************************************************************
	 * 
	 * On Checked Changed Listener (Toggle buttons)
	 * 
	 *************************************************************/
	@Override
	public void onCheckedChanged(final CompoundButton buttonView,
									final boolean isChecked){

		int id = buttonView.getId();
		if (id == R.id.bold){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	BOLD,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.italic){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	ITALIC,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.underline){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	UNDERLINE,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.strikethrough){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	STRIKE,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.bgColor){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	BACKGROUND_COLOR,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.fgColor){
			if (isUserSelectingRange){

				getAppliedSpans(selectionStart,
								selectionEnd);

				updateSpan(	FOREGROUND_COLOR,
							selectionStart,
							selectionEnd,
							isChecked);
			}
		}else if (id == R.id.bullet){
			Log.i(	"EDITOR",
					"\n\n***********************************\n"
						+ "Selection Position: "
						+ Selection.getSelectionStart(mEt.getText())
						+ "\nEnd of Buffer: "
						+ mEt.getText()
								.length()
						+ "\n\n***********************************");
			if (isUserSelectingRange){
				getAppliedSpans(selectionStart,
								selectionEnd);

				updateParagraphSpan(BULLET,
									selectionStart,
									selectionEnd,
									isChecked);
			}
		}
	}


	private void initViews(AttributeSet attrs){

		ViewGroup mLayout =
							(ViewGroup) LayoutInflater.from(getContext())
														.inflate(	R.layout.rich_edit_text,
																	this);

		mEt = (RichEditTextField) mLayout.findViewById(R.id.editTextField);

		mClearButton = (ImageView) mLayout.findViewById(R.id.clearButton);
		mFormattingLayout =
							(HorizontalScrollView) mLayout.findViewById(R.id.formattingOptions);

		// Text formatting spans
		btnSubScript = (Button) mLayout.findViewById(R.id.sub);
		btnSuperScript = (Button) mLayout.findViewById(R.id.sup);

		tgBold = (ToggleButton) mLayout.findViewById(R.id.bold);
		tgItalic = (ToggleButton) mLayout.findViewById(R.id.italic);
		tgUnderline = (ToggleButton) mLayout.findViewById(R.id.underline);
		tgStrikethrough =
							(ToggleButton) mLayout.findViewById(R.id.strikethrough);
		tgBgColor = (ToggleButton) mLayout.findViewById(R.id.bgColor);
		tgFgColor = (ToggleButton) mLayout.findViewById(R.id.fgColor);

		btnBgPicker = (Button) mLayout.findViewById(R.id.bgColorPicker);
		btnFgPicker = (Button) mLayout.findViewById(R.id.fgColorPicker);

		// Paragraph Spans
		tgBullet = (ToggleButton) mLayout.findViewById(R.id.bullet);
		tgOl = (ToggleButton) mLayout.findViewById(R.id.ordered);
		tgAlignLeft = (ToggleButton) mLayout.findViewById(R.id.alignLeft);
		tgAlignCenter = (ToggleButton) mLayout.findViewById(R.id.alignCenter);
		tgAlignRight = (ToggleButton) mLayout.findViewById(R.id.alignOpposite);
		tgTest = (ToggleButton) mLayout.findViewById(R.id.test);

		setEditTextValidator(new DefaultEditTextValidator(	mEt,
															attrs,
															getContext()));

		mLayout.clearFocus();

		mEt.requestFocus();
	}


	private void initAttributes(AttributeSet attrs){

		// TODO: funnel attributes to RichEditTextField

		Log.i(	TAG,
				"\n \nINIT ATTRIBUTES \n \n");

		if (attrs != null)
			for (int i = 0; i < attrs.getAttributeCount(); i++){

				fontName =
							attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
													"fontName");

				showClearButton =
									attrs.getAttributeBooleanValue(	"http://schemas.android.com/apk/res-auto",
																	"showClearButton",
																	true);

				showFormattingOptions =
										attrs.getAttributeBooleanValue(	"http://schemas.android.com/apk/res-auto",
																		"showFormattingOptions",
																		true);

				Log.i(	TAG,
						"Attribute\n Name: "
							+ attrs.getAttributeName(i)
							+ "       Value: "
							+ attrs.getAttributeValue(i));
			}

		showFormattingOptions(showFormattingOptions);
		showClearButton(showClearButton);

		if (showFormattingOptions)
			initFormattingOptions();

		if (showClearButton)
			mClearButton.setOnClickListener(this);

		if (fontName != null)
			setFont();

	}


	private void initFormattingOptions(){

		mEt.setEditableFactory(new TextFactory());
		mEt.addTextChangedListener(this);
		mEt.setOnSelectionChangedListener(this);

		btnSubScript.setOnClickListener(this);
		btnSuperScript.setOnClickListener(this);

		btnBgPicker.setOnClickListener(this);
		btnFgPicker.setOnClickListener(this);

		tgBold.setOnCheckedChangeListener(this);
		tgItalic.setOnCheckedChangeListener(this);
		tgUnderline.setOnCheckedChangeListener(this);
		tgStrikethrough.setOnCheckedChangeListener(this);
		tgBgColor.setOnCheckedChangeListener(this);
		tgFgColor.setOnCheckedChangeListener(this);

		tgBold.setTag(BOLD);
		tgItalic.setTag(ITALIC);
		tgUnderline.setTag(UNDERLINE);
		tgStrikethrough.setTag(STRIKE);
		tgBgColor.setTag(BACKGROUND_COLOR);
		tgFgColor.setTag(FOREGROUND_COLOR);

		tgTest.setOnCheckedChangeListener(this);
		tgBullet.setOnCheckedChangeListener(this);
		tgOl.setOnCheckedChangeListener(this);
		tgAlignLeft.setOnCheckedChangeListener(this);
		tgAlignCenter.setOnCheckedChangeListener(this);
		tgAlignRight.setOnCheckedChangeListener(this);

		tgBullet.setTag(BULLET);
		tgOl.setTag(OL);
		tgAlignCenter.setTag(ALIGN_CENTER);

		mToggles =
					new ToggleButton[] { tgBold,
										tgItalic,
										tgUnderline,
										tgStrikethrough,
										tgBgColor,
										tgFgColor };
	}


	private void showFgColorPicker(){

		fgColorPicker = null;

		if (fgColorPicker == null)
			fgColorPicker = new ColorPickerDialog(	getContext(),
													new OnColorChangedListener(){

														@Override
														public void
															onColorChanged(int color){

															fgColor = color;
														}
													},
													fgColor);
		fgColorPicker.setOldColor(fgColor);
		fgColorPicker.show();


	}


	private void showBgColorPicker(){

		bgColorPicker = null;

		if (bgColorPicker == null)
			bgColorPicker = new ColorPickerDialog(	getContext(),
													new OnColorChangedListener(){

														@Override
														public void
															onColorChanged(int color){

															bgColor = color;


														}
													},
													bgColor);

		bgColorPicker.setOldColor(bgColor);
		bgColorPicker.show();

	}


	/**
	 * Set to show or hide the Clear text button.
	 * 
	 * @param showClearValue
	 *            true - Clear text button will be shown false - Clear text
	 *            button will be hidden
	 */
	public void showClearButton(boolean showClearValue){

		if (showClearValue)
			mClearButton.setVisibility(View.VISIBLE);
		else
			mClearButton.setVisibility(View.GONE);

	}


	/**
	 * Set to show or hide the text formatting options.
	 * 
	 * @param showClearValue
	 *            true: show false: hide
	 */
	public void showFormattingOptions(boolean showFormattingOptions){

		if (showFormattingOptions){
			mFormattingLayout.setVisibility(View.VISIBLE);

			if (mToggles == null)
				initFormattingOptions();

		}else
			mFormattingLayout.setVisibility(View.GONE);

	}


	private void setFont(){

		if (fontName != null
			&& !isInEditMode()){ // editMode is used in Eclipse/dev tools to
									// draw
									// views in layout
			// builders.
			Log.i(	TAG,
					"fontName is not null. Setting font: "
						+ fontName);
			Typeface mtf = WidgetUtil.get(	getContext().getApplicationContext(),
											fontName);

			if (mtf != null)
				mEt.setTypeface(mtf);
			else
				Log.e(	TAG,
						"Couldn't set the typeface in edit mode (something went wrong in eclipse layout builder)");
		}
	}


	/**
	 * Add a validator to this RichEditText. The validator will be added in the
	 * queue of the current validators.
	 * 
	 * @param theValidator
	 * @throws IllegalArgumentException
	 *             if the validator is null
	 */
	public void
		addValidator(Validator theValidator) throws IllegalArgumentException{

		editTextValidator.addValidator(theValidator);
	}


	public EditTextValidator getEditTextValidator(){

		return editTextValidator;
	}


	public void setEditTextValidator(EditTextValidator editTextValidator){

		this.editTextValidator = editTextValidator;
	}


	/**
	 * Calling *testValidity()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity(){

		return editTextValidator.isValid();
	}


	/**
	 * Calling *isValid()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	public boolean isValid(){

		return editTextValidator.isValid();
	}


	public void setText(CharSequence text, BufferType type){

		mEt.setText(text,
					type);
	}


	public int length(){

		return mEt.length();
	}


	public void setTextAppearance(Context context, int resid){

		mEt.setTextAppearance(	context,
								resid);
	}


	public void setTextColor(int color){

		mEt.setTextColor(color);
	}


	public void setTextColor(ColorStateList colors){

		mEt.setTextColor(colors);
	}


	public final void setText(CharSequence text){

		mEt.setText(text);
	}


	public final void setText(char[] text, int start, int len){

		mEt.setText(text,
					start,
					len);
	}


	public final void setText(int resid){

		mEt.setText(resid);
	}


	public final void setText(int resid, BufferType type){

		mEt.setText(resid,
					type);
	}


	@Override
	public void setBackgroundColor(int color){

		mEt.setBackgroundColor(color);
	}


	@Override
	public void setBackgroundResource(int resid){

		mEt.setBackgroundResource(resid);
	}


	@Override
	public void setBackground(Drawable background){

		mEt.setBackgroundDrawable(background);
	}


	@Override
	public void setTag(Object tag){

		mEt.setTag(tag);
	}


	@Override
	public void setTag(int key, Object tag){

		mEt.setTag(	key,
					tag);
	}


	public Editable getText(){

		return mEt.getText();
	}


	public final ColorStateList getTextColors(){

		return mEt.getTextColors();
	}


	@Override
	public Object getTag(){

		return mEt.getTag();
	}


	@Override
	public Object getTag(int key){

		return mEt.getTag(key);
	}


	/***********************************************************
	 * 
	 * Document BaseSpan Tracking/Placing Helpers
	 * 
	 ************************************************************/

	private void getAjacentSpans(int start, int end){

		ajacentSpans = null;

		ajacentSpans = mEt.getText()
							.getSpans(	start,
										end,
										ISpan.class);

		updateSpanPositions(ajacentSpans);
	}


	/**
	 * Used for INSERTION/APPEND
	 */
	private void getAppliedSpans(int position){

		if (position < 1)
			position = 0;

		getAjacentSpans(position - 1,
						position + 1);

		appliedSpans = null;


		if (ajacentSpans != null){ // check ajacentSpans for any that would
									// apply to this position

			appliedSpans = new ISpan[0];

			for (ISpan span : ajacentSpans)
				if (isSpanApplied(	span,
									position))
					appliedSpans = ArrayUtils.add(	appliedSpans,
													span);

		}

		if (appliedSpans != null){
			Log.i(	"EDITOR",
					"\n-------------------------------------------\n"
						+ "APPLIED SPANS"
						+ "\n-------------------------------------------\n\n");
			for (ISpan span : ajacentSpans)
				span.dump();
		}
	}


	/**
	 * TODO implement paragraph flag checking Used for RANGE of Characters
	 */
	private void getAppliedSpans(int start, int end){

		if (start < 1)
			start = 0;

		if (end < start)
			Log.e(	"EDITOR",
					"The range end is less than the range start!");

		if (end == start)
			getAppliedSpans(end); // ended up with a position instead of a range
									// so this is an
									// insertion/append
		else{

			appliedSpans = null;

			getAjacentSpans(start,
							end);

			if (ajacentSpans != null){ // check ajacentSpans for any that would
										// apply to this range

				appliedSpans = new ISpan[0];

				for (ISpan span : ajacentSpans)
					if (isSpanApplied(	span,
										start,
										end))
						appliedSpans = ArrayUtils.add(	appliedSpans,
														span);
			}

			if (appliedSpans != null){
				Log.i(	"EDITOR",
						"\n-------------------------------------------\n"
							+ "APPLIED SPANS"
							+ "\n-------------------------------------------\n\n");
				for (ISpan span : ajacentSpans)
					span.dump();
			}
		}
	}


	private boolean isSpanApplied(ISpan span, int position){

		// BaseSpan engulfs range - regardless of type, it's applied
		if (span.getStartPosition() < position
			&& span.getEndPosition() > position)
			return true;

		// If it's a paragraph span and
		else if (span.getFlag() == Spanned.SPAN_PARAGRAPH){

			int synFlag =
							((IParagraphSpan) span).getFlagSynonym(cursorPosition);

			if (span.getStartPosition() == position
				&& ((IParagraphSpan) span).isStartInclusive(synFlag)
				|| span.getEndPosition() == position
				&& ((IParagraphSpan) span).isEndInclusive(synFlag))
				return true;

		}else if (span.getStartPosition() == position
					&& span.isStartInclusive()
					|| span.getEndPosition() == position
					&& span.isEndInclusive())
			return true;

		return false;
	}


	private boolean isSpanApplied(ISpan span, int start, int end){

		if (span.getFlag() == Spanned.SPAN_PARAGRAPH){

			int synFlag = ((IParagraphSpan) span).getFlagSynonym(cursorPosition);

			if (span.getEndPosition() > start){

				if (span.getStartPosition() < end
					|| span.getStartPosition() == end
					&& ((IParagraphSpan) span).isStartInclusive(synFlag))
					return true;

			}else if (span.getEndPosition() == start
						&& ((IParagraphSpan) span).isEndInclusive(synFlag))
				return true;

		}else if (span.getEndPosition() > start){

			if (span.getStartPosition() < end
				|| span.getStartPosition() == end
				&& ((FontSpan) span).isStartInclusive())
				return true;

		}else if (span.getEndPosition() == start
					&& span.isEndInclusive())
			return true;

		return false;
	}


	private ISpan[] isSpanTypeApplied(int type){

		ISpan[] mSpans = null;

		if (appliedSpans != null)
			for (ISpan mSpan : appliedSpans)
				if (mSpan.getType() == type)
					mSpans = ArrayUtils.add(mSpans,
											mSpan);


		return mSpans;
	}


	private void updateSpanPositions(ISpan[] mSpans){

		if (mSpans != null)
			for (ISpan span : mSpans){
				span.setStartPosition(mEt.getText()
											.getSpanStart(span));
				span.setEndPosition(mEt.getText()
										.getSpanEnd(span));
			}
	}


	private void updateAppliedSpans(int start, int end){

		if (appliedSpans != null)
			updateSpanPositions(appliedSpans);

		int toggleType = 0;

		for (ToggleButton mToggle : mToggles){

			if (mToggle.getTag() != null)
				toggleType = ((Integer) mToggle.getTag()).intValue();
			else
				Log.e(	"EDITOR",
						"A toggle button with text, "
							+ mToggle.getText()
							+ ", was not tagged with its type.");

			if (!SpanUtil.isParagraphSpanType(toggleType))
				updateSpan(	toggleType,
							start,
							end,
							mToggle.isChecked());
			else
				updateParagraphSpan(toggleType,
									start,
									end,
									mToggle.isChecked());
		}
	}


	private void updateSpan(int type, int start, int end, boolean isToggled){

		ISpan[] mSpans = isSpanTypeApplied(type);

		if (mSpans != null
			&& mSpans.length > 1)
			mSpans = combineOverlappingSpans(mSpans);

		if (mSpans != null
			&& isUserSelectingRange
			&& isToggled)
			mSpans = SpanUtil.isSpansCoveringRange(	mSpans,
													type,
													start,
													end);

		boolean isApplied = (mSpans != null);

		if (isToggled
			&& !isApplied){ // Is Toggled, but not applied - apply it

			boolean appliedExtended = false;

			for (ISpan mSpan : ajacentSpans)
				if (mSpan.getType() == type
					&& mSpan.getEndPosition() == start){

					mSpan.setEndPosition(start
											+ (end - start));
					SpanUtil.reApplySpan(	mSpan,
											mEt.getText());

					appliedExtended = true;
					break;
				}

			if (!appliedExtended)
				applySpan(	type,
							start,
							end,
							Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		}else if (!isToggled
					&& isApplied
					&& mSpans.length > 0) // Is applied, but not toggled. Remove
											// if within start/end (or equal to
											// start/end if that end is
											// EXCLUSIVE)
			for (ISpan mSpan : mSpans)
				if (mSpan.getStartPosition() >= start){
					if (mSpan.getEndPosition() <= end)
						mEt.getText()
							.removeSpan(mSpan);
					else if (mSpan.getStartPosition() == end){
						SpanUtil.setSpanStartExclusive(mSpan);
						SpanUtil.reApplySpan(	mSpan,
												mEt.getText());
					}else
						splitSpan(	mSpan,
									start,
									end);
				}else if (mSpan.getEndPosition() == start){
					SpanUtil.setSpanEndExclusive(mSpan);
					SpanUtil.reApplySpan(	mSpan,
											mEt.getText());
				}else
					splitSpan(	mSpan,
								start,
								end);
	}


	private void updateParagraphSpan(int type,
										int start,
										int end,
										boolean isToggled){

		// TODO: update paragraph BaseSpan - this is were the magic happens -
		// see
		// what I did with
		// updateSpan
	}


	private void applySpan(int type, int start, int end, int flag){

		ISpan mSpan = makeSpan(	type,
								start,
								end,
								flag);

		if (mSpan != null)
			mSpan.setSpan(mEt.getText());
	}


	private ISpan makeSpan(int type, int start, int end, int flag){

		switch(type){

		// Appearance Spans
			case BOLD:
			case ITALIC:
			case BOLD_ITALIC:
				return new TextStyleSpan(	type,
											start,
											end,
											flag);

			case UNDERLINE:
				return new UnderliningSpan(	start,
											end,
											flag);

			case STRIKE:
				return new StrikeSpan(	start,
										end,
										flag);

			case SUPERSCRIPT:
				return new TextSuperscriptSpan(	start,
												end,
												flag);

			case SUBSCRIPT:
				return new TextSubscriptSpan(	start,
												end,
												flag);
			case IMAGE:
				return new ImgSpan(	start,
									end,
									flag,
									mDrawable);
			case BACKGROUND_COLOR:
				return new TextBackgroundColorSpan(	start,
													end,
													flag,
													bgColor);
			case FOREGROUND_COLOR:
				return new TextForgroundColorSpan(	start,
													end,
													flag,
													fgColor);
			case FONT:
				return new FontSpan(start,
									end,
									flag,
									mFontFamily);

				// Paragraph Spans
			case BULLET:
				return new BulletListSpan(	start,
											end,
											density,
											bulletColor,
											bulletMarginWidth);

			case OL:
				break;
		}

		return null;
	}


	// TODO: Implement Paragraph SplitSpan
	private void splitSpan(ISpan mSpan, int start, int end){

		if (mSpan != null){

			mEt.getText()
				.removeSpan(mSpan);

			if (mSpan.getStartPosition() >= start
				&& mSpan.getEndPosition() <= end)
				Log.e(	"EDITOR",
						"A span that should be removed was sent to split");
			else if (mSpan.getStartPosition() < start
						&& mSpan.getEndPosition() > end){

				int flag;

				// Apply first span (Covers start and back)
				flag =
						(mSpan.isStartInclusive())
													? Spanned.SPAN_INCLUSIVE_EXCLUSIVE
													: Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
				applySpan(	mSpan.getType(),
							mSpan.getStartPosition(),
							start,
							flag);


				// Apply second BaseSpan (Covers end and forward)
				flag =
						(mSpan.isEndInclusive())
												? Spanned.SPAN_EXCLUSIVE_INCLUSIVE
												: Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

				applySpan(	mSpan.getType(),
							end,
							mSpan.getEndPosition(),
							flag);


			}else if (mSpan.getStartPosition() >= start)
				// spnstart = end
				applySpan(	mSpan.getType(),
							end,
							mSpan.getEndPosition(),
							mSpan.getFlag());
			else if (mSpan.getStartPosition() < start)
				// spnEnd = start
				applySpan(	mSpan.getType(),
							mSpan.getStartPosition(),
							start,
							mSpan.getFlag());

		}else
			Log.e(	"EDITOR",
					"Can't split a null span!");
	}


	private ISpan[] combineOverlappingSpans(ISpan[] mSpans){

		ISpan[] testSpans = ArrayUtils.clone(mSpans);

		do
			if (mSpans.length < 2)
				testSpans = null;
			else{

				testSpans = SpanUtil.isCombinable(mSpans);

				if (testSpans != null){


					mSpans = ArrayUtils.removeElement(	mSpans,
														testSpans[0]);
					mSpans = ArrayUtils.removeElement(	mSpans,
														testSpans[1]);
					appliedSpans = ArrayUtils.removeElement(appliedSpans,
															testSpans[0]);
					appliedSpans = ArrayUtils.removeElement(appliedSpans,
															testSpans[1]);


					mSpans =
								ArrayUtils.add(	mSpans,
												combineOverlappingSpans(testSpans[0],
																		testSpans[1]));

					mEt.getText()
						.removeSpan(testSpans[0]);
					mEt.getText()
						.removeSpan(testSpans[1]);


					testSpans = ArrayUtils.clone(mSpans);
				}
			}
		while (testSpans != null);

		return mSpans;
	}


	// TODO: Post 1.0 For BulletSpan (UL) - They can be nested in the future,
	// but only if the
	// overlap includes a Tab
	// after the \n
	// denoting a sub-bullet.
	private ISpan combineOverlappingSpans(ISpan mSpan1, ISpan mSpan2){

		if (mSpan1.getType() != mSpan2.getType()){
			Log.e(	"EDITOR",
					"Cannot combineOverlappingSpans: Spans are different types");
			return null;
		}

		int startFlag;
		int endFlag;
		int newFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
		int start = mSpan1.getStartPosition();
		int end = mSpan2.getEndPosition();

		// Find closest start and flag
		if (mSpan1.getStartPosition() < mSpan2.getStartPosition())
			startFlag =
						(mSpan1.isStartInclusive()) ? ISpan.INCLUSIVE
													: ISpan.EXCLUSIVE;
		else if (mSpan1.getStartPosition() == mSpan2.getStartPosition())
			// if either starts
			// are
			// inclusive, start
			// should be inclusive
			startFlag =
						(mSpan1.isStartInclusive() || mSpan2.isStartInclusive())
																				? ISpan.INCLUSIVE
																				: ISpan.EXCLUSIVE;
		else{ // Span2 start is lower

			startFlag =
						(mSpan2.isStartInclusive()) ? ISpan.INCLUSIVE
													: ISpan.EXCLUSIVE;
			start = mSpan2.getStartPosition();
		}

		// Find farthest end and flag
		if (mSpan1.getEndPosition() > mSpan2.getEndPosition()){ // Span1 end is
																// higher

			endFlag =
						(mSpan1.isEndInclusive()) ? ISpan.INCLUSIVE
													: ISpan.EXCLUSIVE;
			end = mSpan1.getEndPosition();

		}else if (mSpan1.getEndPosition() == mSpan2.getEndPosition())
			endFlag =
						(mSpan1.isEndInclusive() || mSpan2.isEndInclusive())
																			? ISpan.INCLUSIVE
																			: ISpan.EXCLUSIVE;
		else
			endFlag =
						(mSpan2.isEndInclusive()) ? ISpan.INCLUSIVE
													: ISpan.EXCLUSIVE;

		switch(startFlag){
			case ISpan.EXCLUSIVE:

				newFlag =
							(endFlag == ISpan.EXCLUSIVE)
														? Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
														: Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
				break;

			case ISpan.INCLUSIVE:

				newFlag =
							(endFlag == ISpan.EXCLUSIVE)
														? Spanned.SPAN_INCLUSIVE_EXCLUSIVE
														: Spanned.SPAN_INCLUSIVE_INCLUSIVE;
				break;
		}

		return makeSpan(mSpan1.getType(),
						start,
						end,
						newFlag);
	}


	/***********************************************************
	 * 
	 * Button Toggle Helpers
	 * 
	 ************************************************************/
	private void updateToggledStates(int selPosition){

		getAppliedSpans(selPosition);

		if (appliedSpans != null){

			int toggleType;

			if (appliedSpans.length < mToggles.length)
				for (ISpan mSpan : appliedSpans)
					for (ToggleButton mToggle : mToggles){

						toggleType = ((Integer) mToggle.getTag()).intValue();
						if (!mToggle.isChecked()
							&& mSpan.getType() == toggleType)
							mToggle.setChecked(true);
					}
			else
				for (ToggleButton mToggle : mToggles){

					toggleType = ((Integer) mToggle.getTag()).intValue();
					for (ISpan mSpan : appliedSpans)
						if (!mToggle.isChecked()
							&& mSpan.getType() == toggleType)
							mToggle.setChecked(true);
				}

		}
	}


	/***********************************************************
	 * 
	 * DEBUG HELPERS
	 * 
	 ************************************************************/
	private void printAjacentSpans(){

		if (ajacentSpans != null){
			Log.i(	"EDITOR",
					"\n\n----------------------------\n"
						+ "PRINTING AJACENT SPANS\n"
						+ "AjacentSpans Length: "
						+ ajacentSpans.length
						+ "\n");

			for (ISpan mSpan : ajacentSpans){
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
				}
				Log.i(	"EDITOR",
						"Flag: "
							+ flag);
				Log.i(	"EDITOR",
						"Type: "
							+ type);
				Log.i(	"EDITOR",
						"Action: "
							+ action);
			}
			Log.i(	"EDITOR",
					"----------------------------\n\n\n");
		}
	}


	public void printAllSpans(){

		ISpan[] mSpans = mEt.getText()
							.getSpans(	0,
										mEt.length(),
										ISpan.class);

		if (mSpans != null
			&& mSpans.length > 0){

			Log.i(	"EDITOR",
					"\n\n----------------------------\n"
						+ "PRINTING ALL SPANS\n");

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
					case BULLET:
						type = "UnorderedList";
						break;
					case OL:
						type = "OrderedList";
						break;
					case UNDERLINE:
						type = "Underline";
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
						+ "NO SPANS IN DOCUMENT \n\n\n");
	}
}
