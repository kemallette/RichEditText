package com.kemallette.RichEditText.Widget;


import android.text.DynamicLayout;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;


public class DynLayout extends DynamicLayout{
    
    public DynLayout(CharSequence base, CharSequence display,
        TextPaint paint, int width, Alignment align,
        float spacingmult, float spacingadd, boolean includepad) {

        super(base, display, paint, width, align, spacingmult,
              spacingadd, includepad);
    }
    

    public DynLayout(CharSequence base, CharSequence display,
        TextPaint paint, int width, Alignment align,
        float spacingmult, float spacingadd, boolean includepad,
        TruncateAt ellipsize, int ellipsizedWidth) {

        super(base, display, paint, width, align, spacingmult,
              spacingadd, includepad, ellipsize, ellipsizedWidth);
    }
    

    public DynLayout(CharSequence base, TextPaint paint,
        int width, Alignment align, float spacingmult,
        float spacingadd, boolean includepad) {

        super(base, paint, width, align, spacingmult,
              spacingadd, includepad);
    }
    

}
