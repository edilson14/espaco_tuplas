package tuplas;

import net.jini.core.entry.Entry;

public class Mensagem implements Entry {
    public String content;
    public User sender;
    public User reciever;
    public Boolean monitored = false;


    public Mensagem() {
    }

    public Mensagem(String content, User sender, User reciever) {
        this.content = content;
        this.sender = sender;
        this.reciever = reciever;
    }
}
