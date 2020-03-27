# JCord
https://github.com/coleman2246/JCord
 * Version: 0.8
## Project Description
Text chat application design to communicate between users using the application.
## Contributors
* Cole Mollica
     * Roles: Back-End, Project Planning, GUI
     * [Github Profile](https://github.com/coleman2246)
     * Student ID: 100706187
* Anthony Huang
     * Roles: Back-End, Project Planning, GUI
     * [Github Profile](https://github.com/Nycarus)
     * Student ID: 100711927
* Abdullah Riaz Raja
     * Roles: GUI
     * [Github Profile](https://github.com/abdullah-riaz-raja)
     * Student ID: 100693348
* Nishchal Bhardwaj
     * Roles: GUI
     * [Github Profile](https://github.com/Nishchal2309)
     * Student ID: 100706116
* Azeem Syed Hussaini
     * Roles: GUI     
     * [Github Profile](https://github.com/Azeem-Hussaini)
     * Student ID: 100707191
## How to run
* Requires Gradle 6.3. If another version is used there may be an issue with paths files which may break the css/entire thing. Written with Java version 11.05. 
* To run the application a server must first be started. 
* Server information can be configured in the **JCord/Client/src/main/resources/communication.json**.
* Requires the server to first be open before the client can open their app. Portforwarding is required if not connecting locally.
* Go to the root of the project and go to **Client** folder
     * Start server: gradle server
     * Open application: gradle run
## Current Features
* Account Creation 
* Sending/Receiving Messages
* Emojis
* Displaying Users (Partially Implemented)
* File Attachement (Work in progress)
## Features Towards version 2.0
* Login System
* Server Channels
* Data Base
* Voice Chat
