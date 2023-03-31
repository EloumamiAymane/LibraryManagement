package sid.Fsts.Authentication.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private boolean Active;
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nom;
    private String prenom;

    private String telephone;
    private String CIN;

    private String photo;
    private Date dateNaissance;
//EAger Importer l user Avec Ses Roles
@ManyToMany(fetch = FetchType.EAGER)
private Collection<Role>roles=new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Collection<Emprunt> emprunts;
    @OneToMany(mappedBy = "user")
    private Collection<Avis> avis;
    @OneToMany(mappedBy = "user")
    private Collection<Penalite>penalites;
    @OneToMany(mappedBy = "user")
    private Collection<Notification>notifications;
    public User( String username,boolean active,String password){
        this.username=username;
        this.password=password;
        this.Active=active;
    }
}
