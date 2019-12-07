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


public class Job_Sparql {

	static String serviceEndpoint = "http://localhost:3030/ExploreJob";
	
	public StringBuilder createJobFilter(StringBuilder query, String json) throws ParseException {
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj;

		if (jo.get("title") != null) {
			String value = jo.get("title").toString();
			String titleFilter = "FILTER (?title = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("cityName") != null) {
			String value = jo.get("cityName").toString();
			String titleFilter = "FILTER (?cityName = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("postdate") != null) {
			String value = jo.get("postdate").toString();
			String titleFilter = "FILTER (?postdate = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("appdeadline") != null) {
			String value = jo.get("appdeadline").toString();
			String titleFilter = "FILTER (?appdeadline = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("department") != null) {
			String value = jo.get("department").toString();
			String titleFilter = "FILTER (?department = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("specialzationRequirement") != null) {
			String value = jo.get("specialzationRequirement").toString();
			String titleFilter = "FILTER (?specialzationRequirement = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("graduateLevelReq") != null) {
			String value = jo.get("graduateLevelReq").toString();
			String titleFilter = "FILTER (?graduateLevelReq = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("posteBy") != null) {
			String value = jo.get("postedBy").toString();
			String titleFilter = "FILTER (?posteBy = \"" + value + "\")";
			query.append(titleFilter);
		}
		if (jo.get("jobSalary") != null) {
			String value = jo.get("jobSalary").toString();
			String titleFilter = "\n FILTER (xsd:float(?salary) >= " + value + ")";
			query.append(titleFilter);
		}
		return query;
	}
	public StringBuilder createJobFilterSkill(StringBuilder query, String json) throws ParseException {
		Object obj = new JSONParser().parse(json);
		JSONObject jo = (JSONObject) obj;
		if (jo.get("skillName") != null) {
			JSONArray ja = (JSONArray) jo.get("skillName");
			Iterator itr2 = ja.iterator();
			while (itr2.hasNext()) {
				String str = itr2.next().toString();
				query.append("FILTER regex(?skillName,\"" + str + "\")");

			}
		}

		return query;
	}

	public List<Jobs> getJobByFilter(StringBuilder queryString, String json) throws ParseException {
		queryString.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX getJob: <http://www.semanticweb.org/SER-531/Team-14/Jobs#>\n"
				+ "PREFIX getLoc: <http://www.semanticweb.org/SER-531/Team-14/Location#>\n" + "\n"
				+ "SELECT DISTINCT ?title ?cityName ?postedBy ?skillName ?postdate ?appdeadline ?department ?specialzationRequirement ?graduateLevelReq ?lat ?long ?salary\n"
				+ "WHERE{\n" + "	SERVICE <http://34.197.177.97:3030/Project/>\n" + "  	{\n"
				+ "      SELECT ?title ?cityName ?postedBy ?postdate ?appdeadline ?department ?specialzationRequirement ?graduateLevelReq ?skillName ?salary ?lat ?long\n"
				+ "      WHERE {\n" + "        ?s  getJob:has_title ?title ;\n"
				+ "            getJob:located_in ?cityName ;\n" + "            getJob:posted_by ?postedBy ;\n"
				+ "            getJob:posting_date ?postdate ;\n"
				+ "            getJob:application_Deadline ?appdeadline ;\n"
				+ "            getJob:belongs_to ?department ;\n"
				+ "            getJob:specialzationRequirement ?specialzationRequirement ;\n"
				+ "            getJob:graduateLevelRequirement ?graduateLevelReq ;\n"
				+ "            getJob:has_SkillName ?skillName ;\n" + "            getJob:has_Salary ?salary .");

		queryString = createJobFilterSkill(queryString, json);
		queryString = createJobFilter(queryString, json);

		queryString.append("}\n" + "	}\n" + "	SERVICE <http://137.116.191.4:3030/Project/>\n" + "    {\n"
				+ "      SELECT ?lat ?long ?cityName2\n" + "      WHERE {\n"
				+ "        ?location getLoc:has_Name ?cityName2;\n" + "                  getLoc:has_Longitude ?long;\n"
				+ "                  getLoc:has_Latitude ?lat.\n" + "      }\n" + "    }\n"
				+ "Filter(?cityName = ?cityName2)}\n" + "LIMIT 30");

		// String x = "Select ?s ?p ?o Where {?s ?p ?o}Limit 25";
		Query query = QueryFactory.create(queryString.toString());
		System.out.println(queryString.toString());
		QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndpoint, query);
		List<Jobs> result = new ArrayList<Jobs>();
		try {
			
			ResultSet response = qexec.execSelect();
			result = getJobFromResponse(response);
			System.out.println(result);

		} finally {
			qexec.close();
		}
		return result;
	}
	private List<Jobs> getJobFromResponse(ResultSet response) {
		ArrayList<Jobs> list = new ArrayList<Jobs>();
		while (response.hasNext()) {
			QuerySolution soln = response.nextSolution();
			RDFNode title = soln.get("?title");
			RDFNode cityName = soln.get("?cityName");
			RDFNode postingdate = soln.get("?postdate");
			RDFNode applicationDeadLine = soln.get("?appdeadline");
			RDFNode department = soln.get("?department");
			RDFNode specializationReq = soln.get("?specialzationRequirement");
			RDFNode jobSalary = soln.get("?salary");
			RDFNode graduateLevelReq = soln.get("?graduateLevelReq");
			RDFNode latitude = soln.get("?lat");
			RDFNode longitude = soln.get("?long");
			RDFNode postedBy = soln.get("?postedBy");
			RDFNode skills = soln.get("?skillName");

			Jobs job = new Jobs();
			Location location = new Location();
			LatLongPair pair = new LatLongPair();

			if (skills != null) {
				String skill_string = skills.toString();

				List<String> skill_list = Arrays.asList(skill_string.split(","));
				job.setSkillName(skill_list);
			} else {
				System.out.println("Skills Not Found");
			}

			if (postedBy != null) {
				job.setPostedBy(postedBy.toString());
			} else {
				System.out.println("Posted By Not Found");
			}
			if (title != null)
				job.setTitle(title.toString());
			else
				System.out.println("Title Not Found");

			if (cityName != null)
				location.setCityName(cityName.toString());
			else
				System.out.println("City Name Not Found");

			if (postingdate != null)
				job.setPostingdate(postingdate.toString());
			else
				System.out.println("Posting Date Not Found");
			if (applicationDeadLine != null)
				job.setApplicationDeadLine(applicationDeadLine.toString());
			else
				System.out.println("Application Deadline Not Found");
			if (department != null)
				job.setDepartment(department.toString());
			else
				System.out.println("Department Not Found");
			if (specializationReq != null)
				job.setSpecializationReq(specializationReq.toString());
			else
				System.out.println("Special Requirement Not Found");
			if (jobSalary != null)
				job.setJobSalary(jobSalary.toString());
			else
				System.out.println("Job Salary Not Found");
			if (graduateLevelReq != null)
				job.setGraduateLevelReq(graduateLevelReq.toString());
			else
				System.out.println("Graduate Level Requirement Not Found");
			if (latitude != null)
				pair.setLatitude(latitude.toString());
			else
				System.out.println("Latitude Not found");
			if (longitude != null)
				pair.setLongitude(longitude.toString());
			else
				System.out.println("Longitude Not Found");

			location.setPair(pair);
			job.setLocation(location);

			list.add(job);

		}
		return list;

	}
}
