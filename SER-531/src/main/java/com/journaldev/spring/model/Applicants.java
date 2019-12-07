package com.journaldev.spring.model;

import java.util.List;

public class Applicants {

	String email;
	Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	String expectedGraduationDate;
	String expectedSalary;
	String gender;
	String applicantName;
	List<String> skills;
	String major;
	String university;
	String schoolLevel;
	String specialization;

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Applicants [email=" + email + ", expectedGraduationDate=" + expectedGraduationDate + ", expectedSalary="
				+ expectedSalary + ", gender=" + gender + ", applicantName=" + applicantName + ", skills=" + skills
				+ ", major=" + major + ", university=" + university + ", schoolLevel=" + schoolLevel
				+ ", specialization=" + specialization + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Applicants() {
		super();
// TODO Auto-generated constructor stub
	}

	public Applicants(String email, String expectedGraduationDate, String expectedSalary, String gender,
			String applicantName, List<String> skills, String major, String university, String schoolLevel,
			String specialization) {
		super();
		this.email = email;
		this.expectedGraduationDate = expectedGraduationDate;
		this.expectedSalary = expectedSalary;
		this.gender = gender;
		this.applicantName = applicantName;
		this.skills = skills;
		this.major = major;
		this.university = university;
		this.schoolLevel = schoolLevel;
		this.specialization = specialization;
	}

	public String getExpectedGraduationDate() {
		return expectedGraduationDate;
	}

	public void setExpectedGraduationDate(String expectedGraduationDate) {
		this.expectedGraduationDate = expectedGraduationDate;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
}