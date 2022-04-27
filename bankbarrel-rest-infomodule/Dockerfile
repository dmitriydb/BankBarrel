FROM adoptopenjdk/openjdk8
ADD target/bankbarrel-rest-infomodule-2.6.7.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bankbarrel-rest-infomodule-2.6.7.jar"]
EXPOSE 8887