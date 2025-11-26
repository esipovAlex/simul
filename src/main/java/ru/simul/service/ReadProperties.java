package ru.simul.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream in = InitWorld.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
