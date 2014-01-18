package com.wanglailai.extensions.wandoujia;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class WandoujiaSupported implements FREFunction {
	public static final String KEY = "WandoujiaSupported";
	private String TAG;
	
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Boolean iapSupported = WandoujiaController.getInstance(ctx).inited; 
		Log.i(TAG, "Invoked " + KEY + "\nvalue:" + iapSupported.toString());
		FREObject result;
		try {
			result = FREObject.newObject(iapSupported);
		} catch (FREWrongThreadException e) {
			Log.w(TAG, e);
			result = null;
		}
		return result;
	}

}
