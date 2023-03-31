package sid.Fsts.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sid.Fsts.Authentication.entities.Avis;
@Repository
public interface AvisRepository  extends JpaRepository<Avis,Long> {
}
