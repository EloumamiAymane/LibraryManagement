package sid.Fsts.Authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String auteur;
    private String photo;
    private int quantite;
    private boolean disponibilite;
    private boolean nouveau ;
    @ManyToOne
    private Bibliotheque biblio;
    @ManyToOne
    private Categorie categorie;
    @OneToMany(mappedBy = "livre")
    private Collection<Emprunt> emprunts;
    @OneToMany(mappedBy = "livre")
    private Collection<Avis> avis;
}
