package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import config.SpaceConfig;
import tuplas.Message;
import tuplas.User;

public class Chat extends JFrame {
    private SpaceConfig spaceConfig;
    private JTextArea chatArea;
    private JTextArea messageArea;
    private JPanel panel = new JPanel();
    private User currentUser;
    private User destinyUser;


    public Chat() {
        setTitle("Chat de Mensagens");
        createUser();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        panel.setLayout(new BorderLayout());
        createMessaArea();
        sendMessage();
        add(panel);
        setVisible(true);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }


    private void sendMessage() {
        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (destinyUser == null) {
                    destinyUser = spaceConfig.findOtherUser(currentUser);
                }
                String message = messageArea.getText();
                if (!message.isBlank()) {
                    Message newMessage = new Message(message, currentUser, destinyUser);
                    spaceConfig.sendMessage(newMessage);
                    appendMessage(currentUser.username.toUpperCase(Locale.ROOT) + ": " + message);
                    messageArea.setText("");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        panel.add(buttonPanel, BorderLayout.LINE_END);
    }

    private void createMessaArea() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        panel.add(chatScrollPane, BorderLayout.CENTER);
        messageArea = new JTextArea(3, 1);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        panel.add(messageScrollPane, BorderLayout.PAGE_END);
    }

    private void createUser() {
        spaceConfig = SpaceConfig.getInstance();
        currentUser = spaceConfig.createUser();

        Thread thread = new Thread(listenMessages);
        thread.setDaemon(true);
        thread.start();
    }





    // FICAR ESCUTANDO AS MENSAGENS QUE CHEGAM DO OUTRO USUARIO
    Runnable listenMessages = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Message incomingMessage = new Message();
                incomingMessage.reciever = currentUser;
                incomingMessage.monitored = true;
                try {
                    Message recievied = (Message) spaceConfig.space.take(incomingMessage, null, 500);
                    if (recievied != null) {
//                        handleSuspeciousword(recievied);
                        appendMessage(recievied.sender.username.toUpperCase(Locale.ROOT) + ": " + recievied.content);
                        recievied = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Algo deu errado lendo mensagens");
                }
            }
        }
    };

}
