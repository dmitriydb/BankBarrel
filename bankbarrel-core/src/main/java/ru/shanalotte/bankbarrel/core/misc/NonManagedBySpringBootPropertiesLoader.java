package ru.shanalotte.bankbarrel.core.misc;

import java.io.InputStream;
import java.util.Properties;

public class NonManagedBySpringBootPropertiesLoader {
  private static Properties properties;
  static {
    try (InputStream inputStream = NonManagedBySpringBootPropertiesLoader.class
        .getClassLoader()
        .getResourceAsStream("application.properties")) {
      Properties configuration = new Properties();
      configuration.load(inputStream);
      properties = configuration;
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
  }

  public static String get(String propertyKey) {
    return properties.getProperty(propertyKey);
  }
}
