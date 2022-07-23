FROM adoptopenjdk/openjdk8
ADD target/bb-jwt-provider-2.7.1.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bb-jwt-provider-2.7.1.jar"]
EXPOSE 8892