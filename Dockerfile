FROM openjdk:11
EXPOSE 8089
ADD target/kaddem-1.0.jar.jar kaddem-1.0.jar
ENTRYPOINT ["java","-jar","/kaddem.jar"]