/**
 * 
 */
package com.RichEditText.Text;


import android.text.Editable;
import android.text.Spanned;
import android.util.Log;

public abstract class Span implements ISpan{
    
    
    int startPosition, endPosition, type,
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    

    public Span(final int startPosition, final int endPosition,
        final int type, final int flag) {

        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.type = type;
        this.flag = flag;
    }
    

    public static void setSpan(final Object span,
        final Editable e, int startPosition, int endPosition,
        int flag) {

        if (startPosition < 0)
            startPosition = 0;
        
        if (!(e.length() < startPosition))
            if (startPosition > endPosition)
                Log.e("SPAN",
                      "StartPosition was after End Position - couldn't set.");
            else
                e.setSpan(span, startPosition, endPosition, flag);
        else
            Log.e("SPAN",
                  "DID NOT SET: Start position past EditText length.");
        
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

        this.flag = flag;
    }
    

    @Override
    public int getFlag() {

        return flag;
    }
    

    @Override
    public void setType(int type) {

        this.type = type;
    }
    

    @Override
    public int getType() {

        return type;
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
    

    /***********************************************************
     * 
     * DEBUG
     * 
     ************************************************************/
    public static void dump(ISpan mSpan) {

        String type = null, flag = null;
        
        Log.i("EDITOR", "\n\n----------------------------\n");
        Log.i("EDITOR", "Start: " + mSpan.getStartPosition());
        Log.i("EDITOR", "End: " + mSpan.getEndPosition());
        switch(mSpan.getType()){
            case BOLD:
                type = "Bold";
                break;
            case ITALIC:
                type = "Italic";
                break;
            case BOLD_ITALIC:
                type = "Bold_Italic";
                break;
            case NORMAL:
                type = "Normal";
                break;
            case STRIKE:
                type = "Strike";
                break;
            case UNDERLINE:
                type = "Underline";
                break;
        }
        
        switch(mSpan.getFlag()){
            case Spanned.SPAN_EXCLUSIVE_EXCLUSIVE:
                flag = "EXCLUSIVE_EXCLUSIVE";
                break;
            case Spanned.SPAN_EXCLUSIVE_INCLUSIVE:
                flag = "EXCLUSIVE_INCLUSIVE";
                break;
            case Spanned.SPAN_INCLUSIVE_EXCLUSIVE:
                flag = "INCLUSIVE_EXCLUSIVE";
                break;
            case Spanned.SPAN_INCLUSIVE_INCLUSIVE:
                flag = "INCLUSIVE_INCLUSIVE";
                break;
        }
        Log.i("EDITOR", "Flag: " + flag);
        Log.i("EDITOR", "Type: " + type);
    }
}