package org.cs.demo.pojo;
/**
 * 电影票房pojo
 * @author Administrator
 *
 */
public class Movie {
	private String rid;
	private String name;
	private String wk;
	private String wboxoffice;
	private String tboxoffice;
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWk() {
		return wk;
	}
	public void setWk(String wk) {
		this.wk = wk;
	}
	public String getWboxoffice() {
		return wboxoffice;
	}
	public void setWboxoffice(String wboxoffice) {
		this.wboxoffice = wboxoffice;
	}
	public String getTboxoffice() {
		return tboxoffice;
	}
	public void setTboxoffice(String tboxoffice) {
		this.tboxoffice = tboxoffice;
	}
	@Override
	public String toString() {
		return "Movie [rid=" + rid + ", name=" + name + ", wk=" + wk
				+ ", wboxoffice=" + wboxoffice + ", tboxoffice=" + tboxoffice
				+ "]";
	}
}
