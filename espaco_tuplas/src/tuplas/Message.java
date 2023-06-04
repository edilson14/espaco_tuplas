package tuplas;

import net.jini.core.entry.Entry;

public class Message implements Entry {
    public  String content;
    public User sender;
    public User reciever;


    public Message(){}

    public Message(String content,User sender,User reciever){
        this.content = content;
        this.sender = sender;
        this.reciever = reciever;
    }
}
