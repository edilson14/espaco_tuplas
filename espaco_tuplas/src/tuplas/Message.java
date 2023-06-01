package tuplas;

import net.jini.core.entry.Entry;

public class Message implements Entry {
    public Integer destenyId;
    public  String content;
    public String sender;


    public Message(){}

    public Message(Integer desteni,String content,String sender){
        this.destenyId = desteni;
        this.content = content;
        this.sender = sender;
    }
}
