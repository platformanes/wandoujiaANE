package com.wanglailai.extensions
{
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;

	public class WdjExtensionContext extends EventDispatcher
	{
		private static const EXTENSION_ID : String = "com.wanglailai.ane4wdj";
		private static var _instance:WdjExtensionContext;
		private var _context:ExtensionContext;
		
		private var _authenticated:Boolean = false;
		
		private var _usedInAndroid:Boolean = false;
		
		public function WdjExtensionContext()
		{
			_context = ExtensionContext.createExtensionContext(EXTENSION_ID, null);
			_context.addEventListener(StatusEvent.STATUS, _onStatus);
			
			var mfg:String = Capabilities.manufacturer;
			trace("WdjExtensionContext::Platform - Manufacturer: " + mfg);
			if (mfg.indexOf("Android") > -1)
			{
				_usedInAndroid = true;
			}
		}
		
		public static function get instance():WdjExtensionContext
		{
			if (!_instance)
				_instance = new WdjExtensionContext();
			return _instance;
		}

		/**
		 *初始化豌豆荚SDK
		 */
		public function init(appKey:String, seckey:String, appName:String):void
		{
			if(_usedInAndroid)
				_context.call("WandoujiaInit", appKey, seckey, appName);
		}
		
		/**
		 * 调用登录接口
		 */
		public function login():void
		{
			if(_usedInAndroid)
				_context.call("WandoujiaLogin");
		}
		
		/**
		 * 登录成功的标识
		 */
		public function get authenticated():Boolean
		{
			return _authenticated;
		}
		
		/**
		 * 获得登录用户的信息
		 */
		public function getProfile():WdjUser
		{
			if(!authenticated){
				return null;
			}
			return _context.call("WandoujiaGetProfile") as WdjUser;
		}
		
		/**豌豆荚平台使用该方法发起支付*/
		public function startPayment(orderID:String, price:uint, productName:String):void
		{
			if(_usedInAndroid)
			{
				_context.call("WandoujiaStartPayment", orderID, price, productName);
				trace('WandoujiaPlatform.startPayment:' + orderID +","+ price + "," + productName);
			}
		}

		private function _onStatus(event : StatusEvent ):void{
			trace('WandoujiaPlatform got status event code:' + event.code + '; level:' + event.level);
			switch(event.code){
				case WdjExtensionEvent.AUTHORATION_RECEIVED:
					if(event.level == WdjExtensionEvent.AUTHENTICATE_SUCCESS){
						_authenticated = true;
					}
					var authEvt:WdjExtensionEvent = new WdjExtensionEvent(WdjExtensionEvent.AUTHORATION_RECEIVED);
					authEvt.data = event.level;
					dispatchEvent(authEvt);
					break;
				case WdjExtensionEvent.TRANSACTIONS_RECEIVED:
					var result:Array = [];
					var items:Array = _context.call("WandoujiaGetPurchaseItems") as Array;
					trace('Got' + (items ? items.length : 'null', 'purchase items'));
					for each (var t:WdjPurchaseItem in items) {
						var trans:WdjTransaction = new WdjTransaction();
						trans.transactionID = t.transactionID;
						trans.state = t.state;
						trans.receipt = t.wdjPayload;
						trans.productName = t.productName;
						result.push(trans);
					}
					if (result.length > 0 && hasEventListener(WdjExtensionEvent.TRANSACTIONS_RECEIVED)) {
						var evt:WdjExtensionEvent = new WdjExtensionEvent(WdjExtensionEvent.TRANSACTIONS_RECEIVED);
						evt.transactions = result;
						dispatchEvent(evt);
					}
					break;
			}
		}
	}
}