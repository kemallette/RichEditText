package com.RichEditText.Widget;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.RichEditText.R;
import com.RichEditText.Validations.Validator;


public class RichEditText extends RelativeLayout implements OnClickListener{

	private static final String	TAG	= "RichEditText";

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
	}


	public RichEditText(Context context, AttributeSet attrs){

		super(context,
		      attrs);

		initViews(attrs);

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
		mEt.setId(R.id.et);

		mClearButton = new ImageView(getContext(),
		                             attrs);
		mClearButton.setId(R.id.clear);
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

		setFont(attrs);
	}


	private void setFont(AttributeSet attrs){

		String fontName = null;

		for (int i = 0; i < attrs.getAttributeCount(); i++){
			fontName =
			           attrs.getAttributeValue("http://schemas.android.com/apk/res/com.RichEditText",
			                                   "fontName");
			Log.i(TAG,
			      "Attribute\n Name: " + attrs.getAttributeName(i)
			          + "\n Value: " + attrs.getAttributeValue(i));
		}

		if (fontName != null && !isInEditMode()){ // editMode is used in Eclipse/dev tools to draw
			                                      // views in layout
			// builders.

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
	 * Calling *testValidity()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity(){

		return editTextValidator.testValidity();
	}


}
