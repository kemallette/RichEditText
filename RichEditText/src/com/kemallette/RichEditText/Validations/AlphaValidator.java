package com.kemallette.RichEditText.Validations;

public class AlphaValidator extends RegexpValidator {

	public AlphaValidator(String message) {
		super(message, "[a-zA-Z \\./-]*");
	}
}
