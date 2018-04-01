package com.softnerve;

public class MQTTSubscriber {
    public static void main(String[] args) {
        MQTTApp mqttApp1 = new MQTTApp("iot/mqtt", "tcp://iot.eclipse.org",
                "TestPublisher");
        
        mqttApp1.listen();
    }
}
