package com.softnerve;

public class MQTTSubscriber {
    public static void main(String[] args) {
        MQTTApp mqttApp1 = new MQTTApp("iot/mqtt", "tcp://broker.mqttdashboard.com",
                "TestPublisher");
        
        mqttApp1.listen();
    }
}
