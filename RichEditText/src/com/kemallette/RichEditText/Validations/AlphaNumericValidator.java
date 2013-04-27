package com.kemallette.RichEditText.Validations;


public class AlphaNumericValidator extends RegexpValidator {
	public AlphaNumericValidator(String message) {
		super(message, 	"[a-zA-Z0-9 \\./-]*");
	}
	
}
