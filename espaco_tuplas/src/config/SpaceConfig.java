package config;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.*;
import tuplas.Ambiente;

import java.rmi.RemoteException;


public class SpaceConfig {

    private JavaSpace space;
    private static SpaceConfig instance;
    private Integer lastAmbienteId = 1;


    private  SpaceConfig(){
        this.findSpace();
    }

    public static SpaceConfig getInstance(){
        if(instance == null){
            instance = new SpaceConfig();
        }
        return instance;
    }


    private void findSpace(){
        try{
            System.out.println("Procurando espaço!");

            Lookup finder = new Lookup(JavaSpace.class);
            space = (JavaSpace) finder.getService();
            if(space == null){
                System.out.println("Serviço não encontrado!");
                System.exit(-1);
            }
            System.out.println("Serviço encontrado");
        }catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Algo deu errado!");
        }
    }



    private void createAmbiente(Ambiente ambiente) throws TransactionException, RemoteException {

        try{
            space.write(ambiente,null, Lease.FOREVER);
        } catch (Exception e){
            System.out.println("Algo deu errado criando ambiente");
            e.printStackTrace();
            throw e;
        }

    }

    public Ambiente getAmbientes() throws UnusableEntryException, TransactionException, RemoteException, InterruptedException {
        final int oneMinute = 60*1000;
        Ambiente ambiente = new Ambiente(lastAmbienteId);

        space.read(ambiente,null,oneMinute);
    }

}
