package com.kemallette.RichEditText.ColorPicker;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

public class ColorPickerPrefWidgetView	extends
										View{

	private Paint	paint;
	private float	rectSize;
	private float	strokeWidth;


	public ColorPickerPrefWidgetView(	Context context,
										AttributeSet attrs){

		super(	context,
				attrs);

		float density = context.getResources()
								.getDisplayMetrics().density;
		rectSize = FloatMath.floor(24.f * density + 0.5f);
		strokeWidth = FloatMath.floor(1.f * density + 0.5f);

		paint = new Paint();
		paint.setColor(0xffffffff);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(strokeWidth);
	}


	@Override
	protected void onDraw(Canvas canvas){

		super.onDraw(canvas);

		canvas.drawRect(strokeWidth,
						strokeWidth,
						rectSize
							- strokeWidth,
						rectSize
							- strokeWidth,
						paint);
	}
}
