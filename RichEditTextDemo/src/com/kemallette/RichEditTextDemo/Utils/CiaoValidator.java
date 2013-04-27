package com.kemallette.RichEditTextDemo.Utils;


import android.text.TextUtils;
import android.widget.EditText;

import com.kemallette.RichEditText.Validations.Validator;

public class CiaoValidator
							extends
							Validator
{

	public CiaoValidator(String customErrorMessage)
	{

		super(customErrorMessage);

	}


	@Override
	public boolean isValid(EditText et)
	{

		return TextUtils.equals(et.getText(),
								"ciao");
	}

}
