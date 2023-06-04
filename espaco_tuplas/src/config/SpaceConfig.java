package config;

import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;
import tuplas.Message;
import tuplas.User;

import java.util.ArrayList;
import java.util.List;


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


}
