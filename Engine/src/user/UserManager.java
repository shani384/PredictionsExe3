package user;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String,Object> usersData;
    private Boolean isAdminLoggedIn = false;
    public UserManager() {
        this.usersData = new HashMap<>();
    }
    public boolean isUserExists(String username) {
        return this.usersData.containsKey(username);
    }
    public synchronized void addUser(String username) {
        usersData.put(username,null);
    }
    public synchronized void remove(String username){
        usersData.remove(username);
    }

    public boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }
    public synchronized void adminLogin(){
        this.isAdminLoggedIn = true;
    }
    public synchronized void adminLogout(){
        this.isAdminLoggedIn = false;
    }
}
