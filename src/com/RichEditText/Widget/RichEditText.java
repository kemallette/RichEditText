package com.RichEditText.Widget;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView.BufferType;

import com.RichEditText.Validations.Validator;


public class RichEditText extends RelativeLayout implements OnClickListener{

	private static final String	TAG	            = "RichEditText";
	private static final int	EDIT_TEXT_ID	= 1000;
	private static final int	CLEAR_BUTTON_ID	= 2000;

	private String	            fontName	    = null;


	private EditText	        mEt;
	private ImageView	        mClearButton;
	private EditTextValidator	editTextValidator;


	public RichEditText(Context context){

		super(context);
	}


	public RichEditText(Context context, AttributeSet attrs, int defStyle){

		super(context,
		      attrs,
		      defStyle);

		initViews(attrs);
		initAttributes(attrs);
	}


	public RichEditText(Context context, AttributeSet attrs){

		super(context,
		      attrs);

		initViews(attrs);
		initAttributes(attrs);
	}


	@Override
	public void onClick(View v){

		if (v.getId() == mClearButton.getId()){
			mEt.setText("");
		}
	}


	private void initViews(AttributeSet attrs){

		mEt = new EditText(getContext(),
		                   attrs);
		mEt.setId(EDIT_TEXT_ID);

		mClearButton = new ImageView(getContext(),
		                             attrs);
		mClearButton.setId(CLEAR_BUTTON_ID);
		mClearButton.setOnClickListener(this);
		mClearButton.setImageResource(android.R.drawable.ic_delete);
		mClearButton.setAdjustViewBounds(true);
		mClearButton.setScaleType(ScaleType.FIT_CENTER);

		RelativeLayout.LayoutParams mEtParams =
		                                        new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
		                                                                        RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams mClearButtonParams =
		                                                 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
		                                                                                 RelativeLayout.LayoutParams.WRAP_CONTENT);
		mEtParams.addRule(RelativeLayout.LEFT_OF,
		                  mClearButton.getId());
		mClearButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
		                           mClearButton.getId());
		mClearButtonParams.addRule(RelativeLayout.ALIGN_TOP,
		                           mEt.getId());
		mClearButtonParams.addRule(RelativeLayout.ALIGN_BOTTOM,
		                           mEt.getId());


		addView(mClearButton,
		        mClearButtonParams);
		addView(mEt,
		        mEtParams);

		editTextValidator = new DefaultEditTextValidator(mEt,
		                                                 attrs,
		                                                 getContext());

	}


	private void initAttributes(AttributeSet attrs){

		boolean showClearButton = true;

		Log.i(TAG,
		      "\n \nINIT ATTRIBUTES \n \n");

		if (attrs != null){
			for (int i = 0; i < attrs.getAttributeCount(); i++){

				fontName =
				           attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
				                                   "fontName");

				if (attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
				                            "showClearButton") != null){

					Log.i(TAG,
					      "\nShow clear button was found in the attributes\n");
					showClearButton =
					                  attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto",
					                                                 "showClearButton",
					                                                 true);
				}
				Log.i(TAG,
				      "Attribute\n Name: " + attrs.getAttributeName(i)
				          + "       Value: " + attrs.getAttributeValue(i));
			}


		}

		showClearButton(showClearButton);

		if (fontName != null){
			setFont();
		}

	}


	/**
	 * Set to show or hide the Clear text button.
	 * 
	 * @param showClearValue
	 *        true - Clear text button will be shown
	 *        false - Clear text button will be hidden
	 */
	public void showClearButton(boolean showClearValue){

		Log.i(TAG,
		      "setting clear button visible: " + showClearValue);

		if (showClearValue)
			mClearButton.setVisibility(View.VISIBLE);
		else
			mClearButton.setVisibility(View.INVISIBLE);

	}


	private void setFont(){

		if (fontName != null && !isInEditMode()){ // editMode is used in Eclipse/dev tools to draw
			                                      // views in layout
			// builders.
			Log.i(TAG,
			      "fontName is not null. Setting font: " + fontName);
			Typeface mtf = WidgetUtil.get(getContext().getApplicationContext(),
			                              fontName);

			if (mtf != null)
				mEt.setTypeface(mtf);
			else
				Log.e(TAG,
				      "Couldn't set the typeface in edit mode (something went wrong in eclipse layout builder)");
		}
	}


	/**
	 * Add a validator to this RichEditText. The validator will be added in the
	 * queue of the current validators.
	 * 
	 * @param theValidator
	 * @throws IllegalArgumentException
	 *         if the validator is null
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

		mEt.setTextAppearance(context,
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


	public void setBackgroundColor(int color){

		mEt.setBackgroundColor(color);
	}


	public void setBackgroundResource(int resid){

		mEt.setBackgroundResource(resid);
	}


	public void setBackground(Drawable background){

		mEt.setBackground(background);
	}


	public void setTag(Object tag){

		mEt.setTag(tag);
	}


	public void setTag(int key, Object tag){

		mEt.setTag(key,
		           tag);
	}


	public Editable getText(){

		return mEt.getText();
	}


	public final ColorStateList getTextColors(){

		return mEt.getTextColors();
	}


	public Object getTag(){

		return mEt.getTag();
	}


	public Object getTag(int key){

		return mEt.getTag(key);
	}

	/***********************************************************
	 * 
	 * EditText Delegates
	 * 
	 ************************************************************/

}
