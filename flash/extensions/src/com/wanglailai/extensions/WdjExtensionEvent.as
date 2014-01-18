package com.wanglailai.extensions
{
	import flash.events.Event;
	
	public class WdjExtensionEvent extends Event
	{	
		/**Platform authenticated or logined level: SUCCESS*/
		public static const AUTHENTICATE_SUCCESS:String = "SUCCESS";
		
		/**Platform not authenticated or login failed level: FAIL*/
		public static const AUTHENTICATE_FAIL:String = "FAIL";
		
		public static const TRANSACTIONS_RECEIVED:String = "WdjExtensionEvent.TransactionsReceived";
		public static const AUTHORATION_RECEIVED:String = "WdjExtensionEvent.AuthorationReceived";
		
		public var transactions:Array = [];
		public var data:Object;
		
		public function WdjExtensionEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event
		{
			var e:WdjExtensionEvent = new WdjExtensionEvent(type, bubbles, cancelable);
			e.transactions = transactions;
			e.data = data;
			return e;
		}
	}
}