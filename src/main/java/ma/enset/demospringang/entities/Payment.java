package ma.enset.demospringang.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



//ENTITE JPA
@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String file;
    @ManyToOne    //un etudiant peut effectuer plusieurs payment
    private Student student;
}
