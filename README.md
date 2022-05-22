# SoPra FS22 - Server of The Game

## Introduction

The Game is a card game invented by Steffen Benndorf where the Goal is to play all cards on the 4 decks in the right order.
The Goal of this software is to be able to play The Game remote und in real time with your friends.

This repository of the server side of The Game and needs one other server and the client side to run ([client](https://github.com/sopra-fs22-group-11/SoPra22-group11-TheGame-Client) and [Zoom server](https://zoomvideosdk-signature.herokuapp.com/))

The scope of this project is to have a running application and a playable Game, specifically:
- you can login and adjust your profile
- you can waite in a waiting room until are your friends have joined
- in the game, all restrictions from the rules are implemented
- a User is able to leave the game at any time
- the users are able to have a voice call during the Game

Out of scope for this project:
- having a bug free application
- playing different games on the same time
- storing on a separate database all the users and scores

## Technologies

### Client

On the client side is written in JavaScript using React and Node.js. For the UI we are using CSS and SCSS.
A detailed description you will find in the [client repository](https://github.com/sopra-fs22-group-11/SoPra22-group11-TheGame-Client)


### Server

On the Server side we are using Java with bootRun. 

Tutorials:
-   Documentation: https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
-   Guides: http://spring.io/guides
    -   Building a RESTful Web Service: http://spring.io/guides/gs/rest-service/
    -   Building REST services with Spring: http://spring.io/guides/tutorials/bookmarks/

## High Level components

Header

Sockclient

Waitingroom

Game



## Launch & Deployment

For your local development environment, you will need Node.js. You can download it [here](https://nodejs.org). All other dependencies, including React, get installed with:

```npm install```

To run the app use:

`npm run dev`

It will open the app in [http://localhost:3000](http://localhost:3000) in your default browser

Deployment will be done with [CLI](https://devcenter.heroku.com/articles/heroku-cli) from Heroku.
When you are logged in you can run:

`git heroku push master`

The application will be deployed. Find more about it [here](https://stackoverflow.com/questions/71892543/heroku-and-github-items-could-not-be-retrieved-internal-server-error).


## Illustrations

Before you can enter The Game you needs to Register or Login.


After that you will be redirected to the Start page where you have different options. You can see the Rules, check the scores, edit your profile
or play a Game.


When clicking on "Lets play" you will be redirected to the waitingroom, where you can see which player are also waiting
for The Game to start. When one of the other player clicks on start, the Game will beginn for all of them.


In the Game, they can see all their cards displayed in front of them. You can also see how many cards the other players have. In the
middle there are the four piles and the draw pile. When clicking on one of you cards you the card is selected and you can chose on which pile
you want to play the card.

When The Game is won, it will finish automatically and you will see that 100 point will be added to your score.


You can decided if you want to play again or leave. When leaving you will be redirected to the start page.


## Roadmap

As described above, there are some features which are not scope of this project. The following features can be added to the tool:
- playing different games on the same time
- storing on a separate database all the users and scores
- add on other voice API which is not chargeable

We thank in advance to all the voluntary developer who have fun to improve our Game. Please make sure to tag us :)

## Authors and acknowledgment

Authors of this project:
- [najma98](https://github.com/najma98)
- [saro7890](https://github.com/Saro7890)
- [mariinja](https://github.com/Mariinja)
- [Desteb](https://github.com/Desteb)
- [tikost](https://github.com/tikost)

We thank the whole TA Team of the SoPra 2022 course which helped us during the creation on this project and our TA Jan Kreischer for supporting us.
> A Special thanks to Lucas Pelloni and Kyrill Hux for working on the template and answering our questions.

## License




## Setup this Template with your IDE of choice

Download your IDE of choice: (e.g., [Eclipse](http://www.eclipse.org/downloads/), [IntelliJ](https://www.jetbrains.com/idea/download/)), [Visual Studio Code](https://code.visualstudio.com/) and make sure Java 15 is installed on your system (for Windows-users, please make sure your JAVA_HOME environment variable is set to the correct version of Java).

1. File -> Open... -> SoPra Server Template
2. Accept to import the project as a `gradle project`

To build right click the `build.gradle` file and choose `Run Build`

### VS Code
The following extensions will help you to run it more easily:
-   `pivotal.vscode-spring-boot`
-   `vscjava.vscode-spring-initializr`
-   `vscjava.vscode-spring-boot-dashboard`
-   `vscjava.vscode-java-pack`
-   `richardwillis.vscode-gradle`

**Note:** You'll need to build the project first with Gradle, just click on the `build` command in the _Gradle Tasks_ extension. Then check the _Spring Boot Dashboard_ extension if it already shows `soprafs22` and hit the play button to start the server. If it doesn't show up, restart VS Code and check again.

## Building with Gradle

You can use the local Gradle Wrapper to build the application.
-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

## API Endpoint Testing

### Postman

-   We highly recommend to use [Postman](https://www.getpostman.com) in order to test your API Endpoints.

## Debugging

If something is not working and/or you don't know what is going on. We highly recommend that you use a debugger and step
through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

## Testing

Have a look here: https://www.baeldung.com/spring-boot-testing
