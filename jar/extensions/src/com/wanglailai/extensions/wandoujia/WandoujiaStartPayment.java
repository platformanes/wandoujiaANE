package com.wanglailai.extensions.wandoujia;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class WandoujiaStartPayment implements FREFunction {
	public static final String KEY = "WandoujiaStartPayment";
	private String TAG;
	
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		String orderId, productName;
		int amount;
		try {
			orderId = args[0].getAsString();
			amount = args[1].getAsInt();
			productName = args[2].getAsString();
			WandoujiaController.getInstance(context).startPayment(orderId, productName, amount);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
