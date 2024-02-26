// README.md
# Campaign Manager
### Introduction
Welcome to the Campaign Manager project repository! This project is a demonstration of our expertise in Java, Spring Boot, and Hibernate, showcasing our ability to develop a robust and efficient campaign management system. The system includes features for creating and managing campaigns, targeting specific audiences, and tracking campaign performance. Our focus on scalability, security, and user experience makes this project a valuable learning resource for developers and businesses alike. Explore the codebase, contribute, and leverage our expertise to enhance your own projects!
### Project Support Features
* Campaign Creation: Ability to create new advertising campaigns. 
* Audience Targeting: Functionality to define and target specific audience segments. 
* Campaign Management: Tools for managing and monitoring active campaigns.
* Performance Tracking: Metrics and analytics for tracking campaign performance.
* User Authentication: Secure user authentication and authorization mechanisms.
* Scalability: Design considerations for scalability to handle large volumes of campaigns and users.
* Security: Implementation of security best practices to protect user and campaign data.
* User Experience: Focus on providing an intuitive and efficient user experience.
* Documentation: Comprehensive documentation to facilitate understanding and usage of the project.
* Open Source: Available as an open-source project for collaboration and further development.
* Campaign scheduling 
* Targeting campaigns using user ID 
* Targeting campaigns using email ID (registers users for future use)
* Tracking target audience 
* Automatic campaign triggering based on schedule time
### Installation Guide
* Clone this repository [here](https://github.com/blackdevelopa/ProjectSupport.git).
* The develop branch is the most stable branch at any given time, ensure you're working from it.
* Build the project using maven.
* Run the jar file.
### API Endpoints
| HTTP Verbs | Endpoints             | Action                                                                                           |
|------------|-----------------------|--------------------------------------------------------------------------------------------------|
| POST       | /api/user/add         | To sign up a new user account                                                                    |
| GET        | /api/user/{id}        | To fetch masked user details                                                                     |
| POST       | /api/email/send       | Send a campaign to an audience directly                                                          |
| GET        | /api/email/all        | To fetch all the notifications                                                                   |
| GET        | /api/email/           | To fetch particular notification using notification id<br/> specificed in the request parameters |
| GET        | /api/campaign/        | Get Campaign details and performance report                                                      |
| POST       | /api/campaign/add     | To create a new campaign                                                                         |
| POST       | /api/campaign/trigger | To trigger/launch/schedule a campaign                                                            |
| POST       | /api/authenticate     | To authenticate and fetch JWT token                                                              |
### Technologies Used
* [Spring Boot](https://spring.io/projects/spring-boot)  is a framework that simplifies the development of Java applications by providing a set of conventions and ready-to-use components.
* [Spring JPA](https://spring.io/projects/spring-data-jpa)  is part of the larger Spring Data framework and provides a higher-level abstraction for working with JPA (Java Persistence API) implementations in a Spring application.
* [Spring Hibernate](https://hibernate.org/) is not a specific technology or framework, but it's common to use Spring with Hibernate, which is a popular ORM (Object-Relational Mapping) framework for Java applications.
* [MySQL](https://www.mysql.com/) is a popular open-source relational database management system (RDBMS) that is widely used for web applications.
* [Mockito](https://site.mockito.org/) is a popular Java mocking framework that is used for unit testing of Java applications. It allows developers to create mock objects in tests to simulate the behavior of real objects.
### License