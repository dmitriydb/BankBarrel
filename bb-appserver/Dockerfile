FROM adoptopenjdk/openjdk8
ADD target/bankbarrel-appserver-2.6.7.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bankbarrel-appserver-2.6.7.jar"]
EXPOSE 8886