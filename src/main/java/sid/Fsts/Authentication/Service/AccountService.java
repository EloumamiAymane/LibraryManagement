package sid.Fsts.Authentication.Service;

import org.springframework.stereotype.Service;
import sid.Fsts.Authentication.entities.Role;
import sid.Fsts.Authentication.entities.User;

import java.util.List;
@Service
public interface AccountService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    void addRoleToUser(String Username,String roleName);
    User loadUserByUsername(String Username);
    List<User> ListUsers();

}
