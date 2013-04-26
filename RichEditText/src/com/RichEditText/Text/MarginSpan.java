package com.RichEditText.Text;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan.Standard;


public class MarginSpan extends Standard{
    
    public MarginSpan(int first, int rest) {

        super(first, rest);
    }
    

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x,
        int dir, int top, int baseline, int bottom,
        CharSequence text, int start, int end, boolean first,
        Layout layout) {

        // TODO Auto-generated method stub
        super.drawLeadingMargin(c, p, x, dir, top, baseline,
                                bottom, text, start, end, first,
                                layout);
    }
    
}
