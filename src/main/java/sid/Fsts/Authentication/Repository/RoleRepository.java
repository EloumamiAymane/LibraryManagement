package sid.Fsts.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sid.Fsts.Authentication.entities.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

//trouver le role a partir du rolename
        Role findByName(String rolename);
}
