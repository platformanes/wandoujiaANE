package com.wanglailai.extensions;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class UserProfile {
	
	public String uid;
	public String name;
	public String nick;
	public String token;
	
    public FREObject toWdjUser() throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, FREReadOnlyException{
    	FREObject result = FREObject.newObject("com.wanglailai.extensions.WdjUser", null);
    	result.setProperty("uid", FREObject.newObject(uid));
    	result.setProperty("name", FREObject.newObject(name));
    	result.setProperty("nick", FREObject.newObject(nick));
    	result.setProperty("token", FREObject.newObject(token));
        return result;
   }
    
}