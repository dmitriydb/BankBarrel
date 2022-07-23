FROM adoptopenjdk/openjdk8
ADD target/bankbarrel-webapp-2.6.7.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bankbarrel-webapp-2.6.7.jar"]
EXPOSE 8888