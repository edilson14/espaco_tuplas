package ui;

import config.SpaceConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JFrame {
    private SpaceConfig spaceConfig;
    private JTextArea chatArea;
    private JTextArea  messageArea;
    public Chat(){
        setTitle("Chat de Mensagens");
        spaceConfig = SpaceConfig.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        panel.add(chatScrollPane, BorderLayout.CENTER);

        messageArea = new JTextArea(3, 1);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        panel.add(messageScrollPane, BorderLayout.PAGE_END);

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageArea.getText();
                appendMessage("Você: " + message);
                messageArea.setText("");
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        panel.add(buttonPanel, BorderLayout.LINE_END);
        add(panel);
        setVisible(true);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

}
