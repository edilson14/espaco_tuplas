package config;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.*;
import tuplas.Ambiente;
import tuplas.Dispositive;
import tuplas.Message;
import tuplas.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public boolean isAmbienteEmpty(Integer ambientId){
        List<User> userList1 = getUsersByAmbienteId(ambientId);
        List<Dispositive> dispositives = getDevicesByAmbienteId(ambientId);
        return  userList1.isEmpty() && dispositives.isEmpty();
    }

    public void deleteAmbiente(String ambienteName){
        try {
            Ambiente ambiente = new Ambiente();
            ambiente.ambienteName = ambienteName;
            space.take(ambiente,null,Lease.FOREVER);
            ambienteList.removeIf(_ambiente -> _ambiente.ambienteName.equals(ambienteName));
            System.out.println("Ambiente apagado");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Algo deu errado!");
        }
    }


    public Optional<Ambiente> getAmbienteByName(String ambienteName){
        Optional<Ambiente> ambiente = ambienteList.stream().filter(_ambiente -> _ambiente.ambienteName.equals(ambienteName)).findFirst();
        return ambiente;
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

    public void addDeviceToAmbiente(Dispositive device,Ambiente ambiente) throws UnusableEntryException, TransactionException, RemoteException, InterruptedException {
        try{
           int deviceIndex = dispositiveList.indexOf(device);

            Dispositive deviceToAdd = (Dispositive) space.take(device,null,Lease.FOREVER);

           deviceToAdd.deviceId = device.deviceId;
           deviceToAdd.ambienteid = ambiente.ambienteId;
           space.write(deviceToAdd,null,Lease.FOREVER);
            dispositiveList.set(deviceIndex,deviceToAdd);

        }
        catch (Exception e){
            System.out.println("Algo deu errado");
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Dispositive> getDispositiveByName(String name){
        Optional<Dispositive> dispositive = dispositiveList.stream().filter(_dispositive -> _dispositive.name.equals(name)).findFirst();
        return dispositive;
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

    public void removeDevicefromAmbiente(Dispositive dispositive){
        try {
            Dispositive _dispositive =(Dispositive) space.take(dispositive,null,Lease.FOREVER);
            if(dispositive != null){
                _dispositive.ambienteid = null;
                space.write(_dispositive,null,Lease.FOREVER);
                System.out.println("Dispositivo Removido do Ambiente");
            }
        }
        catch (Exception e){
            System.out.println("Algo deu errado");
            e.printStackTrace();
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
            if(_user != null){
                _user.ambienteId = null;
                space.write(_user,null,Lease.FOREVER);
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

    public void addUserAmbiente(User user,Ambiente ambiente) throws UnusableEntryException, TransactionException, RemoteException, InterruptedException {
        try{
            int userIndex = userList.indexOf(user);
            User userToAdd = (User) space.take(user,null,Lease.FOREVER);
            userToAdd.userId = user.userId;
            userToAdd.ambienteId = ambiente.ambienteId;
            space.write(userToAdd,null,Lease.FOREVER);
            userList.set(userIndex,userToAdd);
        }
        catch (Exception e){
            System.out.println("Algo deu errado");
            e.printStackTrace();
            throw e;
        }
    }
    public List<User> getUsersByAmbienteId(Integer ambienteId){
        List<User> allById = new ArrayList();
        allById = userList.stream().filter(_user -> _user.ambienteId != null && _user.ambienteId.equals(ambienteId)).collect(Collectors.toList());

        return allById;
    }

    public List<Dispositive> getDevicesByAmbienteId(Integer ambienteId){
        List<Dispositive> allById = new ArrayList();
       allById = dispositiveList.stream().filter(_dispositive -> _dispositive.ambienteid != null && _dispositive.ambienteid.equals(ambienteId)).collect(Collectors.toList());

        return allById;
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
            message.ambienteId = user.ambienteId;
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
