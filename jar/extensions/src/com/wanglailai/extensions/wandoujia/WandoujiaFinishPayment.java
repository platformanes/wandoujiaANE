package com.wanglailai.extensions.wandoujia;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class WandoujiaFinishPayment implements FREFunction {
	public static final String KEY = "WandoujiaFinishPayment";
	private String TAG;
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		
		WandoujiaController.getInstance(context).finishPayment();
		
		return null;
	}

}
