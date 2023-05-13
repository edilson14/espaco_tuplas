package tuplas;

import net.jini.core.entry.Entry;

import java.util.ArrayList;
import java.util.List;

public class User implements Entry {
    public Integer userId;
    public Integer ambienteId;
    public String username = "user";
    List<Message> recievideMessages = new ArrayList<Message>();

    public User(){

    }

    public User(Integer id){
        this.userId = id;
        this.username += id.toString();

    }
}
