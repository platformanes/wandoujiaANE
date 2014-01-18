package com.wanglailai.extensions
{
	public class WdjPurchaseItem
	{
		public var productName:String;
		public var transactionID:String;
		public var price:int;
		public var state:int;
		public var wdjPayload:String;
		
		public function WdjPurchaseItem(productName:String, transactionID:String, price:int, state:int, wdjPayload:String)
		{
			this.productName = productName;
			this.transactionID = transactionID;
			this.price = price;
			this.state = state;
			this.wdjPayload = wdjPayload;
		}
	}
}