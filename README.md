### Problem statement
Let's assume for this assessment that you work as part of a team that splits a monolithic application into
several microservices. It's an application that is used as an internal social network, basically like Facebook
but then within a company. Every customer creates a network, consisting of the employees of the customer
and a simple timeline with messages for them to communicate. Let's assume we have 3 services, a
Customer service, an Authentication service and a Timeline Service. The Customer service is responsible
for managing all data for a customer, the timeline service serves a timeline and the authentication service
checks if you're allowed to sign in. The memberships are created in the Customer service and needs to be
shared with the Authentication service somehow. Memberships can also become inactive (indicated by the
status enum) and that needs to be checked to allow access to the timeline.

###Background
Please keep in mind that this schedule is part of the assessment, so it can use some improvement or
changes as well
The main challenge for you to solve is the integration between those apps, as they need to exchange data.
These problems needs to be solved using Kafka, without building the full services. You should deliver a
working project containing consumers, producers and other Kafka components that are helpul solving the
problems. You can assume that someone else builds the full service in Docker, this is just about Kafka and
building the compontents.
Please make sure that when you create a new customer that the membership data is shared with the
authentication service. Memberships can be changed later or being revoked. You can assume that the
creation of memberships happen as part of the user creation.
Please take notes of everything you do and sent them along when submitting code for the assessment.
###Prerequisites
- Java 10
- gradle
- Docker
- Kafka

### Start order
Microservices depend on each other, so the start order should be:

- sn-auth-service
- sn-discovery-service
- sn-gateway-service
- sn-message-service
- sn-timeline-dashboard

### How to run
- build social-network
- run docker-compose file

### Events
- MessageCreated
- MessageDeleted
```
