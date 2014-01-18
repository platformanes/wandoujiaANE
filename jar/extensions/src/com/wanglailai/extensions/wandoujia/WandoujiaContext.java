package com.wanglailai.extensions.wandoujia;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class WandoujiaContext extends FREContext {
	public static final String KEY = "Wandoujia";
	private Map<String, FREFunction> functionMap=null;
	private String tag;
	
	public WandoujiaContext(){
		tag = getClass().getName();
		Log.i(tag, "Creating context"); 
	}
	@Override
	public Map<String, FREFunction> getFunctions() {
		Log.i(tag, "getFunctions");
		
		functionMap = new HashMap<String, FREFunction>();
        //In-app purchasing stuff
  		functionMap.put(WandoujiaInit.KEY, new WandoujiaInit());
  		functionMap.put(WandoujiaLogin.KEY, new WandoujiaLogin());
  		functionMap.put(WandoujiaGetProfile.KEY, new WandoujiaGetProfile());
  		functionMap.put(WandoujiaStartPayment.KEY, new WandoujiaStartPayment());
  		functionMap.put(WandoujiaGetPurchaseItems.KEY, new WandoujiaGetPurchaseItems());
		functionMap.put(WandoujiaFinishPayment.KEY, new WandoujiaFinishPayment());
  		functionMap.put(WandoujiaSupported.KEY, new WandoujiaSupported());
		return functionMap;
	}
	
	@Override
	public void dispose() {
		Log.d(tag, "WandoujiaContext disposed!");
	}
	
	public String getIdentifier() { 
		return tag; 
	}

}
