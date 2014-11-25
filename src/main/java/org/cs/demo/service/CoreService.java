package org.cs.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.cs.demo.message.resp.Article;
import org.cs.demo.message.resp.Music;
import org.cs.demo.message.resp.MusicMessage;
import org.cs.demo.message.resp.NewsMessage;
import org.cs.demo.message.resp.TextMessage;
import org.cs.demo.util.MessageUtil;

/**
 * 核心服务类
 */
public class CoreService {
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
			System.out.println(msgType);

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			textMessage.setContent(getGuide());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content").trim();
				
				if (content.startsWith("翻译")) {  
			        String keyWord = content.replaceAll("^翻译", "").trim();  
			        if ("".equals(keyWord)) {  
			            textMessage.setContent(getTranslateUsage());  
			        } else {  
			            textMessage.setContent(BaiduTranslateService.translate(keyWord));  
			        }  
			        respMessage = MessageUtil.textMessageToXml(textMessage);
			    }
				
				if (content.startsWith("票房")) {  
			        String keyWord = content.replaceAll("^票房", "").trim();  
			        if ("".equals(keyWord)) {  
			            textMessage.setContent(getMoviePerformUsage());  
			        } else {  
			        	if("内地".equals(keyWord)){
			        		keyWord = "CN";
			        	}else if("北美".equals(keyWord)){
			        		keyWord = "US";
			        	}else if("香港".equals(keyWord)){
			        		keyWord = "HK";
			        	}else{
			        		keyWord = "CN";
			        	}
			            textMessage.setContent(MoviePerformService.getMovieMessage(keyWord));  
			        }  
			        respMessage = MessageUtil.textMessageToXml(textMessage);
			    }
				
				if (content.startsWith("天气")) {  
			        String keyWord = content.replaceAll("^天气", "").trim();  
			        if ("".equals(keyWord)) {  
			            textMessage.setContent(getGuide());  
			        } else {  
			            textMessage.setContent(WetherService.getWetherMessage(keyWord));  
			        }  
			        respMessage = MessageUtil.textMessageToXml(textMessage);
			    }
				
				
				// 单图文消息
				if ("1".equals(content)) {
					// 创建图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);

					List<Article> articleList = new ArrayList<Article>();
					Article article = new Article();
					article.setTitle("JSP页面备份数据库");
					article.setDescription("今天给大家介绍的是在JSP页面备份MySql数据库，下面将代码分享给大家!");
					article.setPicUrl("http://static.oschina.net/uploads/img/201303/06135313_Ocre.jpg");
					article.setUrl("http://blog.csdn.net/dbdxcs/article/details/19634207");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 文本
				else if ("2".equals(content)) {
					respContent = "奥特曼有一天在上课，有问题，于是举手……老师死了……";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);

				}
				// 音乐
				else if (content.startsWith("歌曲")) {
					// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
					String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?","");
					// 如果歌曲名称为空
					if ("".equals(keyWord)) {
						respContent = getUsage();
					} else {
						String[] kwArr = keyWord.split("@");
						// 歌曲名称
						String musicTitle = kwArr[0];
						// 演唱者默认为空
						String musicAuthor = "";
						if (2 == kwArr.length)
							musicAuthor = kwArr[1];

						// 搜索音乐
						Music music = BaiduMusicService.searchMusic(musicTitle,musicAuthor);
						// 未搜索到音乐
						if (null == music) {
							respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
						} else {
							// 音乐消息
							MusicMessage musicMessage = new MusicMessage();
							musicMessage.setToUserName(fromUserName);
							musicMessage.setFromUserName(toUserName);
							musicMessage.setCreateTime(new Date().getTime());
							musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
							musicMessage.setMusic(music);
							respMessage = MessageUtil.musicMessageToXml(musicMessage);
						}
						// 未搜索到音乐时返回使用指南
						if (null == respMessage) {
							if (null == respContent)
								respContent = getUsage();
							textMessage.setContent(respContent);
							respMessage = MessageUtil.textMessageToXml(textMessage);
						}
					}
				}
			}
				// 图片消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					// 取得图片地址  
	                String picUrl = requestMap.get("PicUrl");
	                System.out.println(picUrl);
	                // 人脸检测  
	                String detectResult = FaceService.detect(picUrl);  
					textMessage.setContent(detectResult);
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
						respContent = "谢谢您的关注！回复任意文本信息可以获取操作指南！";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
					// 取消订阅
					else if (eventType
							.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						// TODO 自定义菜单权没有开放，暂不处理该类消息
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	/**
	 * 歌曲点播使用指南
	 * 
	 * @return
	 */
	public static String getUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("歌曲点播操作指南").append("\n\n");
		buffer.append("回复：歌曲+歌名").append("\n");
		buffer.append("例如：歌曲枫").append("\n");
		buffer.append("或者：歌曲枫@周杰伦").append("\n\n");
		return buffer.toString();
	}
	
	/** 
	 * Q译通使用指南 
	 *  
	 * @return 
	 */  
	public static String getTranslateUsage() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("目前支持以下翻译方向：").append("\n");  
	    buffer.append("    中 -> 英").append("\n");  
	    buffer.append("    英 -> 中").append("\n");  
	    buffer.append("    西班牙 -> 中").append("\n");  
	    buffer.append("    韩 -> 中").append("\n");  
	    buffer.append("    日 -> 中").append("\n\n");  
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("    翻译我是中国人").append("\n");  
	    buffer.append("    翻译dream").append("\n");  
	    buffer.append("    翻译Quién eres tú").append("\n");  
	    buffer.append("    翻译너 누구야").append("\n");  
	    buffer.append("    翻译さようなら").append("\n\n");  
	    return buffer.toString();  
	} 
	
	/**
	 * 电影票房指南
	 */
	public static String getMoviePerformUsage() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("目前支持以地区电影票房查询：").append("\n");  
	    buffer.append("    内地").append("\n");  
	    buffer.append("    北美").append("\n");  
	    buffer.append("    香港").append("\n\n");  
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("    票房内地").append("\n");  
	    buffer.append("    票房北美").append("\n");  
	    buffer.append("    票房香港").append("\n\n");  
	    return buffer.toString();  
	} 
	
	/**
	 * 微信使用指南
	 */
	public static String getGuide(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("欢迎访问<a href=\"http://dbdxcs.duapp.com\">CS的主页</a>\n\n");
		buffer.append(emoji(0x1F4BB));
		buffer.append("回复1:查看最近推送的文章\n");
		buffer.append(emoji(0x1F604));
		buffer.append("回复2：查看幽默笑话\n");
		buffer.append(emoji(0x1F3B5));
		buffer.append("回复：歌曲+歌名\n如：歌曲枫或者歌曲枫@周杰伦即可点播歌曲\n");
		buffer.append(emoji(0x1F4F7));
		buffer.append("上传图片可以进行人脸检测\n");
		buffer.append(emoji(0x1F47B));
		buffer.append("回复翻译可以查看翻译指南\n");
		buffer.append(emoji(0x1F4BF));
		buffer.append("回复票房可以查看票房指南\n");
		buffer.append(emoji(0x2614));
		buffer.append("回复天气+城市名可以查询天气，如天气北京\n\n");
		return buffer.toString();
	}
}
