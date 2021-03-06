# springboot-readinglist
Sample project following the instructions of the book Spring In Action. I tried to include each aspect from every chapter.

## Chapter 02: Developing your first Spring Boot application
Simple Spring Boot MVC Application with the following chapter topics
* Auto-Configuration: for H2 as data source, Hibernate as entity manager, repository creation from interface, Thymeleaf
* Conditional Configuration: Used ```@Conditional``` annotation as an example

## Chapter 03: Overriding Spring Boot auto-configuration
* Securing application with ```spring-boot-starter-security```, including a custom login page
* HTTPS over SSL with custom Keys (new url: https://localhost:8443/readingList/yourName)
* using YAML as file format for application properties
* externalizing a part of the properties to a bean based on a prefix
* logging to file
* defining the log level with profiles
* custom error page

## Chapter 04: Testing with Spring Boot
* Web Testes
* Security Tests