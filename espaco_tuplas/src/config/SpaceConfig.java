package config;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import tuplas.Message;
import tuplas.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SpaceConfig {

    private JavaSpace space;
    private static SpaceConfig instance;

    public List<User> userList = new ArrayList<User>();


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




    //
    public void createUser(User user) throws TransactionException,RemoteException {
        try{
            space.write(user,null,Lease.FOREVER);
            userList.add(userList.size(),user);
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

    public void removeUserfromAmbiente(User user){
        try {
            User _user =(User) space.take(user,null,Lease.FOREVER);
            int index = userList.indexOf(user);
            if(_user != null){
                _user.ambienteId = null;
                space.write(_user,null,Lease.FOREVER);
                userList.set(index,_user);
                System.out.println("User Removido do Ambiente");
            }
        }
        catch (Exception e){
            System.out.println("Algo deu errado");
            e.printStackTrace();
        }
    }

    public Optional<User> getUserByName(String name){
        Optional<User> user = userList.stream().filter(_user -> _user.username.equals(name)).findFirst();
        return user;
    }



    public void senMessage(Message message){
        try {
            space.write(message,null,Lease.FOREVER);
            System.out.println("Mensagem enviada");
        } catch (TransactionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Message> listenMessage(User user){
        boolean hasMessage = true;
        List<Message> messageList = new ArrayList<Message>();
        Message message = new Message();
            message.destenyId = user.userId;
            try {
                while (hasMessage){
                    Message _message=(Message) space.take(message,null,500);
                    if(_message != null){
                        messageList.add(_message);
                        System.out.println(_message);
                    } else{
                        hasMessage = false;
                    }
                }

            } catch (UnusableEntryException e) {
                throw new RuntimeException(e);
            } catch (TransactionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        return messageList;
    }


}
