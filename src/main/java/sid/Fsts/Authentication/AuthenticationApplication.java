package sid.Fsts.Authentication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sid.Fsts.Authentication.Service.AccountService;
import sid.Fsts.Authentication.entities.Role;
import sid.Fsts.Authentication.entities.User;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@ComponentScan({"Service","Repository","sid.Fsts.Authentication"})

public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

@Bean
	CommandLineRunner start(AccountService AccountService){
		return args -> {
			AccountService.addNewRole(new Role(null,"ADMIN"));
			AccountService.addNewRole(new Role(null,"USER"));
			AccountService.addNewRole(new Role(null,"CUSTUMOR_MANAGER"));
			AccountService.addNewRole(new Role(null,"PRODUCTOR_MANAGER"));



			AccountService.addNewUser(new User("user1",false,"1234"));

			AccountService.addNewUser(new User("admin",true,"1234"));

			AccountService.addNewUser(new User("user2",true,"1234"));

			AccountService.addNewUser(new User("user3",false,"1234"));
			AccountService.addNewUser(new User("Aymane",false,"0000"));

			AccountService.addRoleToUser("user1","USER");
			AccountService.addRoleToUser("admin","ADMIN");
			//AccountService.addRoleToUser("admin","USER");
			AccountService.addRoleToUser("user2","USER");
			AccountService.addRoleToUser("user2","CUSTUMOR_MANAGER");
			AccountService.addRoleToUser("user3","USER");
			AccountService.addRoleToUser("user3","PRODUCTOR_MANAGER");


		};
}


}

