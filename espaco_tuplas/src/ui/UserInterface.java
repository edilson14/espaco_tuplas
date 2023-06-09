package ui;

import config.SpaceConfig;
import tuplas.Ambiente;
import tuplas.Dispositive;
import tuplas.Message;
import tuplas.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInterface extends JFrame {

    private JButton createEnvButton, createUserButton, createDeviceButton,deleteAmbienteButton,deleteDeviceButton,deleteUserButtom;
    private SpaceConfig spaceConfig;
    private JButton removeUserButton, removeDeviceButton;
    private JList<String> envList, userList, deviceList , devicesFromSelectedAmbient,usersFromSelectedAmbient;

    private DefaultListModel<String> envNames = new DefaultListModel<String>();
    private  String[] envNamesBox = {};
    private DefaultListModel<String> devicesNames = new DefaultListModel<String>();
    private List<Dispositive> dispositives;

    private DefaultListModel<String> usersNames = new DefaultListModel<String>();
    private Integer lastEnvNumber =1;
    private Integer lastDeviceNumber = 1;
    private Integer lastUserNumber = 1;


    public UserInterface() {
        super("Espaço de Tuplas");
        spaceConfig = SpaceConfig.getInstance();
        this.dispositives = new ArrayList<Dispositive>();

        // cria os botões
        createEnvButton = new JButton("Criar Ambiente");
        deleteAmbienteButton = new JButton("Apagar Ambiente");
        deleteAmbienteButton.setEnabled(false);
        createEnvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarAmbiente();
            }
        });
        createDeviceButton = new JButton("Criar Dispositivo");
        createDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDevice();
            }
        });

        createUserButton = new JButton("Criar Usuário");

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });

        deleteDevice();
        deleteUser();
        deleteAmbiente();


        // cria as listas
        envList = new JList<String>(envNames);
        deviceList = new JList<String>(devicesNames);
        userList = new JList<String>(usersNames);

        // cria um painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(createEnvButton);
        buttonPanel.add(createDeviceButton);
        buttonPanel.add(createUserButton);

        // cria um painel para as listas
        JPanel listPanel = new JPanel(new GridLayout(1, 3));
        listPanel.add(createListPanel(envList, "Ambientes", deleteAmbienteButton));
        listPanel.add(createListPanel(deviceList, "Dispositivos", deleteDeviceButton));
        listPanel.add(createListPanel(userList, "Usuários", deleteUserButtom));

        // adiciona um ouvinte para o clique em um item da lista de ambientes
        envList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selectedEnv = envList.getSelectedValue();
                    Ambiente ambienteSelected = spaceConfig.getAmbienteByName(selectedEnv).get();
                    List<Dispositive> allAmbienteDispositives = spaceConfig.getDevicesByAmbienteId(ambienteSelected.ambienteId);
                    List<User> allAmbienteUsers = spaceConfig.getUsersByAmbienteId(ambienteSelected.ambienteId);
                    String[] devices = convertToStringArray(allAmbienteDispositives.stream().map(_devices -> _devices.name).toArray());
                    String[] users = convertToStringArray(allAmbienteUsers.stream().map(_user -> _user.username).toArray());
                    showEnvironmentDetails(selectedEnv,  convertToStringArray(spaceConfig.userList.stream().filter(_user -> _user.ambienteId != null && _user.ambienteId.equals(ambienteSelected.ambienteId)).map(_user -> _user.username).toArray()), devices);
                }
            }
        });

        envList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedEnv = envList.getSelectedValue();
                if(selectedEnv != null && spaceConfig.isAmbienteEmpty(spaceConfig.getAmbienteByName(selectedEnv).get().ambienteId)){
                    deleteAmbienteButton.setEnabled(true);
                } else {
                    deleteAmbienteButton.setEnabled(false);
                }

            }

        });

        envList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(spaceConfig.isAmbienteEmpty(spaceConfig.getAmbienteByName(envList.getSelectedValue()).get().ambienteId)){
                    deleteAmbienteButton.setEnabled(true);
                }
            }
        });


        // adiciona um ouvinte para o clique na lista de usuários
        userList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // habilita o botão Remover Usuário se um usuário estiver selecionado
                if (userList.getSelectedValue() != null) {
                    deleteUserButtom.setEnabled(true);
                } else {
                    deleteUserButtom.setEnabled(false);
                }
            }
        });

        // adiciona um ouvinte para o clique na lista de dispositivos
        deviceList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (deviceList.getSelectedValue() != null) {
                    deleteDeviceButton.setEnabled(true);
                } else {
                    deleteDeviceButton.setEnabled(false);
                }
            }

        });



        deviceList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                deviceClicked(evt);
            }
        });

        userList.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent event){
                userClicked(event);
            }
        });

        // adiciona os componentes à janela
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(listPanel, BorderLayout.CENTER);

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

    // mostra uma janela com os detalhes do ambiente
    private void showEnvironmentDetails(String env, String[] users, String[] devices) {
       final String selectedDevice;
        Ambiente ambienteSelected = spaceConfig.getAmbienteByName(env).get();
        JDialog dialog = new JDialog(this, "Detalhes do Ambiente " + env, true);
        JPanel panel = new JPanel(new GridLayout(2, 1));
         usersFromSelectedAmbient = new JList<String>(convertToStringArray(spaceConfig.userList.stream().filter(_user -> _user.ambienteId != null && _user.ambienteId.equals(ambienteSelected.ambienteId)).map(_user -> _user.username).toArray()));
        devicesFromSelectedAmbient = new JList<>(convertToStringArray(spaceConfig.dispositiveList.stream().filter(_device -> _device.ambienteid != null && _device.ambienteid.equals(ambienteSelected.ambienteId)).map(_device -> _device.name).toArray()));
        openUserOption(users);

        devicesFromSelectedAmbient.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(devicesFromSelectedAmbient.getSelectedValue() != null){
                    removeDeviceButton.setEnabled(true);
                }else {
                    removeDeviceButton.setEnabled(false);
                }
            }
        });

        usersFromSelectedAmbient.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(usersFromSelectedAmbient.getSelectedValue() != null){
                    removeUserButton.setEnabled(true);
                }else{
                    removeUserButton.setEnabled(false);
                }
            }
        });

        removeDevice(ambienteSelected.ambienteId);
        removeUser(ambienteSelected.ambienteId);
        panel.add(createListPanel(usersFromSelectedAmbient, "Usuários", removeUserButton));
        panel.add(createListPanel(devicesFromSelectedAmbient, "Dispositivos", removeDeviceButton));
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void criarAmbiente()  {
         try{
            Ambiente ambiente = new Ambiente(lastEnvNumber);
            spaceConfig.createAmbiente(ambiente);
             addAmbiente(ambiente.ambienteName);
            lastEnvNumber +=1;

        }catch (Exception e ){
            System.out.println("Algo deu errado na interface criando ambiente");
            e.printStackTrace();
         }
    }

    private void addAmbiente(String name){
        envNames.add(envNames.size(),name);
        String[] newEnxBox = new String[envNamesBox.length +1];
        System.arraycopy(envNamesBox,0,newEnxBox,0,envNamesBox.length);
        newEnxBox[newEnxBox.length -1] = name;
        envNamesBox = newEnxBox;
    }

    private void removeAmbienteFromEnvBox(String name){
        List<String> newList = new ArrayList<String>(Arrays.asList(envNamesBox));
        newList.remove(name);
        envNamesBox = newList.toArray(new String[0]);
    }


    private void createDevice(){
        try{
            Dispositive dispositive = new Dispositive(lastDeviceNumber);
            spaceConfig.creatDispoitive(dispositive);
            devicesNames.add(devicesNames.size(),dispositive.name);
            lastDeviceNumber +=1;
        }
        catch (Exception e){
            System.out.println("Algo deu errado criando dispositivo");
        }
    }

    private void deleteDevice(){
        deleteDeviceButton = new JButton("Apagar Dispositivo");
        deleteDeviceButton.setEnabled(false);

        deleteDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDevice = deviceList.getSelectedValue();
                if(selectedDevice != null){
                    spaceConfig.apagarDispositivo(selectedDevice);
                    devicesNames.removeElement(selectedDevice);
                }
            }
        });
    }

    private void deviceClicked(MouseEvent event){
        if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
            // obtém o dispositivo selecionado
            String selectedDevice = deviceList.getSelectedValue();
            // cria um diálogo para adicionar o dispositivo a um ambiente
            JDialog dialog = new JDialog(UserInterface.this, "Adicionar Dispositivo a um Ambiente", true);
            JPanel panel = new JPanel(new BorderLayout());
            JComboBox<String> envComboBox = new JComboBox<String>((String[]) envNamesBox);
            JButton addButton = new JButton("Adicionar");
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedEnv = (String) envComboBox.getSelectedItem();
                    if (selectedEnv != null && selectedDevice != null) {
                        Ambiente ambiente = spaceConfig.getAmbienteByName(selectedEnv).get();
                        Dispositive dispositive = spaceConfig.getDispositiveByName(selectedDevice).get();
                        if(dispositive.ambienteid == null){
                            try {
                                spaceConfig.addDeviceToAmbiente(dispositive,ambiente);
                                System.out.println("Dispositivo adicionado ao Ambiente");
                                dialog.dispose();
                            } catch (Exception ex) {
                                System.out.println("Não foi possivel adicionar dispositivo ao ambiente");
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            });
            panel.add(envComboBox, BorderLayout.NORTH);
            panel.add(addButton, BorderLayout.SOUTH);
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }




    private void createUser(){
        try{
            User user = new User(lastUserNumber);
            spaceConfig.createUser(user);
            usersNames.add(usersNames.size(),user.username);
            lastUserNumber+=1;
        } catch (Exception e){
            System.out.println("Algo deu errado criando usuario");
            e.printStackTrace();
        }
    }

    private void deleteUser(){
        deleteUserButtom = new JButton("Apagar Usuário");
        deleteUserButtom.setEnabled(false);

        deleteUserButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = userList.getSelectedValue();
                if(selectedUser != null){
                    spaceConfig.apagarUsuario(selectedUser);
                    usersNames.removeElement(selectedUser);
                }
            }
        });
    }

    private void deleteAmbiente(){
        deleteAmbienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEnv = envList.getSelectedValue();
                if(selectedEnv != null && deleteAmbienteButton.isEnabled()){
                    spaceConfig.deleteAmbiente(selectedEnv);
                    envNames.removeElement(selectedEnv);
                    removeAmbienteFromEnvBox(selectedEnv);
                }
            }
        });
    }

    private void userClicked(MouseEvent event){
        if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
            String selectedUser = userList.getSelectedValue();
            JDialog dialog = new JDialog(UserInterface.this, "Adicionar Usuaário a um Ambiente", true);
            JPanel panel = new JPanel(new BorderLayout());
            JComboBox<String> envComboBox = new JComboBox<String>((String[]) envNamesBox);
            JButton addButton = new JButton("Adicionar");
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedEnv = (String) envComboBox.getSelectedItem();
                    if (selectedEnv != null && selectedUser != null) {
                        Ambiente ambiente = spaceConfig.getAmbienteByName(selectedEnv).get();
                        User user = spaceConfig.getUserByName(selectedUser).get();
                            try {
                                spaceConfig.addUserAmbiente(user,ambiente);
                                System.out.println("Usuário adicionado ao Ambiente");
                                dialog.dispose();
                            } catch (Exception ex) {
                                System.out.println("Não foi possivel adicionar usuario ao ambiente");
                                throw new RuntimeException(ex);
                            }
                    }
                }
            });
            panel.add(envComboBox, BorderLayout.NORTH);
            panel.add(addButton, BorderLayout.SOUTH);
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    private void removeUser(Integer ambienteID){
        removeUserButton = new JButton("Remover Usuario");
        removeUserButton.setEnabled(false);
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = spaceConfig.getUserByName(usersFromSelectedAmbient.getSelectedValue()).get();
                spaceConfig.removeUserfromAmbiente(user);
                updateUserList(ambienteID);

                usersFromSelectedAmbient.clearSelection();
            }
        });

    }

    private void removeDevice(Integer ambienteID){
        removeDeviceButton = new JButton("Remover Dispositivo");
        removeDeviceButton.setEnabled(false);
        removeDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dispositive dispositive = spaceConfig.getDispositiveByName(devicesFromSelectedAmbient.getSelectedValue()).get();
                spaceConfig.removeDevicefromAmbiente(dispositive);
                updateDeviceList(ambienteID);
                devicesFromSelectedAmbient.clearSelection();
            }
        });
    }


        private void updateUserList(Integer ambienteId) {
            // Obter a nova lista de usuários após a remoção
            String[] updatedUsers = spaceConfig.userList.stream()
                    .filter(_user -> _user.ambienteId != null && _user.ambienteId.equals(ambienteId))
                    .map(_user -> _user.username)
                    .toArray(String[]::new);

            // Atualizar o modelo da lista com a nova lista de usuários
            DefaultListModel<String> userModel = new DefaultListModel<>();
            for (String user : updatedUsers) {
                userModel.addElement(user);
            }
            usersFromSelectedAmbient.setModel(userModel);
        }

    private void updateDeviceList(Integer ambienteId) {
        // Obter a nova lista de usuários após a remoção
        String[] updatedDevices = spaceConfig.dispositiveList.stream()
                .filter(_device -> _device.ambienteid != null && _device.ambienteid.equals(ambienteId))
                .map(_device -> _device.name)
                .toArray(String[]::new);

        // Atualizar o modelo da lista com a nova lista de usuários
        DefaultListModel<String> deviceModel = new DefaultListModel<>();
        for (String device : updatedDevices) {
            deviceModel.addElement(device);
        }
        devicesFromSelectedAmbient.setModel(deviceModel);
    }



    public static String[] convertToStringArray(Object[] objectArray) {
        String[] stringArray = new String[objectArray.length];
        for (int i = 0; i < objectArray.length; i++) {
            stringArray[i] = objectArray[i].toString();
        }
        return stringArray;
    }


    private void listenUserAmbienteList(JList<String> list){
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeDeviceButton.setEnabled(true);
            }
        });
    }



    private void openUserOption(String[] usersFromAmbiente){
        usersFromSelectedAmbient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event){
                if (SwingUtilities.isRightMouseButton(event)){
                    String selectedUser = usersFromSelectedAmbient.getSelectedValue();
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem sendMessage = new JMenuItem("Enviar Mensagem");
                    JMenuItem viewMessage = new JMenuItem("Ver Mensagens");
                    sendMessage.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            openSendMessage(usersFromAmbiente);
                        }
                    });

                    viewMessage.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showMessages(selectedUser);
                        }
                    });
                    popupMenu.add(sendMessage);
                    popupMenu.add(viewMessage);
                    popupMenu.show(usersFromSelectedAmbient,event.getX(),event.getY());

                }
            }
        });
    }


    private void openSendMessage(String[] _users){

    String reciever = (String) JOptionPane.showInputDialog(null,"Selecione o destinatario:","Enviar Mensagem",JOptionPane.QUESTION_MESSAGE,null, Arrays.stream(_users).filter(_user -> !_user.equals(usersFromSelectedAmbient.getSelectedValue())).toArray(),_users[0]);
    if(reciever != null){
            String messageContent = JOptionPane.showInputDialog(null,"Digite aqui a sua mensagem");
            if(messageContent != null){
                User userSender = spaceConfig.getUserByName(usersFromSelectedAmbient.getSelectedValue()).get();
                User userReciever = spaceConfig.getUserByName(reciever).get();
                sendMessage(messageContent,userReciever.userId,userSender.ambienteId,userSender.username);
            }
        }
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

    private void sendMessage(String content,Integer destiny,Integer ambiente,String senderName){
        Message message = new Message(destiny,ambiente,content,senderName);
        spaceConfig.senMessage(message);
     }
}