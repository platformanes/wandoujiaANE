package com.wanglailai.extensions.wandoujia;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.wandoujia.sdk.plugin.paydef.LoginCallBack;
import com.wandoujia.sdk.plugin.paydef.PayCallBack;
import com.wandoujia.sdk.plugin.paydef.User;
import com.wandoujia.sdk.plugin.paydef.WandouAccount;
import com.wandoujia.sdk.plugin.paydef.WandouOrder;
import com.wandoujia.sdk.plugin.paydef.WandouPay;
import com.wandoujia.sdk.plugin.paysdkimpl.PayConfig;
import com.wandoujia.sdk.plugin.paysdkimpl.WandouAccountImpl;
import com.wandoujia.sdk.plugin.paysdkimpl.WandouPayImpl;
import com.wandoujia.wandoujiapaymentplugin.utils.MSG;
import com.wanglailai.extensions.PurchasedItem;
import com.wanglailai.extensions.UserProfile;

public class WandoujiaController {

	private static final int TRANSACTION_STATE_FAILED = 1;
	private static final int TRANSACTION_STATE_PURCHASED = 2;
	private static final int TRANSACTION_STATE_PURCHASING = 3;
	
	private static final String TAG = "com.wanglailai.extensions.wandoujia.WandoujiaController";
	private static final String PURCHASED_EVENT = "WdjExtensionEvent.TransactionsReceived";
	
	private static final String AUTHORIZED_EVENT = "WdjExtensionEvent.AuthorationReceived";
	
	private static final String AUTHENTICATE_SUCCESS = "SUCCESS";
	
	private static final String AUTHENTICATE_FAIL = "FAIL";
	
    
    private static WandoujiaController instance = null;
    
    public static WandoujiaController getInstance(FREContext context) {
    	if (instance == null) {
    		instance = new WandoujiaController(context);
    	}
    	Log.i(TAG, "instance()");
    	return instance;
    }
    
    public FREContext mContext;
    
    public Activity _activity;
    
	private UserProfile _user;
	
	public boolean inited = false;
	
    private ArrayList<PurchasedItem> mOwnedItems;
    private HashMap<String, PurchasedItem> mProcessedItems = new HashMap<String, PurchasedItem>();
    
    // 初始化支付实例
    private WandouPay wandoupay;
    // 初始化账户实例
    private WandouAccount account;
    protected WandoujiaController(FREContext context){
    	mContext = context;
    	_activity = context.getActivity();
    	
    	mOwnedItems = new ArrayList<PurchasedItem>();
    	
    	wandoupay = new WandouPayImpl();
    	account = new WandouAccountImpl();
    }
    
    private String _appName;
    public void init(String appkey_id, String seckey, String appName){
    	if(inited){
			return;
		}
    	_appName = appName;
    	PayConfig.init(_activity, appkey_id, seckey);
    	inited = true;
    }
    
    /**登陆豌豆荚平台*/
    public void login(){
    	account.doLogin(_activity, mLoginCallback);
    }
    
	public UserProfile getUser() {
		return _user;
	}
	
    /**
     * 使用豌豆荚支付接口
     */
    private PurchasedItem _currentItem;
    public void startPayment(String orderId, String productName, long amount) {
    	if(_user == null || _currentItem != null){
    		Log.w(TAG, "User is not login or another payment is not complete!!! Cannot start payment!");
    		return;
    	}
         // 三个参数分别是 游戏名(String)，商品(String)，价格(Long)单位是分
    	WandouOrder order = new WandouOrder(_appName, productName, amount);
    	order.out_trade_no = orderId;
    	_currentItem = new PurchasedItem(orderId, productName, (int)amount);
    	_currentItem.state = TRANSACTION_STATE_PURCHASING;
    	wandoupay.pay(_activity, order, mPayCallback);
    }
    
	@SuppressWarnings("unchecked")
	public ArrayList<PurchasedItem> getPurchaseItems() {
		ArrayList<PurchasedItem> copiedItems;

		synchronized (mOwnedItems) {
			copiedItems = (ArrayList<PurchasedItem>) mOwnedItems.clone();
			mOwnedItems.clear();
		}
		Log.i(TAG, "getPurchasedItems read " + copiedItems.size() + " items.");
		
		return copiedItems;
	}
	
	public void finishPayment(){
		//add finish code
	}
	
	public void dispose(){
		WandoujiaController.instance = null;
	}
	
    //--------------调用支付接口--------------
    // 支付的回调
    private PayCallBack mPayCallback = new PayCallBack() {

		@Override
		public void onError(User user, WandouOrder order) {
			Log.w(TAG, "onError:"+user.getNick() + "Trade Failed:" + order);
			_currentItem.state = TRANSACTION_STATE_FAILED;
			if(order != null){
				_currentItem.platformPayload = order.toJSONString();
			}
			dispatchEvent();
		}

		@Override
		public void onSuccess(User user, WandouOrder order) {
			Log.i(TAG, "onSuccess:" + order + " status:" + order.status(WandouOrder.TRADE_SUCCESS));
			_currentItem.state = TRANSACTION_STATE_PURCHASED;
			dispatchEvent();
		}
		
		private void dispatchEvent(){
			Boolean raiseEvent = false;
			 synchronized(mOwnedItems) {
				 if (!mProcessedItems.containsKey(_currentItem.transactionID)) {
              		mProcessedItems.put(_currentItem.transactionID, _currentItem);
                      mOwnedItems.add(_currentItem);
                      raiseEvent = true;
          		}
			 }

        	 if(raiseEvent) {
                 mContext.dispatchStatusEventAsync(WandoujiaController.PURCHASED_EVENT, "");
                 Log.i(TAG, "PayCallBack() PURCHASED_EVENT raised");
         	}else{
                 Log.i(TAG, "PayCallBack() already processed: " + _currentItem.transactionID);
         	}
        	 _currentItem = null;
		}
    };
    
    //豌豆荚 登录回调
    private LoginCallBack mLoginCallback = new LoginCallBack(){
		@Override
		public void onError(int code, String info) {
			Log.e(TAG, info + "\nmessage:" + MSG.trans(info));
			mContext.dispatchStatusEventAsync(AUTHORIZED_EVENT, AUTHENTICATE_FAIL);
		}

		@Override
		public void onSuccess(User user, int type) {
			Log.i(TAG, "user info:" + user.toString() + "\ntype:" + type);
			_user = new UserProfile();
			_user.uid = user.getUid() + "";
			_user.name = user.getUsername();
			_user.token = user.getToken();
			_user.nick = user.getNick();
			mContext.dispatchStatusEventAsync(AUTHORIZED_EVENT, AUTHENTICATE_SUCCESS);
		}
    	
    };
    
}
