package chat;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatWindow extends JFrame {
  private PrintWriter out;
  private BufferedReader in;
  private JTextArea chatArea;
  private JTextField inputField;

  public ChatWindow(String title, Socket socket, int pos1, int pos2) {
    super(title);

    // Imposta layout
    chatArea = new JTextArea(20, 50);
    chatArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(chatArea);

    inputField = new JTextField(40);
    JButton sendButton = new JButton("Send");

    JPanel panel = new JPanel();
    panel.add(inputField);
    panel.add(sendButton);

    // Configura finestra
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
    add(panel, BorderLayout.SOUTH);
    setLocation(pos1, pos2);
    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);

    // Configura comunicazione
    try {
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Thread per leggere i messaggi in arrivo
      new Thread(() -> {
        try {
          String message;
          while ((message = in.readLine()) != null) {
            chatArea.append(socket + ": " + message + "\n");
          }
        } catch (IOException e) {
          chatArea.append("Connection closed.\n");
        }
      }).start();

      // Azione sul pulsante di invio
      sendButton.addActionListener(e -> sendMessage());
      inputField.addActionListener(e -> sendMessage());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sendMessage() {
    String message = inputField.getText();
    if (!message.isEmpty()) {
      chatArea.append("Me: " + message + "\n");
      out.println(message);
      inputField.setText("");
    }
  }
}
