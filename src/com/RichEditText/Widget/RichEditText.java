package com.RichEditText.Widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.RichEditText.Validations.Validator;

/**
 * EditText Extension to be used in order to create forms in android.
 * 
 */
public class RichEditText extends EditText{

	public RichEditText(Context context){

		super(context);
		// FIXME how should this constructor be handled
	}


	public RichEditText(Context context, AttributeSet attrs){

		super(context,
		      attrs);
		editTextValidator = new DefaultEditTextValidator(this,
		                                                 attrs,
		                                                 context);
	}


	public RichEditText(Context context, AttributeSet attrs, int defStyle){

		super(context,
		      attrs,
		      defStyle);
		editTextValidator = new DefaultEditTextValidator(this,
		                                                 attrs,
		                                                 context);

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
