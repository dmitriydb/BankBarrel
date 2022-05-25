FROM adoptopenjdk/openjdk8
ADD target/bb-monitoring-webapp-2.7.0.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bb-monitoring-webapp-2.7.0.jar"]