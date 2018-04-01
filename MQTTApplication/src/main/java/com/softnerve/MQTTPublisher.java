package com.softnerve;

public class MQTTPublisher {
    public static void main(String[] args) {
        MQTTApp mqttApp = new MQTTApp("iot/mqtt", "tcp://iot.eclipse.org",
                "TestPublisher");
        
        mqttApp.publish("Hello MQTT!");
    }

}
