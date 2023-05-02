package ui;

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
    private JButton criarAmbienteBtn;
    private JButton criarDispositivoBtn;
    private JButton criarUsuarioBtn;
    private JButton destruirAmbienteBtn;
    private JButton listarDispositivosBtn;
    private JButton listarUsuariosBtn;
    private JButton moverDispositivoBtn;
    private JButton moverUsuarioBtn;
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

    public UserInterface(){
        // Cria o JFrame
        frame = new JFrame("ESPAÇOS DE TUPLAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        panel = new JPanel(new GridLayout(5,2));

        // Cria os botões
        criarAmbienteBtn = new JButton("Criar Ambiente");
        criarDispositivoBtn = new JButton("Criar Dispositivo");
        criarUsuarioBtn = new JButton("Criar Usuário");
        destruirAmbienteBtn = new JButton("Destruir Ambiente");
        listarDispositivosBtn = new JButton("Listar Dispositivos");
        listarUsuariosBtn = new JButton("Listar Usuários");
        moverDispositivoBtn = new JButton("Mover Dispositivo");
        moverUsuarioBtn = new JButton("Mover Usuário");



        // Adiciona os eventos de clique aos botões
        criarAmbienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarAmbiente();
            }
        });
        criarDispositivoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarDispositivo();
            }
        });
        criarUsuarioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarUsuario();
            }
        });
        destruirAmbienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destruirAmbiente();
            }
        });
        listarDispositivosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarDispositivos();
            }
        });
        listarUsuariosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarUsuarios();
            }
        });
        moverDispositivoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverDispositivo();
            }
        });
        moverUsuarioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverUsuario();
            }
        });


        // Cria os JComboBoxes
        ambientes = new ArrayList<>();
        dispositivos = new ArrayList<>();
        usuarios = new ArrayList<>();
        ambientePorNome = new HashMap<>();
        dispositivoPorNome = new HashMap<>();
        usuarioPorNome = new HashMap<>();
        ambientesComboBox = new JComboBox<>();
        dispositivosComboBox = new JComboBox<>();
        usuariosComboBox = new JComboBox<>();



        // Adiciona os componentes ao JPanel
        panel.add(new JLabel("Ambientes:"));
        panel.add(ambientesComboBox);
        panel.add(criarAmbienteBtn);
        panel.add(destruirAmbienteBtn);
        panel.add(new JLabel("Dispositivos:"));
        panel.add(dispositivosComboBox);
        panel.add(listarDispositivosBtn);
        panel.add(moverDispositivoBtn);
        panel.add(new JLabel("Usuários:"));
        panel.add(usuariosComboBox);
        panel.add(criarDispositivoBtn);
        panel.add(criarUsuarioBtn);
        panel.add(listarUsuariosBtn);
        panel.add(moverUsuarioBtn);


        frame.add(panel);

        frame.setVisible(true);
    }


    private void criarAmbiente() {
        // Implementação da criação de ambiente
    }

    private void criarDispositivo() {
        // Implementação da criação de dispositivo
    }

    private void criarUsuario() {
        // Implementação da criação de usuário
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
