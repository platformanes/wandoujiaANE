package com.wanglailai.extensions;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class PurchasedItem {
	public String productName;
	public String transactionID;
	public int price;
	public int state;
	public String platformPayload;
	
	public PurchasedItem(String orderId, String productName, int amount)
	{
		this.transactionID = orderId;
		this.productName = productName;
		this.price = amount;
	}
	
	public FREObject toWdjPurchaseItem() throws 
		FREWrongThreadException, IllegalStateException, 
		FRETypeMismatchException, FREInvalidObjectException, 
		FREASErrorException, FRENoSuchNameException {
		
        FREObject[] args = {
        		FREObject.newObject(this.productName),
        		FREObject.newObject(this.transactionID),
        		FREObject.newObject(this.price),
        		FREObject.newObject(this.state),
        		FREObject.newObject(this.platformPayload)
        };
        return FREObject.newObject("com.wanglailai.extensions.WdjPurchaseItem", args);
	}
}