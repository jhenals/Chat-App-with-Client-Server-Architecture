package chat;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ChatNode {
  private static final int PORT = 3001; //can be changed
  //private static final List<String> hostList = Arrays.asList("localhost"); //can add other hosts

  private static List<String> hostList;

  static {
    try {
      hostList = Files.readAllLines(Paths.get(ChatNode.class.getResource("hosts.txt").toURI())); //files inside src directory are typically trated as resources in Java -> use the resource loading mechanism instead of a direct file path.
    } catch (IOException | URISyntaxException e) {
      System.err.println("Error loading hosts. Make sure the file hosts.txt exists.");
      hostList = Arrays.asList("localhost");
    }
  }

  public static void main(String[] args) {
    new Thread(
        ChatNode::startServer
    ).start();


    while (true) {
      String selectedHost = (String) JOptionPane.showInputDialog(
          null,
          "Select host:",
          "Client",
          JOptionPane.QUESTION_MESSAGE,
          null,
          hostList.toArray(),
          hostList.get(0)
      );

      if (selectedHost != null)
        startClient(selectedHost);
      else
        System.exit(0);
      break;
    }

  }

  private static void startClient(String selectedHost) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    int frame1x = (int) (screenWidth * 0.55);
    int centerY = (int) (screenHeight * 0.3);
    try {
      Socket s = new Socket(selectedHost, PORT);
      System.out.println("Connected to" + selectedHost);
      new ChatWindow("Chat with " + selectedHost, s, frame1x, centerY);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void startServer() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    int frame2x = (int) (screenWidth * 0.2);
    int centerY = (int) (screenHeight * 0.3);
    try {
      ServerSocket ss = new ServerSocket(PORT);
      System.out.println("Serve is active in port " + PORT);

      Socket client = ss.accept();
      System.out.println("Connection received from " + client.getInetAddress());
      new ChatWindow("Chat with " + client.getInetAddress(), client, frame2x, centerY);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
