package com.journaldev.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journaldev.spring.model.Applicants;
import com.journaldev.spring.model.Composition;
import com.journaldev.spring.model.Employee;
import com.journaldev.spring.model.Student;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {

	// build Map from any of the json object constructed from the query
	public void recursive(JSONObject object, Map<String, List<String>> map) {
		for (Object key : object.keySet()) {
			Object value = object.get(key);

			if (value instanceof JSONObject) {
				recursive((JSONObject) value, map);
			} else if (value instanceof JSONArray) {

				if (value != null) {
					JSONArray jsonArray = (JSONArray) value;
					String str = "";
					for (int i = 0; i < jsonArray.size(); i++) {
						str += jsonArray.get(i) + ",";
					}
					if (map.containsKey(key.toString())) {
						map.get(key.toString()).add(str);
					} else {
						List<String> lst = new ArrayList<String>();
						lst.add(str);
						map.put(key.toString(), lst);
					}
				}

			} else {

				if (value != null) {
					if (map.containsKey(key.toString())) {
						map.get(key.toString()).add(value.toString());
					} else {
						List<String> lst = new ArrayList<String>();
						lst.add(value.toString());
						map.put(key.toString(), lst);
					}
				}

				else {
					if (!map.containsKey(key.toString())) {
						List<String> lst = new ArrayList<String>();
						lst.add("-");
						map.put(key.toString(), lst);
					}
				}

			}

		}

	}

	// build json from the constructed hash map
	public List<JSONObject> buildJson(Map<String, List<String>> map) {
		int length = 0;
		for (String key : map.keySet()) {
			length = map.get(key).size();
		}
		List<JSONObject> temp = new ArrayList<JSONObject>();
		for (int i = 0; i < length; i++) {
			JSONObject jsonObject = new JSONObject();
			for (String key : map.keySet()) {
				jsonObject.put(key, map.get(key).get(i));
			}
			temp.add(jsonObject);
		}
		return temp;
	}

	@RequestMapping(value="/")
	 public String index() {
		System.out.println("Heyyyyyyyyy");
	  return "index";
	 }
	
	@RequestMapping(value = "/sparql/{json}", method = RequestMethod.GET)
	public @ResponseBody String createEmployee(@PathVariable("json") String json) throws JsonProcessingException, ParseException {

		StringBuilder queryString = new StringBuilder();
		queryString.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryString.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryString.append("PREFIX getApp: <http://www.semanticweb.org/SER-531/Team-14/Applicants#>");
		queryString.append("PREFIX getJob: <http://www.semanticweb.org/SER-531/Team-14/Jobs#>");
		queryString.append("PREFIX getLoc: <http://www.semanticweb.org/SER-531/Team-14/Location#>");

		Sparql_Fuseki_Query sfq = new Sparql_Fuseki_Query();
		//Applicant_Sparql as = new Applicant_Sparql();
		Job_Sparql js = new Job_Sparql();
		// sfq.getCityName(serviceEndpoint, queryString);
		// sfq.solve1(serviceEndpoint, queryString);
	   System.out.println("Printing JSON ");
	   System.out.println(json);
	   JSONObject jsonObject = (JSONObject)JSONValue.parse(json);
	   
	   if(jsonObject.containsKey("skillName")) {
	   String s = jsonObject.get("skillName").toString();
       String split[] = s.split(",");
       
       JSONArray jsonArray = new JSONArray();
       for(int i=0;i<split.length;i++) {
    	     jsonArray.add(split[i]);
       }
		jsonObject.put("skillName", jsonArray);
	   }       
	   json = jsonObject.toString();
	   System.out.println(json);
       // pass the query result in place of composition
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(js.getJobByFilter(queryString, json));
        System.out.println(jsonString);
        JSONArray array =(JSONArray)JSONValue.parse(jsonString);
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for(int i=0; i<array.size();i++){
			 recursive((JSONObject) array.get(i), map);
		}
	
		return objectMapper.writeValueAsString(buildJson(map));
	}
	
	
	
	
	@RequestMapping(value = "/applicants/{json}", method = RequestMethod.GET)
	public @ResponseBody String createEmployee1(@PathVariable("json") String json) throws JsonProcessingException, ParseException {

		StringBuilder queryString = new StringBuilder();
		queryString.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryString.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryString.append("PREFIX getApp: <http://www.semanticweb.org/SER-531/Team-14/Applicants#>");
		queryString.append("PREFIX getJob: <http://www.semanticweb.org/SER-531/Team-14/Jobs#>");
		queryString.append("PREFIX getLoc: <http://www.semanticweb.org/SER-531/Team-14/Location#>");

		Sparql_Fuseki_Query sfq = new Sparql_Fuseki_Query();
		Applicant_Sparql as = new Applicant_Sparql();
		//Job_Sparql js = new Job_Sparql();
		// sfq.getCityName(serviceEndpoint, queryString);
		// sfq.solve1(serviceEndpoint, queryString);
		System.out.println("Printing JSON ");
	   System.out.println(json);
	   JSONObject jsonObject = (JSONObject)JSONValue.parse(json);
	   
	   if(jsonObject.containsKey("skills")) {
	   String s = jsonObject.get("skills").toString();
       String split[] = s.split(",");
       
       JSONArray jsonArray = new JSONArray();
       for(int i=0;i<split.length;i++) {
    	     jsonArray.add(split[i]);
       }
		jsonObject.put("skills", jsonArray);
	   }       
	   json = jsonObject.toString();
	   System.out.println(json);
       // pass the query result in place of composition
		ObjectMapper objectMapper = new ObjectMapper();
		List<Applicants> res = as.getApplicantByFilter(queryString, json);
		String jsonString = objectMapper.writeValueAsString(res);
        System.out.println(jsonString);
        JSONArray array =(JSONArray)JSONValue.parse(jsonString);
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for(int i=0; i<array.size();i++){
			 System.out.println("99999");
			 System.out.println(array.get(i));
			 recursive((JSONObject) array.get(i), map);
		}
		System.out.println(map);
		return objectMapper.writeValueAsString(buildJson(map));
	}

}
