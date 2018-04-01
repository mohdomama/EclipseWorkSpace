package com.softnerve;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MQTTApp {
    private String topic = "Default Topic";
    int qos = 2;
    private String brokerURL = "Default Broker";
    private String clientID = "DefaultClient";
    MemoryPersistence persistence  = new MemoryPersistence();
    
    public MQTTApp(String topic, String brokerURL, String clientID){
        this.topic = topic;
        this.brokerURL = brokerURL;
        this.clientID = clientID;       
    }
    
    public void publish(String content) {
        try {
            MqttClient client = new MqttClient(brokerURL, clientID, persistence);
            MqttConnectOptions connect_options = new MqttConnectOptions();
            connect_options.setCleanSession(true);
            
            System.out.println("Establishing connection to: " + brokerURL);
            client.connect(connect_options);
            System.out.println("Connected");
            
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
            System.out.println("Message published!");
            
            client.disconnect();
            System.out.println("Disconnected!");                        
        } catch (MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    public void listen(){
        try {

            // mqtt client with specific url and client id
            System.out.println("Connecting to: " + brokerURL);
            MqttClient client = new MqttClient(brokerURL, clientID, persistence);
            client.connect();
            System.out.println("Connected!");
            
            System.out.println("Subscribing to topic '" + topic + "' from "
                    + client.getServerURI());
            // Subscribing to specific topic
            client.subscribe(topic);
            System.out.println("Subscribed!");
            

            // It will trigger when a new message is arrived
            MqttCallback callback = new MqttCallback() {
      
                public void connectionLost(Throwable arg0) {
                    System.out.println("Connection lost");
                }


                public void deliveryComplete(IMqttDeliveryToken arg0) {
                    // TODO Auto-generated method stub
                    
                }

                public void messageArrived(String arg0, MqttMessage arg1)
                        throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println("Message:"
                            + new String(arg1.getPayload()));
                    
                } 
            };
            // Continue waiting for messages until the Enter is pressed
            System.out.println("Listening...");
            client.setCallback(callback);
            System.out.println("Press <Enter> to exit");
            try {
                System.in.read();
            } catch (IOException e) {
                // If we can't read we'll just exit
            }
            client.disconnect();
            System.out.println("Client Disconnected");

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
    
}
