package config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.json.simple.JsonObject;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import tuplas.Mensagem;



public class SocketServer {
    ConnectionFactory connFactory = new ActiveMQConnectionFactory();
    Connection conn;
    Session sess;
    Destination dest;

    public SocketServer() {
        conect();
    }


    private void conect() {
        try {
            conn = connFactory.createConnection();
            sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            dest = sess.createTopic("mensagens-suspeitas");
            System.out.println("Conectado com sucesso");
        } catch (Exception e) {
            System.out.println("Algo deu errado conectando");
        }
    }


    public void senMessage(Mensagem message) {
        try {

            MessageProducer prod = sess.createProducer(dest);
            JsonObject messageToSend = new JsonObject();
            messageToSend.put("sender",message.sender.username);
            messageToSend.put("message",message.content);
            messageToSend.put("reciever",message.reciever.username);
            Message msg = sess.createTextMessage(messageToSend.toString());
            prod.send(msg);
            System.out.println("mensagem suspeita enviada para o topico");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado enviando mensagem");
        }
    }

}
