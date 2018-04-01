package com.softnerve;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class MainService {
	private String file_path = "intern.json";
	private ObjectMapper mapper = new ObjectMapper();
	@RequestMapping("/")
	public Intern getIntern(){
		File json = new File(file_path);
		Intern intern = new Intern("DefaulName", 1, 1);
		
		 try{
	         intern = mapper.readValue(json, Intern.class);
	      }
	      catch (JsonParseException e) { e.printStackTrace();}
	      catch (JsonMappingException e) { e.printStackTrace(); }
	      catch (IOException e) { e.printStackTrace(); }
		 
		 return intern;		
	}
	
	@RequestMapping("/set")
    public String setIntern(@RequestParam Map<String,String> requestParams) {
		String name=requestParams.get("name");
		int age = Integer.parseInt(requestParams.get("age"));
		int stipend = Integer.parseInt(requestParams.get("stipend"));
		
		Intern intern = new Intern(name, age, stipend);
		System.out.println(intern);
		try{
			mapper.writeValue(new File(file_path), intern );
			return "Successful!";
		}catch (IOException e) {
	        e.printStackTrace();
	        return "Error!";
	    }
		
		
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MainService.class, args);
	}
}
