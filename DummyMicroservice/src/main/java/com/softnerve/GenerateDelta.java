package com.softnerve;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenerateDelta {
    static ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) {
        generate();
    }
    
    public static void generate() {
        
        try {
            JsonNode newJson = objectMapper.readTree(new File("newFile.json"));
            JsonNode delta, oldJson;
            File old_File = new File("oldFile.json");
            
            if(!old_File.exists())
                delta = newJson;
            
            else{
                try {
                    oldJson = objectMapper.readTree(old_File);
                } catch (JsonMappingException e) {
                    // TODO: handle exception
                    oldJson = objectMapper.readTree("{}");
                }
                
                delta = node_traversal(newJson, oldJson);
            }
            
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("delta.json"), delta);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("oldFile.json"), newJson);// Make doc writing pretty
            
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static JsonNode node_traversal(JsonNode newJson, JsonNode oldJson) {
        JsonNode delta, innerDelta;
        try {
            
            
            delta = objectMapper.readTree("{}");
            innerDelta = objectMapper.readTree("{}");
            
            Iterator<String> fields = newJson.fieldNames();
            
            while(fields.hasNext()) {
                String field = fields.next();
                // Add if field dosent exist in old json case
                if (!oldJson.has(field)) {
                    ((ObjectNode) delta).set(field, newJson.get(field));
                }
                else if (!newJson.get(field).equals(oldJson.get(field))){
                    
                    if(newJson.get(field).isObject()){
                        innerDelta = node_traversal(newJson.get(field), oldJson.get(field));
                        ((ObjectNode) delta).set(field, innerDelta);
                    }
                    else if(newJson.get(field).isArray()) {
                        ArrayNode deltaArray = objectMapper.createArrayNode();
                        
                        JsonNode newArray = newJson.get(field);
                        JsonNode oldArray = oldJson.get(field);
                        
                        Iterator<JsonNode> newArrayElements = newArray.elements();
                        Iterator<JsonNode> oldArrayElements = oldArray.elements();
                        while (newArrayElements.hasNext()) {
                            JsonNode newNode = newArrayElements.next();
                            JsonNode oldNode = oldArrayElements.next();
                            if (!newNode.equals(oldNode)){
                                 innerDelta = node_traversal(newNode, oldNode);
                                
                            }
                            // Add the condition when array elements are not objects.
                            
                            if (innerDelta.size() != 0){
                                deltaArray.add(innerDelta);
                            }
                            innerDelta = objectMapper.readTree("{}");
                        }
                        ((ObjectNode) delta).set(field, deltaArray);
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
