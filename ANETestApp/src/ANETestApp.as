package
{
	import com.wanglailai.extensions.WdjExtensionContext;
	import com.wanglailai.extensions.WdjExtensionEvent;
	import com.wanglailai.extensions.WdjTransaction;
	import com.wanglailai.extensions.WdjUser;
	import com.wanglailai.wdjTestPanel;
	
	import fl.controls.Button;
	
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	public class ANETestApp extends Sprite
	{
		private var loginBtn:Button;
		private var purchaseBtn:Button;
		
		private var userTxt:TextField;
		private var logTxt:TextField;
		
		public function ANETestApp()
		{
			super();
			
			// support autoOrients
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			initPanel();
			initWdjSDK();
		}
		
		private function initPanel():void
		{
			var panel:wdjTestPanel = new wdjTestPanel();
			panel.scaleX = panel.scaleY = 2.0;
			panel.x = (stage.stageWidth - panel.width) >> 1;
			panel.y = 0;
			addChild(panel);
			
			loginBtn = panel.loginBtn;
			loginBtn.addEventListener(MouseEvent.CLICK, onLoginPressed);
			userTxt = new TextField();
			userTxt.wordWrap = true;
			userTxt.width = 300;
			userTxt.height = 50;
			userTxt.x = loginBtn.x - 150; userTxt.y = loginBtn.y + 20;
			panel.addChild(userTxt);
			
			purchaseBtn = panel.purchaseBtn;
			purchaseBtn.addEventListener(MouseEvent.CLICK, onPurchasePressed);
			logTxt = new TextField();
			logTxt.multiline = true;
			logTxt.wordWrap = true;
			logTxt.width = 300;
			logTxt.height = 400;
			logTxt.x = userTxt.x; 
			logTxt.y = purchaseBtn.y + 20;
			panel.addChild(logTxt);
		}
		
		private function initWdjSDK():void
		{
			var appkey_id:String = "100000000";
			// 开发者 安全秘钥
			var secretkey:String = "99b4efb45d49338573a00be7a1431511";
			var appName:String = "豌豆荚SDK AIR版DEMO";
			WdjExtensionContext.instance.init(appkey_id, secretkey, "appName");
			WdjExtensionContext.instance.addEventListener(WdjExtensionEvent.AUTHORATION_RECEIVED, onLoginCallback);
			WdjExtensionContext.instance.addEventListener(WdjExtensionEvent.TRANSACTIONS_RECEIVED, onPurchaseCallback);
		}
		
		protected function onPurchaseCallback(evt:WdjExtensionEvent):void
		{
			outputLog("支付回调信息，返回支付信息数目：" + evt.transactions.length.toString());
			for each (var t:WdjTransaction in evt.transactions)
			{
				if (t.state == WdjTransaction.TRANSACTION_STATE_PURCHASED)
				{// 购买成功的
					//TODO: 通知服务器，进行后台支付信息校验
					outputLog("支付成功，通知应用服务器发货等相关操作！");
				}
				else if (t.state == WdjTransaction.TRANSACTION_STATE_FAILED)
				{
					outputLog("购买失败！");
				}
			}
		}
		
		protected function onLoginCallback(evt:WdjExtensionEvent):void
		{
			if (evt.data == WdjExtensionEvent.AUTHENTICATE_SUCCESS)
			{// 登录成功
				outputLog("登录豌豆荚帐号成功，获取用户信息");
				var user:WdjUser = WdjExtensionContext.instance.getProfile();
				if (user != null)
				{
					outputLog("获得用户信息成功!\n uid=" + user.uid + ", name=" + user.name + ", nick=" + user.nick + ", token=" + user.token);
					userTxt.text = "用户信息：uid=" + user.uid + ", name=" + user.name + ", nick=" + user.nick + ", token=" + user.token;
				}else{
					outputLog("获取用户信息失败！请检查代码或联系豌豆荚负责SDK的同学");
				}
			}
			else
			{
				outputLog("登录豌豆荚帐号失败！");
			}
		}
		
		private function onLoginPressed(evt:MouseEvent):void
		{
			WdjExtensionContext.instance.login();
		}
		
		private function onPurchasePressed(evt:MouseEvent):void
		{
			//TODO: orderID应该是由应用服务器生成
			var now:Date = new Date();
			var orderID:String = "orderGeneratedByYourWebServer" + now.time;
			// 价格的单位是分
			var price:int = 1;
			
			var productName:String = "测试商品";
			
			WdjExtensionContext.instance.startPayment(orderID, price, productName);
		}
		
		private function outputLog(message:String):void
		{
			logTxt.htmlText += message + "<br/>";
			trace(message);
		}
	}
}