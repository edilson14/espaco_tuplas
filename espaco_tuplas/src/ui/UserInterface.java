package ui;
//
//import config.SpaceConfig;
//import tuplas.Ambiente;
//import tuplas.Dispositive;
//import tuplas.User;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class UserInterface {
//    private JFrame frame;
//    private JPanel panel;
//    private JComboBox<String> ambientesComboBox;
//    private JComboBox<String> dispositivosComboBox;
//    private JComboBox<String> usuariosComboBox;
//
//    // Estruturas de dados para armazenar informações do sistema
//    private ArrayList<Ambiente> ambientes;
//    private ArrayList<Dispositive> dispositivos;
//    private ArrayList<User> usuarios;
//    private HashMap<String, Ambiente> ambientePorNome;
//    private HashMap<String, Dispositive> dispositivoPorNome;
//    private HashMap<String, User> usuarioPorNome;
//
//    private SpaceConfig spaceConfig;
//
//
//
//    public UserInterface(){
////         spaceConfig = SpaceConfig.getInstance();
//        // Cria o JFrame
//        frame = new JFrame("ESPAÇOS DE TUPLAS");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//
//        panel = new JPanel(new GridLayout(5,2));
//
//
//
//
//        // Cria os JComboBoxes
//        ambientes = new ArrayList<Ambiente>();
//        dispositivos = new ArrayList<Dispositive>();
//        usuarios = new ArrayList<User>();
//        ambientePorNome = new HashMap<>();
//        dispositivoPorNome = new HashMap<>();
//        usuarioPorNome = new HashMap<>();
//        ambientesComboBox = new JComboBox<>();
//        dispositivosComboBox = new JComboBox<>();
//        usuariosComboBox = new JComboBox<>();
//
//
//
//        // Adiciona os componentes ao JPanel
//        panel.add(new JLabel("Ambientes:"));
//        panel.add(ambientesComboBox);
//        panel.add(createAmbienteButton());
//        panel.add(deleteAmbienteButton());
//        panel.add(new JLabel("Dispositivos:"));
//        panel.add(dispositivosComboBox);
//        panel.add(listDevicesButton());
//        panel.add(moveDevicesButton());
//        panel.add(new JLabel("Usuários:"));
//        panel.add(usuariosComboBox);
//        panel.add(createDevice());
//        panel.add(createUserButton());
//        panel.add(listUsersButton());
//        panel.add(moveUserButton());
//
//
//        frame.add(panel);
//
//        frame.setVisible(true);
//    }
//
//
//    private void criarAmbiente()  {
//        try{
//        Ambiente ambiente = new Ambiente(1);
//        spaceConfig.createAmbiente(ambiente);
//        ambientes.add(ambiente);
////        ambientesComboBox.add();
//
//        }catch (Exception e ){
//            System.out.println("Algo deu errado na interface criando ambiente");
//        }
//    }
//    private JButton createAmbienteButton(){
//        JButton createambiente = new JButton("Criar Ambiente");
//        createambiente.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    criarAmbiente();
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//        return createambiente;
//    }
//
//    private JButton createDevice(){
//        JButton button = new JButton("Criar Dispositivo");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                criarDispositivo();
//            }
//        });
//        return button;
//    }
//
//    private JButton createUserButton(){
//        JButton button = new JButton("Criar Usuário");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                criarUsuario();
//            }
//        });
//
//        return  button;
//    }
//
//
//    private JButton deleteAmbienteButton(){
//        JButton button = new JButton("Distruir Ambiente");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                destruirAmbiente();
//            }
//        });
//
//        return button;
//    }
//
//    private JButton listDevicesButton(){
//        JButton button = new JButton("Listar Dispositivos");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                listarDispositivos();
//            }
//        });
//
//        return button;
//    }
//
//    private JButton listUsersButton(){
//        JButton button = new JButton("Listar Usuários");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                listarUsuarios();
//            }
//        });
//
//        return button;
//    }
//
//    private JButton moveDevicesButton(){
//        JButton button = new JButton("Mover Dispositivos");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                moverDispositivo();
//            }
//        });
//
//        return button;
//    }
//    private JButton moveUserButton(){
//        JButton button = new JButton("Mover Usuário");
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                moverUsuario();
//            }
//        });
//
//        return button;
//    }
//
//
//
//    private void criarDispositivo() {
//        try{
//            Dispositive dispositive = new Dispositive(1);
//            spaceConfig.creatDispoitive(dispositive);
//            dispositivos.add(dispositive);
//
//        }catch (Exception e ){
//            System.out.println("Algo deu errado na interface criando dispositivo");
//        }
//    }
//
//    private void criarUsuario() {
//        try{
//            User user = new User(1);
//            spaceConfig.createUser(user);
//            usuarios.add(user);
//
//        }catch (Exception e ){
//            System.out.println("Algo deu errado na interface criando user");
//        }
//    }
//
//    private void destruirAmbiente() {
//        // Implementação da destruição de ambiente
//    }
//
//    private void listarDispositivos() {
//        // Implementação da listagem de dispositivos
//    }
//
//    private void listarUsuarios() {
//        // Implementação da listagem de usuários
//    }
//
//    private void moverDispositivo() {
//        // Implementação da movimentação de dispositivo
//    }
//
//    private void moverUsuario() {
//        // Implementação da movimentação de usuário
//    }
//}
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame {

    private JButton createEnvButton, createUserButton, createDeviceButton;
    private JButton removeUserButton, removeDeviceButton;
    private JList<String> envList, userList, deviceList;

    public UserInterface() {
        super("Minha Aplicação");

        // cria os botões
        createEnvButton = new JButton("Criar Ambiente");
        createUserButton = new JButton("Criar Usuário");
        createDeviceButton = new JButton("Criar Dispositivo");

        removeUserButton = new JButton("Remover Usuário");
        removeUserButton.setEnabled(false);

        removeDeviceButton = new JButton("Remover Dispositivo");
        removeDeviceButton.setEnabled(false);

        // cria as listas
        envList = new JList<String>(new String[]{"Ambiente 1", "Ambiente 2", "Ambiente 3"});
        userList = new JList<String>(new String[]{"Usuário 1", "Usuário 2", "Usuário 3"});
        deviceList = new JList<String>(new String[]{"Dispositivo 1", "Dispositivo 2", "Dispositivo 3"});

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
}