package chat;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ChatNode {
  private static final int PORT = 3001; //can be changed
  private static List<String> hostList = Arrays.asList("localhost"); //add other hostss


  public static void main(String[] args) {
    new Thread(
        ChatNode::startServer
    ).start();


    while (true) {
      String selectedHost = (String) JOptionPane.showInputDialog(
          null,
          "Selezione un host per la chat:",
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
      System.out.println("Connesso a " + selectedHost);
      new ChatWindow("Chat con " + selectedHost, s, frame1x, centerY);
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
      System.out.println("Server attivo sulla porta " + PORT);

      Socket client = ss.accept();
      System.out.println("Connessione ricevuta da " + client.getInetAddress());
      new ChatWindow("Chat con" + client.getInetAddress(), client, frame2x, centerY);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
