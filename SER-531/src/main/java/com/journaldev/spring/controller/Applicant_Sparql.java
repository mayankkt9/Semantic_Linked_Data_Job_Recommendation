package com.journaldev.spring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.journaldev.spring.model.*;

public class Applicant_Sparql {

	static String serviceEndpoint = "http://localhost:3030/ExploreJob";

	private StringBuilder createApplicantFilter(StringBuilder query, String json) throws ParseException {
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj;
		if (jo.get("email") != null) {
			String value = jo.get("email").toString();
			String titleFilter = "FILTER (?email = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("expectedGraduationDate") != null) {
			String value = jo.get("expectedGraduationDate").toString();
			String titleFilter = "FILTER (?expectedGradDate = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("expectedSalary") != null) {
			String value = jo.get("expectedSalary").toString();
			String titleFilter = "FILTER (xsd:float(Substr(?expectedSalary,2))) <= " + value + ")";
			query.append(titleFilter);
		}
		if (jo.get("applicantName") != null) {
			String value = jo.get("applicantName").toString();
			String titleFilter = "FILTER (?name = \"" + value + "\")";
			query.append(titleFilter);
		}

		if (jo.get("specialization") != null) {
			String value = jo.get("specialization").toString();
			String titleFilter = "FILTER (?specialization = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("schoolLevel") != null) {
			String value = jo.get("schoolLevel").toString();
			String titleFilter = "FILTER (?schoolLevel = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("gender") != null) {
			String value = jo.get("gender").toString();
			String titleFilter = "FILTER (?gender = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("livesIn") != null) {
			String value = jo.get("livesIn").toString();
			String titleFilter = "FILTER (?loc = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("major") != null) {
			String value = jo.get("major").toString();
			String titleFilter = "FILTER (?major = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("university") != null) {
			String value = jo.get("university").toString();
			String titleFilter = "FILTER (?university = \"" + value + "\")";
			query.append(titleFilter);
		}

		return query;

	}

	public StringBuilder createApplicantFilterSkill(StringBuilder query, String json) throws ParseException {
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj;
		if (jo.get("skills") != null) {
			JSONArray ja = (JSONArray) jo.get("skills");
			Iterator itr2 = ja.iterator();
			while (itr2.hasNext()) {
				String str = itr2.next().toString();
				// query.append("FILTER regex(?skills, \"" + str + "\")");
				query.append("FILTER EXISTS {?person getApp:has_skills \"" + str + "\"}.");
			}
		}

		return query;
	}

	public List<Applicants> getApplicantByFilter(StringBuilder queryString, String json) throws ParseException {
		queryString.append(
				"SELECT ?name ?email ?gender ?expectedGradDate ?expectedSalary ?skills ?major ?university ?schoolLevel ?specialization ?loc ?lat ?long\n"
						+ "WHERE{\n" + "  	SERVICE <http://34.94.128.250:3030/Project/>\n" + "  	{\n"
						+ "    	SELECT ?name ?email ?gender ?expectedGradDate ?expectedSalary (group_concat(?skill) as ?skills) ?major ?university ?schoolLevel ?specialization ?loc\n"
						+ "    	WHERE {\n" + "            ?person    getApp:has_Name  ?name ;\n"
						+ "                       getApp:lives_in  ?loc ;\n"
						+ "                       getApp:email  ?email ;\n"
						+ "                       getApp:gender ?gender ;\n"
						+ "                       getApp:expectedGraduationDate  ?expectedGradDate ;\n"
						+ "                       getApp:expected_Salary  ?expectedSalary ;\n"
						+ "                       getApp:has_skills  ?skill ;\n"
						+ "                       getApp:major  ?major ;\n"
						+ "                       getApp:school  ?university ;\n"
						+ "                       getApp:schoolLevel ?schoolLevel ;\n"
						+ "                       getApp:specialization ?specialization ;\n"
						+ "                       getApp:schoolLevel ?schoolLevel .");
		queryString = createApplicantFilterSkill(queryString, json);
		queryString = createApplicantFilter(queryString, json);

		queryString.append("}\n" + "    \n"
				+ "    	group by ?name ?email ?gender ?expectedGradDate ?expectedSalary ?major ?university ?schoolLevel ?specialization ?loc\n"
				+ "  	}\n" + "    SERVICE <http://137.116.191.4:3030/Project/>\n" + "    {\n"
				+ "        SELECT ?lat ?long ?loc2\n" + "        WHERE {\n"
				+ "			?location getLoc:has_Name ?loc2;\n" + "					  getLoc:has_Longitude ?long;\n"
				+ "					  getLoc:has_Latitude ?lat.\n" + "      }\n" + "    }\n"
				+ "  Filter(?loc = ?loc2)\n" + "} LIMIT 20");

		System.out.println(queryString.toString());
		Query query = QueryFactory.create(queryString.toString());
		System.out.println(queryString.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndpoint, query);
		List<Applicants> result = new ArrayList<Applicants>();
		try {
			ResultSet response = qexec.execSelect();
			result = getApplicantFromResponse(response);
			System.out.println(result);

		} finally {
			qexec.close();
		}
		return result;
	}

