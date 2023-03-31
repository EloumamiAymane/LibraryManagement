package sid.Fsts.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sid.Fsts.Authentication.entities.Avis;
import sid.Fsts.Authentication.entities.Emprunt;
@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt,Long> {
}
