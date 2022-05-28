# SoPra FS22 - Server of The Game

## Introduction

The Game is a card game invented by Steffen Benndorf where the Goal is to play all cards on 4 decks in the right order. 
The Goal of this software is to be able to play The Game remote and in real time with your friends.

This repository of the server side of The Game and needs one other server and the client side to run ([client](https://github.com/sopra-fs22-group-11/SoPra22-group11-TheGame-Client) and [Zoom server](https://zoomvideosdk-signature.herokuapp.com/))

The scope of this project is to have a running application and a playable Game, specifically:
- you can login and adjust your profile
- you can wait in a waiting room until your friends have joined
- in the game, all restrictions from the rules are implemented
- a user is able to leave the game at any time
- the users are able to have a voice call during the game

Out of scope for this project:
- having a bug free application
- playing different games at the same time
- storing all the users and scores on a separate database 

## Technologies


### Server

We are using Java on the server side with the Spring Boot framework.
For deployment we are using Heroku.

As soon as a waitingroom is joined we establish a websocket connection. 
Otherwise the connection between server and client is done with REST.


### Client

The client side is written in JavaScript using React and Node.js. For the UI we are using CSS and SCSS.
A detailed description you will find in the [client repository](https://github.com/sopra-fs22-group-11/SoPra22-group11-TheGame-Client)



## High Level components

The [UserController](src/main/java/ch/uzh/ifi/hase/soprafs22/controller/UserController.java) handles the REST requests from the client. With help from the [UserService](src/main/java/ch/uzh/ifi/hase/soprafs22/service/UserService.java), it handles the given data, like updating profile information of users or changing the scores after each game.

The [WebSocketController](src/main/java/ch/uzh/ifi/hase/soprafs22/controller/WebSocketController.java) is used during a game to communicate with the [SockClient](src/components/utils/sockClient.js) from the Client repository. When the SockClient sends a request to the WebSocketController, it applies the changes that happend in the client to the game object in the server. Then it returns a [TransferGameObject](src/main/java/ch/uzh/ifi/hase/soprafs22/entity/TransferGameObject.java) which is easier to handle for the client.

For each game round an object of the class [game](src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Game.java) is created, which memorizes the respective info, such as the list of [players](src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Player.java),
the [deck](src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Deck.java) and [piles](src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Pile.java). Here are all the methods which are used by the WebSocketController to modify the game object. For example, we can initialize a new game, update whose turn it is or check if the game is terminated.



## Launch & Deployment
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

## Roadmap

As described above, there are some features which are not in the scope of this project. The following features can be added to the tool:
- playing different games at the same time
- storing all the users and scores on a separate database 
- add another voice API which is not chargeable

We give thanks in advance to all the voluntary developers who have fun to improve our game. Please make sure to tag us :)

## Authors and acknowledgment

Authors of this project:
- [Najma Christen](https://github.com/najma98)
- [Sandra Rosch](https://github.com/Saro7890)
- [Marinja Principe](https://github.com/Mariinja)
- [Deborah Stebler](https://github.com/Desteb)
- [Tijana Kostovic](https://github.com/tikost)

We thank the whole TA Team of the SoPra 2022 course which helped us during the creation on this project and our TA Jan Kreischer for supporting us.
> A Special thanks to Lucas Pelloni and Kyrill Hux for working on the template and answering our questions.

## License

This project is licensed under [Apache-2.0](LICENSE).