	private List<Applicants> getApplicantFromResponse(ResultSet response) {
		ArrayList<Applicants> list = new ArrayList<Applicants>();
		while (response.hasNext()) {
			QuerySolution soln = response.nextSolution();
			RDFNode email = soln.get("?email");
			RDFNode expectedGraduationDate = soln.get("?expectedGradDate");
			RDFNode expectedSalary = soln.get("?expectedSalary");
			RDFNode gender = soln.get("?gender");
			RDFNode applicantName = soln.get("?name");
			RDFNode lives_in = soln.get("?loc");
			RDFNode major = soln.get("?major");
			RDFNode university = soln.get("?university");
			RDFNode specialization = soln.get("?specialization");
			RDFNode latitude = soln.get("?lat");
			RDFNode longitude = soln.get("?long");
			RDFNode schoolLevel = soln.get("?schoolLevel");
			RDFNode skills = soln.get("skills");

			Applicants applicant = new Applicants();
			Location location = new Location();
			LatLongPair pair = new LatLongPair();

			if (skills != null) {
				String skill_string = skills.toString();

				List<String> skill_list = Arrays.asList(skill_string.split(" "));
				applicant.setSkills(skill_list);
			} else {
				System.out.println("Skills Not Found");
			}
			if (applicantName != null)
				applicant.setApplicantName(applicantName.toString());
			else
				System.out.println("Name not there");

			if (email != null) {
				applicant.setEmail(email.toString());
			} else {
				System.out.println("Email  Not Found");
			}
			if (university != null)
				applicant.setUniversity(university.toString());
			else
				System.out.println("No University");

			if (expectedGraduationDate != null)
				applicant.setExpectedGraduationDate(expectedGraduationDate.toString());
			else
				System.out.println("Expected Graduation Date Not Found");

			if (lives_in != null)
				location.setCityName(lives_in.toString());
			else
				System.out.println("City Name Not Found");

			if (expectedSalary != null)
				applicant.setExpectedSalary(expectedSalary.toString());
			else
				System.out.println("expectedSalary  Not Found");

			if (gender != null)
				applicant.setGender(gender.toString());
			else
				System.out.println("Gender Not Mentioned");
			if (major != null)
				applicant.setMajor(major.toString());
			else
				System.out.println("Major Not Found");
			if (schoolLevel != null)
				applicant.setSchoolLevel(schoolLevel.toString());
			else
				System.out.println("School Level Not Found");
			if (specialization != null)
				applicant.setSpecialization(specialization.toString());
			else
				System.out.println("Specialization Not Found");

			if (latitude != null)
				pair.setLatitude(latitude.toString());
			else
				System.out.println("Latitude Not found");
			if (longitude != null)
				pair.setLongitude(longitude.toString());
			else
				System.out.println("Longitude Not Found");

			location.setPair(pair);
			applicant.setLocation(location);

			list.add(applicant);

		}
		return list;

	}

}
