package com.softnerve;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONTest {
    static ObjectMapper objectMapper = new ObjectMapper();
    
    
    public static void main(String[] args) {
        
        try {
            JsonNode newFile = objectMapper.readTree(new File("newFile.json"));
            JsonNode oldFile = objectMapper.readTree(new File("oldFile.json"));
            JsonNode delta = tree_traversal(newFile, oldFile);
            objectMapper.writeValue(new File("delta.json"), delta); // Make doc writing pretty
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    public static JsonNode tree_traversal(JsonNode newJson, JsonNode oldJson) {
        JsonNode delta, innerDelta;
        try {
            
            
            delta = objectMapper.readTree("{}");
            innerDelta = objectMapper.readTree("{}");
            
            Iterator<String> fields = newJson.fieldNames();
            
            while(fields.hasNext()) {
                String field = fields.next();
                // Add if field dosent exist in old json case
                if (!newJson.get(field).equals(oldJson.get(field))){
                    
                    if(newJson.get(field).isObject()){
                        innerDelta = tree_traversal(newJson.get(field), oldJson.get(field));
                        ((ObjectNode) delta).set(field, innerDelta);
                    }
                    
                    else{
                    ((ObjectNode) delta).set(field, newJson.get(field));
                    }
                }
                
            }
            
           
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            delta = null;
            e.printStackTrace();
        } 
        return delta;
    }
}
