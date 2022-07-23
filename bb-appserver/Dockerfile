FROM adoptopenjdk/openjdk8
ADD target/bb-appserver-2.6.7.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bb-appserver-2.6.7.jar"]
EXPOSE 8886