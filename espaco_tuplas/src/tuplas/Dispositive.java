package tuplas;
import net.jini.core.entry.*;

public class Dispositive implements Entry{
    public Integer ambienteid;
    public  Integer deviceId;
    public String name ="disp";


    Dispositive(){
        
    }

    Dispositive(Integer id){
        this.deviceId = id;
        this.name += id.toString();
    }

    public void addDeviceToAmbiente(Integer ambienteid) throws Exception {
        if(this.ambienteid == null){
            this.ambienteid = ambienteid;
        } else{
            throw new Exception("O dispositivo já se encontra em um ambiente");
        }
    }

    public void removerDispositivoDoAmbiente(){
        this.ambienteid = null;
    }
}
