package com.kemallette.RichEditText.ColorPicker;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.kemallette.RichEditText.R;

public class ColorPickerAlpha	extends
								View{

	// private Paint paint, colorPaint;
	// private Shader luar;
	private int[]				gradientColors;
	private GradientDrawable	gradientDrawable;
	private BitmapDrawable		checkers;


	public ColorPickerAlpha(Context context,
							AttributeSet attrs){

		super(	context,
				attrs);
		initialize();
	}


	public ColorPickerAlpha(Context context,
							AttributeSet attrs,
							int defStyle){

		super(	context,
				attrs,
				defStyle);
		initialize();
	}


	private void initialize(){

		setColor(0);
		checkers = (BitmapDrawable) getResources().getDrawable(R.drawable.color_picker_checkers);
		checkers.setTileModeX(Shader.TileMode.REPEAT);
		checkers.setTileModeY(Shader.TileMode.REPEAT);
	}


	public void setColor(int color){

		if (gradientColors == null)
			gradientColors = new int[] { color & 0x00FFFFFF,
								color | 0xFF000000 };
		else{
			gradientColors[0] = color & 0x00FFFFFF;
			gradientColors[1] = color | 0xFF000000;
		}

		if (gradientDrawable == null
			|| Build.VERSION.SDK_INT < 16)
			gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
													gradientColors);
		else
			gradientDrawable.setColors(gradientColors);

		invalidate();
	}


	@Override
	protected void onDraw(Canvas canvas){

		super.onDraw(canvas);

		checkers.setBounds(	0,
							0,
							getMeasuredWidth(),
							getMeasuredHeight());
		checkers.draw(canvas);
		gradientDrawable.setBounds(	0,
									0,
									getMeasuredWidth(),
									getMeasuredHeight());
		gradientDrawable.draw(canvas);

		/*
		 * if (paint == null) { paint = new Paint();
		 * paint.setShader(mHeightShader); mHeightShader = new
		 * LinearGradient(0.f, 0.f, 0.f, this.getMeasuredHeight(), 0xffffffff,
		 * 0x00ffffff, TileMode.CLAMP); paint.setXfermode(new
		 * PorterDuffXfermode(Mode.DST_IN)); colorPaint = new Paint();
		 * colorPaint.setColor(gradientColors); } canvas.drawRect(0.f, 0.f,
		 * this.getMeasuredWidth(), this.getMeasuredHeight(), colorPaint);
		 * canvas.drawRect(0.f, 0.f, this.getMeasuredWidth(),
		 * this.getMeasuredHeight(), paint);
		 */
	}
}
