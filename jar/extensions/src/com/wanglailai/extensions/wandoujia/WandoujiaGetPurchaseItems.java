package com.wanglailai.extensions.wandoujia;

import java.util.ArrayList;

import android.util.Log;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.wanglailai.extensions.PurchasedItem;

public class WandoujiaGetPurchaseItems implements FREFunction {
	public static final String KEY = "WandoujiaGetPurchaseItems";
	private String TAG;

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		WandoujiaContext ctx = (WandoujiaContext) context;
		TAG = ctx.getIdentifier() + "." + KEY;
		Log.i(TAG, "Invoked " + KEY);
		try {
			ArrayList<PurchasedItem> items = WandoujiaController.getInstance(ctx).getPurchaseItems();
			int itemCount = items.size();
			
			FREArray result;
			result = FREArray.newArray(itemCount);
			for (int i = 0; i < itemCount; i++) {
				PurchasedItem item = items.get(i);
				result.setObjectAt(i, item.toWdjPurchaseItem());
			}
			Log.w(TAG, "Processed list of purchased items.");
			return result;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FREASErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FRENoSuchNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
