package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import config.SpaceConfig;
import tuplas.Espiao;
import tuplas.Message;

public class EspiaoChat extends JFrame {
    private SpaceConfig spaceConfig;
    private Espiao espiao = new Espiao();
    private JPanel panel = new JPanel();
    private JTextArea words;
    private List<String> palavrasSuspeitas =new ArrayList<String>();

    public EspiaoChat() {
        setTitle("Palavras Suspeitas");
        criarEspiao();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        words = new JTextArea();
        words.setEditable(false);
        panel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(words);
        panel.add(scrollPane,BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton suspeitas =new JButton("Nova Palavra");
        buttonPanel.add(suspeitas);

        suspeitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String newWord = JOptionPane.showInputDialog("Digite a nova palavra suspeita" );
                if (newWord != null && !newWord.isEmpty()) {
                    spaceConfig.addPalavraSuspeita(espiao, newWord);
                    words.append(newWord + "\n");
                }

            }
        });

        panel.add(buttonPanel,BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        add(panel);
        setVisible(true);
    }


    private void criarEspiao() {
        spaceConfig = SpaceConfig.getInstance();
        Espiao _espiao = (Espiao) spaceConfig.getEspiao(espiao);
        Thread thread = new Thread(listenMessages);
        thread.setDaemon(true);
        thread.start();
        if (_espiao == null) {
            spaceConfig.setSpie(espiao);
        }
    }

    private void handleSuspeciousword(Message message) {
        if (includesSupeciousWords(message.content)) {
            Espiao _espiao = spaceConfig.getEspiao(espiao);
            //TODO implementar metodo para enviar a mensagem via websocket

        }
    }

    private boolean includesSupeciousWords(String messageContent) {
        for (String word : espiao.palavrasSuspeitas) {
            if (messageContent.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    Runnable listenMessages = new Runnable() {
        @Override
        public void run() {
            while (true){
                Message messageToMonitore = new Message();
                messageToMonitore.monitored = false;

                try{
                    Message recevied = (Message) spaceConfig.space.take(messageToMonitore,null,500);
                    if(recevied != null){
                        handleSuspeciousword(recevied);
                        recevied.monitored = true;
                        spaceConfig.sendMessage(recevied);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
}
