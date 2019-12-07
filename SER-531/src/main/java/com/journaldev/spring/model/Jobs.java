package com.journaldev.spring.model;

import java.util.List;

public class Jobs {

	String title;
	Location location;
	String postingDate;
	String postedBy;
	String applicationDeadLine;
	String department;
	String specializationReq;
	String jobSalary;
	String graduateLevelReq;
	List<String> skillName;

	public Jobs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public List<String> getSkillName() {
		return skillName;
	}

	public void setSkillName(List<String> skillName) {
		this.skillName = skillName;
	}

	public Jobs(String title, Location location, String postingdate, String applicationDeadLine, String department,
			String specializationReq, String jobSalary, String graduateLevelReq) {
		super();
		this.title = title;
		this.location = location;
		this.postingDate = postingdate;
		this.applicationDeadLine = applicationDeadLine;
		this.department = department;
		this.specializationReq = specializationReq;
		this.jobSalary = jobSalary;
		this.graduateLevelReq = graduateLevelReq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPostingdate() {
		return postingDate;
	}

	public void setPostingdate(String postingdate) {
		this.postingDate = postingdate;
	}

	public String getApplicationDeadLine() {
		return applicationDeadLine;
	}

	public void setApplicationDeadLine(String applicationDeadLine) {
		this.applicationDeadLine = applicationDeadLine;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSpecializationReq() {
		return specializationReq;
	}

	public void setSpecializationReq(String specializationReq) {
		this.specializationReq = specializationReq;
	}

	public String getJobSalary() {
		return jobSalary;
	}

	public void setJobSalary(String jobSalary) {
		this.jobSalary = jobSalary;
	}

	public String getGraduateLevelReq() {
		return graduateLevelReq;
	}

	public void setGraduateLevelReq(String graduateLevelReq) {
		this.graduateLevelReq = graduateLevelReq;
	}

	@Override
	public String toString() {
		return "Jobs [title=" + title + ", location=" + location + ", postingDate=" + postingDate + ", postedBy="
				+ postedBy + ", applicationDeadLine=" + applicationDeadLine + ", department=" + department
				+ ", specializationReq=" + specializationReq + ", jobSalary=" + jobSalary + ", graduateLevelReq="
				+ graduateLevelReq + ", skillName=" + skillName + "]";
	}

	

}
