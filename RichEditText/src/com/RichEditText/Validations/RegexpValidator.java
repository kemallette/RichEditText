package com.RichEditText.Validations;


import java.util.regex.Pattern;

/**
 * Used for validating the user input using a regexp.
 * 
 */
public class RegexpValidator extends PatternValidator{

	public RegexpValidator(String message, String _regexp){

		super(message,
		      Pattern.compile(_regexp));
	}
}
