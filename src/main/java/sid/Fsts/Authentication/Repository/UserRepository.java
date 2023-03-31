package sid.Fsts.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sid.Fsts.Authentication.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//trouver l'user authentifie a partir du username
    User findByUsername(String username);


}
