package com.softnerve;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.bind.annotation.DeleteMapping;
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
	public JsonNode getIntern(){
	    ObjectMapper objectMapper = new ObjectMapper();
        JsonNode desired = null;
        JsonNode delta = null;
        JsonNode shadow = null;
        try {
            desired = objectMapper.readTree(new File("intern.json")); // Change
                                                                      // default
                                                                      // location
                                                                      // of file
                                                                      // to
                                                                      // classpath
                                                                      // folder
            delta = objectMapper.readTree(new File("intern.json"));
            shadow = objectMapper.readTree("{ }");
            
            ((ObjectNode) shadow).set("desired", desired);
            ((ObjectNode) shadow).set("delta", delta);
            
            File delte = new File ("intern.json");
            delte.delete();
            
        } catch (IOException e) {

            e.printStackTrace();
        }
        return shadow;
        
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
