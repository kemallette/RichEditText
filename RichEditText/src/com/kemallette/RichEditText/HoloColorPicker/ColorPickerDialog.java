package com.kemallette.RichEditText.HoloColorPicker;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kemallette.RichEditText.R;
import com.kemallette.RichEditText.HoloColorPicker.ColorPicker.OnColorChangedListener;


public class ColorPickerDialog	extends
								AlertDialog.Builder	implements
													OnClickListener{

	private int						oldColor;

	private ColorPicker				picker;
	private SVBar					svBar;
	private OpacityBar				opacityBar;
	private SaturationBar			satBar;
	private OnColorChangedListener	mColorChangeListener;


	public ColorPickerDialog(	Context context,
								int theme){

		super(	context,
				theme);
		initLayout(context);
	}


	public ColorPickerDialog(Context context){

		super(context);

		initLayout(context);

	}


	public ColorPickerDialog(	Context context,
								OnColorChangedListener mColorChangeListener,
								int currentColor){

		this(context);

		oldColor = currentColor;
		this.mColorChangeListener = mColorChangeListener;
	}


	private void initLayout(Context context){

		ViewGroup mLayout = (ViewGroup) LayoutInflater.from(context)
														.inflate(	R.layout.color_picker_dialog,
																	null);

		setView(mLayout);

		picker = (ColorPicker) mLayout.findViewById(R.id.picker);
		svBar = (SVBar) mLayout.findViewById(R.id.svBar);
		opacityBar = (OpacityBar) mLayout.findViewById(R.id.opacityBar);
		satBar = (SaturationBar) mLayout.findViewById(R.id.saturationBar);

		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.addSaturationBar(satBar);

		picker.setOnColorChangedListener(mColorChangeListener);

		setOldColor(oldColor);

		setNegativeButton(	"Cancel",
							null);
		setPositiveButton(	"OK",
							this);
	}


	@Override
	public void onClick(DialogInterface arg0, int arg1){

		mColorChangeListener.onColorChanged(picker.getColor());
	}


	public void setOldColor(int oldColor){

		picker.setOldCenterColor(oldColor);
		picker.updatePositions();
	}


}
