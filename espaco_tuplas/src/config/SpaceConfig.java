package config;

import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

import java.util.ArrayList;
import java.util.List;

import tuplas.Espiao;
import tuplas.Message;
import tuplas.User;


public class SpaceConfig {

    public JavaSpace space;
    private static SpaceConfig instance;

    public List<User> userList = new ArrayList<User>();


    private SpaceConfig() {
        this.findSpace();
    }

    public static SpaceConfig getInstance() {
        if (instance == null) {
            instance = new SpaceConfig();
        }
        return instance;
    }


    private void findSpace() {
        try {
            System.out.println("Procurando espaço!");

            Lookup finder = new Lookup(JavaSpace.class);
            space = (JavaSpace) finder.getService();
            if (space == null) {
                System.out.println("Serviço não encontrado!");
                System.exit(-1);
            }
            System.out.println("Serviço encontrado");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Algo deu errado!");
        }
    }

    public void setSpie(Espiao espiao) {
        try {
            space.write(espiao, null, Lease.FOREVER);
            System.out.println("Espião adicionado a rede");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado adicionando espiao");
        }
    }

    public void addPalavraSuspeita(Espiao espiao, String palavraSuspeita) {
        try {
            Espiao espiao1 = (Espiao) space.take(espiao, null, 500);
            if (espiao1 != null) {
                espiao1.palavrasSuspeitas.add(palavraSuspeita);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado registrando palavra");
        }
    }


    public User findOtherUser(User currentUser) {
        User otherUser = null;
        Integer otherUserId = currentUser.userId == 1 ? 2 : 1;
        try {
            while (true) {
                User newUser = (User) space.read(new User(otherUserId), null, 5000);
                if (newUser != null) {
                    otherUser = newUser;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return otherUser;
    }

    public User createUser() {
        Integer lastUserId = 1;
        try {
            User user = new User(lastUserId);
            User lastUser = (User) space.read(user, null, 5000);
            if (lastUser != null) {
                lastUserId = lastUser.userId + 1;
            }
            User newUser = new User(lastUserId);
            space.write(newUser, null, Lease.FOREVER);
            System.out.println("Úsuario criado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado!");
        }
        return new User(lastUserId);
    }

    public void sendMessage(Message message) {
        try {
            space.write(message, null, Lease.FOREVER);
            System.out.println("Mensagem enviado!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado enviando mensagem");
        }
    }


    public Espiao getEspiao(Espiao espiao) {
        try {
            Espiao newEspiao = (Espiao) space.take(espiao, null, 500);
            if (newEspiao != null) {

                return newEspiao;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo deu errado pegando espiao");
        }
        return null;
    }

}
