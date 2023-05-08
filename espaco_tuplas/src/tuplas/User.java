package tuplas;

import net.jini.core.entry.Entry;

public class User implements Entry {
    public Integer userId;
    public Integer ambienteId;
    public String username = "user";

    public User(){

    }

    public User(Integer id){
        this.userId = id;
        this.username += id.toString();

    }
}
