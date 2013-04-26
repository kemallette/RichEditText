package com.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;
import android.util.Log;


public class UnorderedListMarginSpan extends MarginSpan
    implements ISpan{
    
    float density;
    private int startPosition, endPosition, marginSizeFirst,
        marginSizeRest;
    

    public UnorderedListMarginSpan(int start, int end,
        float density, int marginSizeFirst, int marginSizeRest) {

        super(marginSizeFirst, marginSizeRest);
        
        this.density = density;
        this.marginSizeFirst = marginSizeFirst;
        this.marginSizeRest = marginSizeRest;
    }
    

    @Override
    public int getLeadingMargin(boolean first) {

        if (first)
            return (int) density * marginSizeFirst;
        else
            return (int) density * marginSizeRest;
    }
    

    @Override
    public void setSpan(Editable e) {

        if (startPosition < 0)
            startPosition = 0;
        
        if (startPosition > endPosition)
            Log.e("SPAN",
                  "StartPosition was after End Position - couldn't set.");
        else
            Span.setSpan(this, e, startPosition, endPosition,
                         Spanned.SPAN_PARAGRAPH);
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
    public void setStartPosition(int startPos) {

        this.startPosition = startPos;
        
    }
    

    @Override
    public void setEndPosition(int endPos) {

        this.endPosition = endPos;
    }
    

    @Override
    public void setFlag(int flag) {

        
    }
    

    @Override
    public int getFlag() {

        return Spanned.SPAN_PARAGRAPH;
        
    }
    

    @Override
    public void setType(int type) {

        
    }
    

    @Override
    public int getType() {

        return SpanTypes.LEADING_MARGIN_UL;
    }
    

    @Override
    public void removeSpan(Editable text) {

        text.removeSpan(this);
    }
    

    @Override
    public void dump() {

        Span.dump(this);
    }
    

    @Override
    public boolean isStartInclusive() {

        // TODO Auto-generated method stub
        return false;
    }
    

    @Override
    public boolean isEndInclusive() {

        // TODO Auto-generated method stub
        return false;
    }
    
}
