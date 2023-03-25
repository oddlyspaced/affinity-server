# Affinity - Server

Affinity is meant to be an easy to deploy and use platform for any person/organisation wanting to publicize their offerings in their area.

Note: affinity is a 2 part project, this repo contains the server codebase. You can checkout the app part of the project here.

## About
Affinity is meant to be used by organisations or individuals that offer some form of service and want to publicize their offerings to people in their area, but while doing so do not want to involve a congolomerate service platform as their basis. Affinity provides a solution by providing a fully self sufficient deployable platform that anyone can deploy on their server and then use it as a basis to share and deploy apps to the people.

## Setting up
Step 1: Clone the server repo to the server where the project is going to be deployed

    git clone https://github.com/oddlyspaced/affinity-server.git

Step 2: Open `AffinityConfiguration` class and edit the class parameters according to your requirements. All the parameters and their applications are documented in the class itself.

Step 3: Run the server by using the command in the root of project :

    ./gradlew run

That should be it for deploying the server. The deployment can be tested by visiting the `ping` endpoint on the ip address/domain at the port you configured in the file.

Example:
http://srivalab-compute.cse.iitk.ac.in:5030/ping

If everything went well, the ping endpoint should return a minimal json as following:

    {"message":"pong","error":false}

## Requirements
- Java Runtime, Java Development Kit

## Technologies Used
- Kotlin
- KTor
- Coroutines

## Contributors
- Hardik Srivastava ([@oddlyspaced](https://github.com/oddlyspaced))
