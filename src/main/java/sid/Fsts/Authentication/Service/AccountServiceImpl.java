package sid.Fsts.Authentication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sid.Fsts.Authentication.Repository.RoleRepository;
import sid.Fsts.Authentication.Repository.UserRepository;
import sid.Fsts.Authentication.entities.Role;
import sid.Fsts.Authentication.entities.User;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
@Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User addNewUser(User user) {
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String Username, String roleName) {
User user=userRepository.findByUsername(Username);
Role role=roleRepository.findByName(roleName);
user.getRoles().add(role);
    }

    @Override
    public User loadUserByUsername(String Username) {
        return userRepository.findByUsername(Username);
    }

    @Override
    public List<User> ListUsers() {
        return userRepository.findAll();
    }
}
