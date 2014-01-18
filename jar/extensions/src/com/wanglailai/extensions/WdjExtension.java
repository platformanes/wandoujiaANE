package com.wanglailai.extensions;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;
import com.wanglailai.extensions.wandoujia.WandoujiaContext;

public class WdjExtension implements FREExtension {

	private static final String EXT_NAME = "com.wanglailai.extensions"; 
	private FREContext context; 
	private String tag = EXT_NAME + ".WdjExtension"; 
	
	@Override
	public FREContext createContext(String contextKey) {
		Log.i(tag, "Creating context:" + contextKey); 
		context = new WandoujiaContext();
		return context;
	}

	@Override
	public void dispose() {
		Log.i(tag, "Disposing extension"); 
		context.dispose();
		context = null;
	}

	@Override
	public void initialize() {
		Log.i(tag, "Initialize"); 
		
		// Disable strict mode so we can use the filesystem on the main thread and generally be bad citizens.
		if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			Log.i(tag, "Set StrictMode permitAll()");
		}
	}

}
