package com.kemallette.RichEditTextDemo.Activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.kemallette.RichEditTextDemo.R;


public class EditingExampleActivity	extends
									SherlockActivity implements
													OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);

		setContentView(R.layout.editing_example_activity);
	}


	@Override
	public void onClick(View mView){

		int id = mView.getId();

		switch(id){
		}

	}

}
