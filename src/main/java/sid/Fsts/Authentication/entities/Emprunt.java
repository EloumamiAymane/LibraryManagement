package sid.Fsts.Authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateEmp;
    private Date dateRet;
    private String status;
    private boolean valide;
    @ManyToOne
    private Livre livre;
    @ManyToOne
    private User user;
}
