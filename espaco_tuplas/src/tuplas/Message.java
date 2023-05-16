package tuplas;

import net.jini.core.entry.Entry;

public class Message implements Entry {
    public Integer destenyId;
    public  String content;
    public Integer ambienteId;

    public String sender;

    public Message(){}

    public Message(Integer desteni,Integer ambienteId,String content,String sender){
        this.destenyId = desteni;
        this.ambienteId = ambienteId;
        this.content = content;
        this.sender = sender;
    }
}
