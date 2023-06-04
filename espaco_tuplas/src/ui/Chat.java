package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import config.SpaceConfig;
import tuplas.Espiao;
import tuplas.Message;
import tuplas.User;

public class Chat extends JFrame {
    private SpaceConfig spaceConfig;
    private JTextArea chatArea;
    private JTextArea messageArea;
    private JPanel panel = new JPanel();
    private User currentUser;
    private User destinyUser;
    private Espiao espiao = new Espiao();
    private DefaultListModel<String> palavrasSuspeitas = new DefaultListModel<String>();

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

        JButton suspeciousWords = new JButton("Palavras Supeitas");
        suspeciousWords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showSuspeciousWords();
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        buttonPanel.add(suspeciousWords);
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
        spaceConfig.setSpie(espiao);
        Thread thread = new Thread(listenMessages);
        thread.setDaemon(true);
        thread.start();
    }


    //MODAL PARA APRESENTAR AS PALAVRAS SUSPEITAS E CADASTRAR NOVAS
    public void showSuspeciousWords() {
        JDialog dialog = new JDialog(this, "Palavras Suspeitas", true);
        dialog.setLayout(new BorderLayout());

        JList<String> _palavrasSupeitas = new JList<String>(palavrasSuspeitas);
        JScrollPane scrollPane = new JScrollPane(_palavrasSupeitas);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Adicionar Palavra Suspeita");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newWord = JOptionPane.showInputDialog(dialog, "Digite a nova palavra suspeita:");
                if (newWord != null && !newWord.isEmpty()) {
                    spaceConfig.addPalavraSuspeita(espiao, newWord);
                    palavrasSuspeitas.addElement(newWord);
//                    model.addElement(newWord);
                }
            }
        });
        buttonPanel.add(addButton);
        dialog.add(buttonPanel, BorderLayout.PAGE_END);


        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    // FICAR ESCUTANDO AS MENSAGENS QUE CHEGAM DO OUTRO USUARIO
    Runnable listenMessages = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Message incomingMessage = new Message();
                incomingMessage.reciever = currentUser;
                try {
                    Message recievied = (Message) spaceConfig.space.take(incomingMessage, null, 500);
                    if (recievied != null) {
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
