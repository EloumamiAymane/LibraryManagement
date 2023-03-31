package sid.Fsts.Authentication.Controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sid.Fsts.Authentication.Repository.UserRepository;
import sid.Fsts.Authentication.Service.AccountService;
import sid.Fsts.Authentication.entities.Role;
import sid.Fsts.Authentication.entities.User;

import java.util.List;

@RestController

public class Home {
@Autowired
    private AccountService accountService;


@Autowired
private UserRepository userRepository;
    @GetMapping("/user")

    public List<User> index(){
        return accountService.ListUsers();
    }
    @PostMapping(path="/addusers")

    public User saveUser(@RequestBody User user){
        return  accountService.addNewUser(user);
    }
    @PostMapping(path="/roles")
    public Role saveRole(@RequestBody Role role){
        return  accountService.addNewRole(role);
    }

    @PostMapping(path="/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleForm roleForm){
       accountService.addRoleToUser(roleForm.getUsername(),roleForm.getRoleName());
    }



    @GetMapping("getUser/{username}")
    public User getUser(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }


}
@Data
class RoleForm{
    private String username;
    private String roleName;
}
