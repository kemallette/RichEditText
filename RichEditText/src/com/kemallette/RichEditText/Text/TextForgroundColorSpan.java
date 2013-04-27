package com.kemallette.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;


/**
 * Color is set by constructor and is immutable.
 * Have to create a new span and apply it to change the fgcolor
 */
public class TextForgroundColorSpan extends ForegroundColorSpan
    implements ISpan{
    
    private int startPosition, endPosition, flag;
    private TextPaint mTextPaint;
    

    public TextForgroundColorSpan(final int startPostion,
        final int endPostion, final int flag, final int color) {

        super(color); // Foreground color span's constructor sets the color
        
        startPosition = startPostion;
        endPosition = endPostion;
        this.flag = flag;
        
        mTextPaint = new TextPaint();
    }
    

    @Override
    public void setSpan(final Editable e) {

        if (startPosition < 0)
            startPosition = 0;
        
        if (!(e.length() < startPosition))
            if (startPosition > endPosition)
                Log.e("SPAN",
                      "StartPosition was after End Position - couldn't set.");
            else
                e.setSpan(this, startPosition, endPosition, flag);
        else
            Log.e("SPAN",
                  "DID NOT SET: Start position past EditText length.");
    }
    

    public int getColor() {

        return super.getForegroundColor();
    }
    

    public void setColor(int color) {

        mTextPaint.bgColor = color;
        updateDrawState(mTextPaint);
        super.updateDrawState(mTextPaint);
    }
    

    @Override
    public int getStartPosition() {

        return startPosition;
    }
    

    @Override
    public int getEndPosition() {

        return endPosition;
    }
    

    @Override
    public void setStartPosition(final int startPos) {

        startPosition = startPos;
        
    }
    

    @Override
    public void setEndPosition(final int endPos) {

        endPosition = endPos;
    }
    

    @Override
    public void setFlag(final int flag) {

        this.flag = flag;
    }
    

    @Override
    public int getFlag() {

        return flag;
    }
    

    @Override
    public void setType(final int type) {

        // do nothing
    }
    

    @Override
    public int getType() {

        return FOREGROUND_COLOR;
    }
    

    @Override
    public boolean isStartInclusive() {

        if (flag == Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            || flag == Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            return true;
        else
            return false;
    }
    

    @Override
    public boolean isEndInclusive() {

        if (flag == Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            || flag == Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            return true;
        else
            return false;
    }
    

    @Override
    public void removeSpan(Editable text) {

        text.removeSpan(this);
    }
    

    @Override
    public void dump() {

        Span.dump(this);
    }
    

}
