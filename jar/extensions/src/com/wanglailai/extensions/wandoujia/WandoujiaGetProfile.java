package com.wanglailai.extensions.wandoujia;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.wanglailai.extensions.UserProfile;

public class WandoujiaGetProfile implements FREFunction {

	public static final String KEY = "WandoujiaGetProfile";

	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		UserProfile user = WandoujiaController.getInstance(context).getUser();
		if(user != null){
			FREObject result;
			try {
				result = user.toWdjUser();
				return result;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FREASErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FRENoSuchNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FREReadOnlyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
