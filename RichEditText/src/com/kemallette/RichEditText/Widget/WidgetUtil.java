package com.kemallette.RichEditText.Widget;


import java.util.Hashtable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;

import com.kemallette.RichEditText.R;


public class WidgetUtil{

	private static final String	                     TAG	  = "WidgetUtil";

	private static final Hashtable<String, Typeface>	cache	=
	                                                              new Hashtable<String, Typeface>();


	public static Typeface get(Context c, String name){

		String extension = ".ttf";

		if (name == null)
			Log.e(TAG,
			      "typeface name was null");

		synchronized (cache){
			if (!cache.containsKey(name)){

				Resources res = c.getResources();
				String[] otfFonts = res.getStringArray(R.array.otf_fonts);

				for (String font : otfFonts){
					if (name.equals(font)){
						extension = ".otf";
					}else{
						extension = ".ttf";
					}
				}


				Log.i(TAG,
				      "fontNameAndExtension: "
				          + String.format("%s" + extension,
				                          name));

				Typeface t =
				             Typeface.createFromAsset(c.getAssets(),
				                                      String.format("fonts/%s"
				                                                        + extension,
				                                                    name));
				cache.put(name,
				          t);
			}
		}
		return cache.get(name);
	}


	public static float getScreenDensity(Context ctx){

		return ctx.getResources()
		          .getDisplayMetrics().density;
	}


	public static int convertToDips(Context ctx, float pixels){

		// Get the screen's density scale
		final float scale = ctx.getResources()
		                       .getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}
}
