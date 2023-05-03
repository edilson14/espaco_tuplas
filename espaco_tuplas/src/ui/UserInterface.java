package ui;

import config.SpaceConfig;
import tuplas.Ambiente;
import tuplas.Dispositive;
import tuplas.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInterface {
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> ambientesComboBox;
    private JComboBox<String> dispositivosComboBox;
    private JComboBox<String> usuariosComboBox;

    // Estruturas de dados para armazenar informações do sistema
    private ArrayList<Ambiente> ambientes;
    private ArrayList<Dispositive> dispositivos;
    private ArrayList<User> usuarios;
    private HashMap<String, Ambiente> ambientePorNome;
    private HashMap<String, Dispositive> dispositivoPorNome;
    private HashMap<String, User> usuarioPorNome;

    private SpaceConfig spaceConfig;



    public UserInterface(){
//         spaceConfig = SpaceConfig.getInstance();
        // Cria o JFrame
        frame = new JFrame("ESPAÇOS DE TUPLAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        panel = new JPanel(new GridLayout(5,2));




        // Cria os JComboBoxes
        ambientes = new ArrayList<Ambiente>();
        dispositivos = new ArrayList<Dispositive>();
        usuarios = new ArrayList<User>();
        ambientePorNome = new HashMap<>();
        dispositivoPorNome = new HashMap<>();
        usuarioPorNome = new HashMap<>();
        ambientesComboBox = new JComboBox<>();
        dispositivosComboBox = new JComboBox<>();
        usuariosComboBox = new JComboBox<>();



        // Adiciona os componentes ao JPanel
        panel.add(new JLabel("Ambientes:"));
        panel.add(ambientesComboBox);
        panel.add(createAmbienteButton());
        panel.add(deleteAmbienteButton());
        panel.add(new JLabel("Dispositivos:"));
        panel.add(dispositivosComboBox);
        panel.add(listDevicesButton());
        panel.add(moveDevicesButton());
        panel.add(new JLabel("Usuários:"));
        panel.add(usuariosComboBox);
        panel.add(createDevice());
        panel.add(createUserButton());
        panel.add(listUsersButton());
        panel.add(moveUserButton());


        frame.add(panel);

        frame.setVisible(true);
    }


    private void criarAmbiente()  {
        try{
        Ambiente ambiente = new Ambiente(1);
        spaceConfig.createAmbiente(ambiente);
        ambientes.add(ambiente);
//        ambientesComboBox.add();

        }catch (Exception e ){
            System.out.println("Algo deu errado na interface criando ambiente");
        }
    }
    private JButton createAmbienteButton(){
        JButton createambiente = new JButton("Criar Ambiente");
        createambiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    criarAmbiente();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return createambiente;
    }

    private JButton createDevice(){
        JButton button = new JButton("Criar Dispositivo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarDispositivo();
            }
        });
        return button;
    }

    private JButton createUserButton(){
        JButton button = new JButton("Criar Usuário");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarUsuario();
            }
        });

        return  button;
    }


    private JButton deleteAmbienteButton(){
        JButton button = new JButton("Distruir Ambiente");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destruirAmbiente();
            }
        });

        return button;
    }

    private JButton listDevicesButton(){
        JButton button = new JButton("Listar Dispositivos");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarDispositivos();
            }
        });

        return button;
    }

    private JButton listUsersButton(){
        JButton button = new JButton("Listar Usuários");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });

        return button;
    }

    private JButton moveDevicesButton(){
        JButton button = new JButton("Mover Dispositivos");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverDispositivo();
            }
        });

        return button;
    }
    private JButton moveUserButton(){
        JButton button = new JButton("Mover Usuário");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverUsuario();
            }
        });

        return button;
    }



    private void criarDispositivo() {
        try{
            Dispositive dispositive = new Dispositive(1);
            spaceConfig.creatDispoitive(dispositive);
            dispositivos.add(dispositive);

        }catch (Exception e ){
            System.out.println("Algo deu errado na interface criando dispositivo");
        }
    }

    private void criarUsuario() {
        try{
            User user = new User(1);
            spaceConfig.createUser(user);
            usuarios.add(user);

        }catch (Exception e ){
            System.out.println("Algo deu errado na interface criando user");
        }
    }

    private void destruirAmbiente() {
        // Implementação da destruição de ambiente
    }

    private void listarDispositivos() {
        // Implementação da listagem de dispositivos
    }

    private void listarUsuarios() {
        // Implementação da listagem de usuários
    }

    private void moverDispositivo() {
        // Implementação da movimentação de dispositivo
    }

    private void moverUsuario() {
        // Implementação da movimentação de usuário
    }
}
