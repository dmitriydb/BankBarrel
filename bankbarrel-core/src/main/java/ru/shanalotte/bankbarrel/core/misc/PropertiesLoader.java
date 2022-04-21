package ru.shanalotte.bankbarrel.core.misc;

import java.io.InputStream;
import java.util.Properties;

/**
 * Class for loading application properties for non-managed by Spring classes.
 */
public class PropertiesLoader {

  private static Properties properties;

  static {
    try {
      Properties configuration = new Properties();
      InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream("application.properties");
      configuration.load(inputStream);
      inputStream.close();
      properties = configuration;
    } catch (Throwable ex) {
      ex.printStackTrace();
    }

  }

  public static String get(String propertyKey) {
    return properties.getProperty(propertyKey);
  }


}
