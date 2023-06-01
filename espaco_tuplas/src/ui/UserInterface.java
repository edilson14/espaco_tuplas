package ui;

import config.SpaceConfig;
import tuplas.Message;
import tuplas.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserInterface extends JFrame {

    private SpaceConfig spaceConfig;
    public UserInterface() {
        super("Espaço de Tuplas");
        spaceConfig = SpaceConfig.getInstance();


        // cria os botões


        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // cria um painel para uma lista com um rótulo e um botão de ação
    private JPanel createListPanel(JList<String> list, String label, JButton button) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        if (button != null) {
            panel.add(button, BorderLayout.SOUTH);
        }
        return panel;
    }


    public static String[] convertToStringArray(Object[] objectArray) {
        String[] stringArray = new String[objectArray.length];
        for (int i = 0; i < objectArray.length; i++) {
            stringArray[i] = objectArray[i].toString();
        }
        return stringArray;
    }
    private void showMessages(String user){
      User selectedUser = spaceConfig.getUserByName(user).get();
      List<Message> userMessages = spaceConfig.listenMessage(selectedUser);
      JDialog dialog = new JDialog(this,"Mensagens Recebidas",true);
      JPanel panel = new JPanel(new GridLayout(1,1));
      JList messages = new JList(userMessages.stream().map(_message -> _message.sender + ": "+ _message.content).toArray());

      panel.add(createListPanel(messages,"Mensagens",null));
      dialog.getContentPane().add(panel);
      dialog.pack();
      dialog.setLocationRelativeTo(this);
      dialog.setVisible(true);
    }

}