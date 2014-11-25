package org.cs.demo.pojo;
/**
 * 课程pojo类
 * @author ZPJ
 *
 */
public class Course {
	private int pk_course;	 //课程主键
	private int pk_collage;	 //学院主键
	private int pk_teacher;	 // 代课老师主键
	private int pk_major;    //专业主键
	private int pk_class;    //上课班级主键
	private int pk_classroom ;//教室id
	private String	col_name;// 学院名称
	private String	maj_name;// 	 	          专业名称   如：信息与计算科学
	private String	cl_classname;//     	          班级名称					
	private String	cou_name;//     	          课程名称
	private String	cou_week;//          星期几
	private String	cou_num;//           第几节课
	private String	cla_classroomname;	//				教室位置
	private String	tea_name;	// 老师姓名
	private String	cou_weeknum;	//上课周数
	public int getPk_course() {
		return pk_course;
	}
	public void setPk_course(int pk_course) {
		this.pk_course = pk_course;
	}
	public int getPk_collage() {
		return pk_collage;
	}
	public void setPk_collage(int pk_collage) {
		this.pk_collage = pk_collage;
	}
	public int getPk_teacher() {
		return pk_teacher;
	}
	public void setPk_teacher(int pk_teacher) {
		this.pk_teacher = pk_teacher;
	}
	public int getPk_major() {
		return pk_major;
	}
	public void setPk_major(int pk_major) {
		this.pk_major = pk_major;
	}
	public int getPk_class() {
		return pk_class;
	}
	public void setPk_class(int pk_class) {
		this.pk_class = pk_class;
	}
	public int getPk_classroom() {
		return pk_classroom;
	}
	public void setPk_classroom(int pk_classroom) {
		this.pk_classroom = pk_classroom;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getMaj_name() {
		return maj_name;
	}
	public void setMaj_name(String maj_name) {
		this.maj_name = maj_name;
	}
	public String getCl_classname() {
		return cl_classname;
	}
	public void setCl_classname(String cl_classname) {
		this.cl_classname = cl_classname;
	}
	public String getCou_name() {
		return cou_name;
	}
	public void setCou_name(String cou_name) {
		this.cou_name = cou_name;
	}
	public String getCou_week() {
		return cou_week;
	}
	public void setCou_week(String cou_week) {
		this.cou_week = cou_week;
	}
	public String getCou_num() {
		return cou_num;
	}
	public void setCou_num(String cou_num) {
		this.cou_num = cou_num;
	}
	public String getCla_classroomname() {
		return cla_classroomname;
	}
	public void setCla_classroomname(String cla_classroomname) {
		this.cla_classroomname = cla_classroomname;
	}
	public String getTea_name() {
		return tea_name;
	}
	public void setTea_name(String tea_name) {
		this.tea_name = tea_name;
	}
	public String getCou_weeknum() {
		return cou_weeknum;
	}
	public void setCou_weeknum(String cou_weeknum) {
		this.cou_weeknum = cou_weeknum;
	}

}
