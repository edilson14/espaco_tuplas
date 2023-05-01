package tuplas;
import java.util.ArrayList;
import java.util.List;

import net.jini.core.entry.Entry;


public class Ambiente  implements Entry {
    public Integer ambienteId;
    public List<Dispositive> dispositives;
    public List<User> users;

    public String ambienteName = "amb";


    Ambiente(){}

    Ambiente(Integer id){
        this.ambienteId = id;
        this.ambienteName += id.toString();
        this.dispositives = new ArrayList<Dispositive>();
        this.users = new ArrayList<User>();
    }
}
