package org.cs.demo.pojo;

/**
 * 消息pojo
 * @author cs
 *
 */
public class News {
	private int pk_news;                   		/**消息主键*/
	private String new_title;					  	/**消息标题*/
	private String new_description;					/**消息内容*/
	private String new_picurl;						/**图片url*/
	private String new_url;							/**消息原文url*/
	private String new_type;						/**消息类型*/
	private String new_creattime;					/**消息创建时间*/
	private String new_booleandel;					/**是否删除(Y 表示已删除   N表示未删除)*/
	public int getPk_news() {
		return pk_news;
	}
	public void setPk_news(int pk_news) {
		this.pk_news = pk_news;
	}
	public String getNew_title() {
		return new_title;
	}
	public void setNew_title(String new_title) {
		this.new_title = new_title;
	}
	public String getNew_description() {
		return new_description;
	}
	public void setNew_description(String new_description) {
		this.new_description = new_description;
	}
	public String getNew_picurl() {
		return new_picurl;
	}
	public void setNew_picurl(String new_picurl) {
		this.new_picurl = new_picurl;
	}
	public String getNew_url() {
		return new_url;
	}
	public void setNew_url(String new_url) {
		this.new_url = new_url;
	}
	public String getNew_type() {
		return new_type;
	}
	public void setNew_type(String new_type) {
		this.new_type = new_type;
	}
	public String getNew_creattime() {
		return new_creattime;
	}
	public void setNew_creattime(String new_creattime) {
		this.new_creattime = new_creattime;
	}
	public String getNew_booleandel() {
		return new_booleandel;
	}
	public void setNew_booleandel(String new_booleandel) {
		this.new_booleandel = new_booleandel;
	}	
}
