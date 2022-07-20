FROM adoptopenjdk/openjdk8
ADD target/bankbarrel-serviceregistry-2.6.7.jar /home
WORKDIR ["/home"]
CMD ["java", "-jar", "/home/bankbarrel-serviceregistry-2.6.7.jar"]
EXPOSE 8181