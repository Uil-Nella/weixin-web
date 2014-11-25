package org.cs.demo.service;

import org.cs.demo.pojo.AccessToken;
import org.cs.demo.pojo.Button;
import org.cs.demo.pojo.CommonButton;
import org.cs.demo.pojo.ComplexButton;
import org.cs.demo.pojo.Menu;
import org.cs.demo.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 菜单管理器类
 * 
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wxd1d2ea9d372e97a4";
		// 第三方用户唯一凭证密钥
		String appSecret = "c3f46be8c5749e13cabf451279f8717b";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
//		btn11.setName("我的成绩");
//		btn11.setType("view");
//		btn11.setUrl("http://202.206.16.125/ACTIONLOGON.APPPROCESS?mode=1&applicant=ACTIONQUERYTEACHERSCHEDULEBYSELF");

		CommonButton btn12 = new CommonButton();
		btn12.setName("我的课表");
		btn12.setType("click");
		btn12.setKey("12");

//		CommonButton btn13 = new CommonButton();
//		btn13.setName("我要自习");
//		btn13.setType("click");
//		btn13.setKey("13");

//		CommonButton btn14 = new CommonButton();
//		btn14.setName("我的图书");
//		btn14.setType("click");
//		btn14.setKey("14");
		


		CommonButton btn21 = new CommonButton();
		btn21.setName("校园资讯");
		btn21.setType("click");
		btn21.setKey("21");
		
		CommonButton btn22 = new CommonButton();
		btn22.setName("跳骚市场");
		btn22.setType("click");
//		btn22.setUrl("http://goodsswitch.duapp.com");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("表白墙");
		btn23.setType("view");
		btn23.setUrl("http://182.92.79.92/NEUQ_Helper/showexpressaction.action");
		
		CommonButton btn24 = new CommonButton();
		btn24.setName("精彩世界杯");
		btn24.setType("view");
		btn24.setUrl("http://182.92.79.92/NEUQ_Helper/wordcupaction!show_news.action");

//		CommonButton btn23 = new CommonButton();
//		btn23.setName("教务通知");
//		btn23.setType("click");
//		btn23.setKey("23");
//
//		CommonButton btn24 = new CommonButton();
//		btn24.setName("就业信息");
//		btn24.setType("click");
//		btn24.setKey("24");

//		CommonButton btn25 = new CommonButton();
//		btn25.setName("意见建议");
//		btn25.setType("click");
//		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("绑定");
		btn31.setType("click");
		btn31.setKey("31");

//		CommonButton btn32 = new CommonButton();
//		btn32.setName("解除绑定");
//		btn32.setType("click");
//		btn32.setKey("32");
//
//		CommonButton btn33 = new CommonButton();
//		btn33.setName("个人中心");
//		btn33.setType("click");
//		btn33.setKey("33");
		
//		CommonButton btn34 = new CommonButton();
//		btn33.setName("校园导航");
//		btn33.setType("click");
//		btn33.setKey("34");
//		
//		CommonButton btn35 = new CommonButton();
//		btn33.setName("掌上东秦");
//		btn33.setType("click");
//		btn33.setKey("35");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("学在东秦");
		mainBtn1.setSub_button(new CommonButton[] {  btn12});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("玩在东秦");
		mainBtn2.setSub_button(new CommonButton[] { btn21, btn22 ,btn23 ,btn24});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("个人信息");
		mainBtn3.setSub_button(new CommonButton[] { btn31});

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
