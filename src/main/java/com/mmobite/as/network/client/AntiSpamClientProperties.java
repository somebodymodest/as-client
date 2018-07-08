package com.mmobite.as.network.client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiSpamClientProperties {
    private static Logger log = LoggerFactory.getLogger(AntiSpamClientProperties.class.getName());

    public static boolean ENABLED;
    public static String WORLD_GUID;
    public static String SERVER_ADDR;
    public static int PORT_CTRL;
    public static int PORT_DATA;
    public static int RECONNECT_TIMEOUT;
    public static int READ_TIMEOUT;

    public static void load(String configPath) {
        try {
            Properties prop = new Properties();
            InputStream stream = new FileInputStream(configPath);
            prop.load(stream);
            stream.close();

            ENABLED = Boolean.parseBoolean(prop.getProperty("ENABLED", "true"));
            SERVER_ADDR = prop.getProperty("ADDRESS", "127.0.0.1");
            WORLD_GUID = prop.getProperty("WORLD_GUID", "076BA8A9-1CDA-4697-8E1D-E8F3044BF184");
            PORT_CTRL = Integer.parseInt(prop.getProperty("PORT_CTRL", "5556"));
            PORT_DATA = Integer.parseInt(prop.getProperty("PORT_DATA", "5555"));
            RECONNECT_TIMEOUT = Integer.parseInt(prop.getProperty("RECONNECT_TIMEOUT", "5"));
            READ_TIMEOUT = Integer.parseInt(prop.getProperty("READ_TIMEOUT", "-1"));
        } catch (Exception ex) {
            log.error("Load config exception: ", ex);
        }
    }
}
