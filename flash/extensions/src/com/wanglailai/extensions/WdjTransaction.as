package com.wanglailai.extensions
{
	public class WdjTransaction
	{
		public static const TRANSACTION_STATE_FAILED:int = 1;
		public static const TRANSACTION_STATE_PURCHASED:int = 2;
		public static const TRANSACTION_STATE_PURCHASING:int = 3;
		
		public var receipt:String;
		public var transactionID:String;
		public var state:int;
		public var productName:String;
	}
}