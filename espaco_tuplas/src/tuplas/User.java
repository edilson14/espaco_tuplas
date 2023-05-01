package tuplas;

import net.jini.core.entry.Entry;

public class User implements Entry {
    public Integer userId;
    public Integer ambienteId;
    public String username = "user";

    User(){

    }

    User(Integer id){
       this.username += id.toString();
    }
}
