package org.cs.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cs.demo.message.resp.Article;
import org.cs.demo.message.resp.NewsMessage;
import org.cs.demo.message.resp.TextMessage;
import org.cs.demo.util.MessageUtil;
import org.cs.demo.util.WeekNumUtil;

/**
 * 核心服务类
 */
public class NEUQCoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content").trim();
				if(content.startsWith("#")){
					String keyword = content.substring(1, content.length()-2);
					respContent = SelectScheduleService.select(keyword)+"<a href=\"http://182.92.79.92/NEUQ_Helper/init_view_course.action?sche_class_num="+keyword+"&sche_current_week="+WeekNumUtil.getCurrentWeekNum()+"\">点击可以查看编辑一周课表</a>";
				}else{
					respContent = "欢迎访问<a href=\"http://182.92.79.92/NEUQ_Helper/index.jsp\">首页！</a>";
				}
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢关注NEUQHelper,回复任意信息可以看到帮助菜单！";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					 if (eventKey.equals("12")) {
						 //先判断是否已经绑定
						boolean flag = ValidateBindService.validate(fromUserName);
						if(flag){
							//已经绑定
							String class_num = ValidateBindService.getClassNum(fromUserName);
							if(!("".equals(class_num))&&class_num!=null){
								//已经成功绑定
								String num = class_num.substring(0,class_num.length()-2);
								respContent = SelectScheduleService.select(num)+"<a href=\"http://182.92.79.92/NEUQ_Helper/init_view_course.action?sche_class_num="+num+"&sche_current_week="+WeekNumUtil.getCurrentWeekNum()+"\">点击可以查看编辑一周课表</a>";
							}
						}else{
							//没有绑定
							respContent = "请发送#学号，如#4100510.您也可以<a href=\"http://182.92.79.92/NEUQ_Helper/bind.jsp?fromUserName="+fromUserName+"&toUserName="+toUserName+"\">绑定学号</a>,就可以直接获取课表信息";
						}
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
//					 } else if (eventKey.equals("13")) {
//						respContent = SelectSelfStudyRommService.select();
//						textMessage.setContent(respContent);
//						respMessage = MessageUtil.textMessageToXml(textMessage);
//					} else if (eventKey.equals("14")) {
//						respContent = "我的图书菜单项被点击！";
					} else if (eventKey.equals("21")) {
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList = SelectNewsService.select();
//						Article article = new Article();
//						article.setTitle("JSP页面备份数据库");
//						article.setDescription("今天给大家介绍的是在JSP页面备份MySql数据库，下面将代码分享给大家!");
//						article.setPicUrl("http://static.oschina.net/uploads/img/201303/06135313_Ocre.jpg");
//						article.setUrl("http://blog.csdn.net/dbdxcs/article/details/19634207");
//						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
					} else if (eventKey.equals("22")) {
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList =  new ArrayList<Article>();
						Article article = new Article();
						article.setTitle("跳骚市场");
						article.setDescription("欢迎大家使用东秦跳骚市场，在这里，你可以转让自己不需要的物品，获取自己想要的物品！");
						article.setPicUrl("http://images.gz.house.sina.com.cn/2011/0720/S36126T1311143660266.jpg");
						article.setUrl("http://182.92.79.92/GoodsSwitch?fromUserName="+fromUserName+"&toUserName="+toUserName+"'");
						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						
//					} else if (eventKey.equals("23")) {
//						respContent = "教务通知菜单项被点击！";
//					} else if (eventKey.equals("24")) {
//						respContent = "就业信息菜单项被点击！";
//					} else if (eventKey.equals("25")) {
//						respContent = "意见建议菜单项被点击！";
					} else if (eventKey.equals("31")) {
						// 创建图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setFuncFlag(0);

						List<Article> articleList =  new ArrayList<Article>();
						Article article = new Article();
						article.setTitle("用户绑定");
						article.setDescription("欢迎大家使用东秦Helper，简单的绑定你的学号和手机号就可以便捷的使用本公共号的所有功能！");
						article.setPicUrl("http://pic22.nipic.com/20120629/1790055_102716506167_2.gif");
						article.setUrl("http://182.92.79.92/NEUQ_Helper/bind.jsp?fromUserName="+fromUserName+"&toUserName="+toUserName+"'");
						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
//					} else if (eventKey.equals("32")) {
//						respContent = "解除绑定菜单项被点击！";
//						textMessage.setContent(respContent);
//						respMessage = MessageUtil.textMessageToXml(textMessage);
//					} else if (eventKey.equals("33")) {
//						respContent = "个人中心菜单项被点击！";
//						textMessage.setContent(respContent);
//						respMessage = MessageUtil.textMessageToXml(textMessage);
//					} else if(eventKey.equals("34")){
//						respContent = "校园导航菜单项被点击！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}