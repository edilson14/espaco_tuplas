package tuplas;
import net.jini.core.entry.Entry;

public class Dispositive implements Entry{
    public Integer ambienteid;
    public  Integer deviceId;
    public String name ="disp";


    public Dispositive(){
        
    }

    public Dispositive(Integer id){
        this.deviceId = id;
        this.name += id.toString();
    }


}
