package com.wanglailai.extensions.wandoujia;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class WandoujiaLogin implements FREFunction {

	public static final String KEY = "WandoujiaLogin";
	private String tag;

	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		tag = ctx.getIdentifier() + "." + KEY;
		Log.i(tag, "Invoked " + KEY);
		
		WandoujiaController.getInstance(ctx).login();
		return null;
	}

}
