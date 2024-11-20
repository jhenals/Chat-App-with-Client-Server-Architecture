# Chat-Application-with-Client-Server-Architecture
This is a simple chat application implemented in Java that demonstrates the basics of client-server communication using sockets. It features a graphical user interface (GUI) built with Swing and enables real-time text-based communication between a server and a client.

## Features
1. Server-Client Communication
  - A server listens for incoming client connections on a predefined port.
  - The client can connect to a specified server host to initiate a chat.

2. Graphical User Interface
  - Chat windows for each connection with a simple, clean and user-friendly interface.
  - Real - time messages display with input fields for text.

3. Host Selection
  - User's can choose the target server host from a pre-configured list (dummy list)

4. Multi-Threaded Design
   - The server uses threading to handle connections (ensuring responsiveness specially for multiple clients)


## Technologies Used
* Java (Core libraries for networking(java.net) and multithreading
* Swing (Used to build the graphical user interface for the chat windows)

## Usage
1. Run the ```ChatNode``` class to start the server.
2. Select a host to initiate a chat as a client.
    PS: You can add new hosts per line inside the ```hosts.txt``` file.
3. Exchange messages between the client and server in real-time.


## Future Enhancements
* Support for multiple clients simultaneously in a group chat.
* Encryption for sercure message exchange.
* Persistent chat history
* Network Diagnostics and Error Handling
* Peer-to-peer mode (without a central server)
