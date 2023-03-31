package sid.Fsts.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sid.Fsts.Authentication.entities.Avis;
import sid.Fsts.Authentication.entities.Categorie;
@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
}
