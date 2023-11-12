FROM openjdk:11
EXPOSE 8089
ADD target/AcademicDevopsProject.jar AcademicDevopsProject.jar
ENTRYPOINT ["java","-jar","/AcademicDevopsProject.jar"]