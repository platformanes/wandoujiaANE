package com.wanglailai.extensions.wandoujia;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class WandoujiaInit implements FREFunction {
	public static final String KEY = "WandoujiaInit";
	private String TAG;
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		
		String appKey, seckey, appName;
		try {
			appKey = args[0].getAsString();
			seckey = args[1].getAsString();
			appName = args[2].getAsString();
			WandoujiaController.getInstance(ctx).init(appKey, seckey, appName);
		} catch (IllegalStateException e) {
			Log.w(TAG, e);
		} catch (FRETypeMismatchException e) {
			Log.w(TAG, e);
		} catch (FREInvalidObjectException e) {
			Log.w(TAG, e);
		} catch (FREWrongThreadException e) {
			Log.w(TAG, e);
		}
		return null;
	}

}
