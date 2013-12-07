package model;

import java.sql.Date;

public class Comment {
	
	private String employeeName;
	private Date date;
	private String comment;

	
	public Comment (String empployeeName, Date date, String comment){
		this.employeeName = empployeeName;
		this.date = date;
		this.comment = comment;
		
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
