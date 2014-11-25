package org.cs.demo.pojo;

/**
 * 新版课表pojo类
 * @author cs
 *
 */
public class Schedule {
	/**主键*/
	private int pk_schedule;
	/**班级号*/
	private String sche_class_num;	
	/**上课周数*/
	private String sche_week_num;
	/**周几*/
	private String sche_week;	
	/**课程名称*/
	private String sche_name;
	/**上课地点*/
	private String sche_space;	
	/**任课老师*/
	private String sche_teacher;	
	/**第几节课*/
	private String sche_num;		
	public int getPk_schedule() {
		return pk_schedule;
	}
	public void setPk_schedule(int pk_schedule) {
		this.pk_schedule = pk_schedule;
	}
	public String getSche_class_num() {
		return sche_class_num;
	}
	public void setSche_class_num(String sche_class_num) {
		this.sche_class_num = sche_class_num;
	}
	public String getSche_week_num() {
		return sche_week_num;
	}
	public void setSche_week_num(String sche_week_num) {
		this.sche_week_num = sche_week_num;
	}
	public String getSche_week() {
		return sche_week;
	}
	public void setSche_week(String sche_week) {
		this.sche_week = sche_week;
	}
	public String getSche_name() {
		return sche_name;
	}
	public void setSche_name(String sche_name) {
		this.sche_name = sche_name;
	}
	public String getSche_space() {
		return sche_space;
	}
	public void setSche_space(String sche_space) {
		this.sche_space = sche_space;
	}
	public String getSche_teacher() {
		return sche_teacher;
	}
	public void setSche_teacher(String sche_teacher) {
		this.sche_teacher = sche_teacher;
	}
	public String getSche_num() {
		return sche_num;
	}
	public void setSche_num(String sche_num) {
		this.sche_num = sche_num;
	}
}
