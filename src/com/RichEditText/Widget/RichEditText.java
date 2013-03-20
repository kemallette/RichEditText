package com.RichEditText.Widget;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.RichEditText.Validations.Validator;

public class RichEditText extends EditText{

	private static final String	TAG	= "RichEditText";

	private Context	            ctx;


	public RichEditText(Context context){

		super(context);

		ctx = context;
	}


	public RichEditText(Context context, AttributeSet attrs){

		super(context,
		      attrs);

		ctx = context;

		editTextValidator = new DefaultEditTextValidator(this,
		                                                 attrs,
		                                                 ctx);

		setFont(attrs);
	}


	public RichEditText(Context context, AttributeSet attrs, int defStyle){

		super(context,
		      attrs,
		      defStyle);

		ctx = context;

		editTextValidator = new DefaultEditTextValidator(this,
		                                                 attrs,
		                                                 context);
		setFont(attrs);	
	}


	private void setFont(AttributeSet attrs){

		String fontName = null;

		for (int i = 0; i < attrs.getAttributeCount(); i++) {
			fontName =
			           attrs.getAttributeValue("http://schemas.android.com/apk/res/com.RichEditText.Demo",
			                                   "fontName");
			Log.i(TAG, "Attribute\n Name: " + attrs.getAttributeName(i) + "\n Value: " + attrs.getAttributeValue(i));
		}

		if (fontName != null && !isInEditMode()){ // editMode is used in Eclipse/dev tools to draw views in layout
			                  // builders.

			Typeface mtf = WidgetUtil.get(ctx.getApplicationContext(),
			                              fontName);

			if (mtf != null)
				setTypeface(mtf);
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
	 * Calling *testValidity()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity(){

		return editTextValidator.testValidity();
	}

	private EditTextValidator	editTextValidator;

}
