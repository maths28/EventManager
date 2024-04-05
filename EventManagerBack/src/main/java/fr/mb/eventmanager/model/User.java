package fr.mb.eventmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

@Entity
@Table(name = "users")
@Data
@DiscriminatorFormula("""
    case
        when role = 'PARTICIPANT' then 'P'
        when role = 'ORGA' then 'O'
        else ''
    end
""")
@DiscriminatorValue("O")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    private String password;
    private String role;
}
