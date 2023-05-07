package config;

import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.*;
import tuplas.Ambiente;
import tuplas.Dispositive;
import tuplas.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class SpaceConfig {

    private JavaSpace space;
    private static SpaceConfig instance;
    private List<Ambiente> ambienteList = new ArrayList<Ambiente>();
    private List<User> userList = new ArrayList<User>();
    private List<Dispositive> dispositiveList = new ArrayList<Dispositive>();

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



    public void createAmbiente(Ambiente ambiente) throws TransactionException, RemoteException {

        try{
            space.write(ambiente,null, Lease.FOREVER);
            ambienteList.add(ambiente);
            System.out.println("ambiente criado");
        } catch (Exception e){
            System.out.println("Algo deu errado criando ambiente");
            e.printStackTrace();
            throw e;
        }

    }

    public void creatDispoitive(Dispositive dispositive) throws TransactionException,RemoteException{
        try{
            space.write(dispositive,null,Lease.FOREVER);
            dispositiveList.add(dispositive);
            System.out.println("Dispositivo criado");
        }
        catch (Exception e){
            System.out.println("Algo deu errado criando dispositivo");
            e.printStackTrace();
            throw  e;
        }
    }

    public void apagarDispositivo(String name){
        try{
            Dispositive dispositive = new Dispositive();
            dispositive.name = name;
            space.take(dispositive,null,Lease.FOREVER);
            dispositiveList.removeIf(dispositive1 -> dispositive1.name.equals(name) && dispositive1.ambienteid == null);
            System.out.println("Dispositivo apagado");
        }
        catch (Exception e){
            System.out.println("Algo deu Errado");
            e.printStackTrace();
        }
    }

    //
    public void createUser(User user) throws TransactionException,RemoteException {
        try{
            space.write(user,null,Lease.FOREVER);
            System.out.println("Usuario adicionado com sucesso");
        }catch (Exception e){
            System.out.println("Algo deu errado na criação do usuário");
            throw e;
        }
    }

    public void apagarUsuario(String name){
        try{
            User user = new User();
            user.username = name;
            space.take(user,null,Lease.FOREVER);
            userList.removeIf(_user -> _user.username.equals(name) && _user.ambienteId == null);
            System.out.println("Usuario apagado");
        }
        catch (Exception e){
            System.out.println("Algo deu Errado");
            e.printStackTrace();
        }
    }


}
