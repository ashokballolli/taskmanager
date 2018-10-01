package utils;

import org.apache.commons.configuration.*;

public class ReadConfig {
    public static Configuration configuration;

    public static String getProperty(String key) {
        try {
            configuration = new PropertiesConfiguration(Thread.currentThread()
                    .getContextClassLoader().getResource("application.properties"));
        } catch (ConfigurationException e) {
            System.err.println(e.getMessage());
        }
        return configuration.getProperty(key).toString();
    }
}
