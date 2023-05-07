package ui;

import tuplas.Ambiente;
import config.SpaceConfig;
import tuplas.Dispositive;
import tuplas.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInterface extends JFrame {

    private JButton createEnvButton, createUserButton, createDeviceButton;
    private SpaceConfig spaceConfig;
    private JButton removeUserButton, removeDeviceButton;
    private JList<String> envList, userList, deviceList;

    private DefaultListModel<String> envNames = new DefaultListModel<String>();
    private DefaultListModel<String> devicesNames = new DefaultListModel<String>();
    private DefaultListModel<String> usersNames = new DefaultListModel<String>();
    private Integer lastEnvNumber =1;
    private Integer lastDeviceNumber = 1;
    private Integer lastUserNumber = 1;

//    {
//        envNames = new String[]
//    }

    public UserInterface() {
        super("Espaço de Tuplas");
        spaceConfig = SpaceConfig.getInstance();

        // cria os botões
        createEnvButton = new JButton("Criar Ambiente");
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



        removeUserButton = new JButton("Remover Usuário");
        removeUserButton.setEnabled(false);

        removeDeviceButton = new JButton("Remover Dispositivo");
        removeDeviceButton.setEnabled(false);

        // cria as listas
        envList = new JList<String>(envNames);
        deviceList = new JList<String>(devicesNames);
        userList = new JList<String>(usersNames);

        // cria um painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(createEnvButton);
        buttonPanel.add(createUserButton);
        buttonPanel.add(createDeviceButton);

        // cria um painel para as listas
        JPanel listPanel = new JPanel(new GridLayout(1, 3));
        listPanel.add(createListPanel(envList, "Ambientes", null));
        listPanel.add(createListPanel(userList, "Usuários", removeUserButton));
        listPanel.add(createListPanel(deviceList, "Dispositivos", removeDeviceButton));

        // adiciona um ouvinte para o clique em um item da lista de ambientes
        envList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // obtém o ambiente selecionado e mostra uma janela com seus usuários e dispositivos
                    String selectedEnv = envList.getSelectedValue();
                    String[] users = {"Usuário A", "Usuário B", "Usuário C"};
                    String[] devices = {"Dispositivo X", "Dispositivo Y", "Dispositivo Z"};
                    showEnvironmentDetails(selectedEnv, users, devices);
                }
            }
        });

        // adiciona um ouvinte para o clique no botão Remover Usuário
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUser = userList.getSelectedValue();
                String selectedEnv = envList.getSelectedValue();
                if (selectedUser != null && selectedEnv != null) {
                    // remove o usuário do ambiente e atualiza a lista
                    System.out.println("Removendo usuário " + selectedUser + " do ambiente " + selectedEnv);
                    DefaultListModel<String> model = (DefaultListModel<String>) userList.getModel();
                    model.removeElement(selectedUser);
                    removeUserButton.setEnabled(false);
                }
            }
        });
        // adiciona um ouvinte para o clique no botão Remover Dispositivo
        removeDeviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDevice = deviceList.getSelectedValue();
                String selectedEnv = envList.getSelectedValue();
                if (selectedDevice != null && selectedEnv != null) {
                    // remove o dispositivo do ambiente e atualiza a lista
                    System.out.println("Removendo dispositivo " + selectedDevice + " do ambiente " + selectedEnv);
                    DefaultListModel<String> model = (DefaultListModel<String>) deviceList.getModel();
                    model.removeElement(selectedDevice);
                    removeDeviceButton.setEnabled(false);
                }
            }
        });

        // adiciona um ouvinte para o clique na lista de usuários
        userList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // habilita o botão Remover Usuário se um usuário estiver selecionado
                if (userList.getSelectedValue() != null) {
                    removeUserButton.setEnabled(true);
                } else {
                    removeUserButton.setEnabled(false);
                }
            }
        });

        // adiciona um ouvinte para o clique na lista de dispositivos
        deviceList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (deviceList.getSelectedValue() != null) {
                    removeDeviceButton.setEnabled(true);
                } else {
                    removeDeviceButton.setEnabled(false);
                }

            }

        });

        // adiciona os componentes à janela
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(listPanel, BorderLayout.CENTER);

        // configura o tamanho e visibilidade da janela
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
        JDialog dialog = new JDialog(this, "Detalhes do Ambiente " + env, true);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JList<String> userList = new JList<String>(users);
        JList<String> deviceList = new JList<String>(devices);
        panel.add(createListPanel(userList, "Usuários", null));
        panel.add(createListPanel(deviceList, "Dispositivos", null));
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

        private void criarAmbiente()  {
         try{
            Ambiente ambiente = new Ambiente(lastEnvNumber);
            spaceConfig.createAmbiente(ambiente);
            envNames.add(envNames.size() ,ambiente.ambienteName);
            lastEnvNumber +=1;

        }catch (Exception e ){
            System.out.println("Algo deu errado na interface criando ambiente");
            e.printStackTrace();
         }
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

    private void createUser(){
try{
    User user = new User(lastUserNumber);
    spaceConfig.createUser(user);
    usersNames.add(usersNames.size(),user.username);
    lastUserNumber+=1;
    }
catch (Exception e){

    System.out.println("Algo deu errado criando usuario");
    e.printStackTrace();
}
}
}