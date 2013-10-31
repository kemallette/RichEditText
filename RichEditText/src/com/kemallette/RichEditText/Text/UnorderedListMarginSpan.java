package com.kemallette.RichEditText.Text;


import android.text.Spanned;


public class UnorderedListMarginSpan extends
									MarginSpan
												implements
												IParagraphSpan{

	float				density;
	private final int	marginSizeFirst;
	private final int	marginSizeRest;


	public UnorderedListMarginSpan(	final int start,
									final int end,
									final float density,
									final int marginSizeFirst,
									final int marginSizeRest){

		super(	marginSizeFirst,
				marginSizeRest);

		this.density = density;
		this.marginSizeFirst = marginSizeFirst;
		this.marginSizeRest = marginSizeRest;
	}


	@Override
	public int getLeadingMargin(final boolean first){

		if (first)
			return (int) density
					* marginSizeFirst;
		else
			return (int) density
					* marginSizeRest;
	}


	@Override
	public void setFlag(final int flag){


	}


	@Override
	public int getFlag(){

		return Spanned.SPAN_PARAGRAPH;

	}


	@Override
	public void setType(final int type){


	}


	@Override
	public int getType(){

		return SpanTypes.LEADING_MARGIN_UL;
	}


}
