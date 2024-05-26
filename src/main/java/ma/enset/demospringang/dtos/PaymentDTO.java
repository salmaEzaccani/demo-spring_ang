package ma.enset.demospringang.dtos;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.demospringang.entities.PaymentStatus;
import ma.enset.demospringang.entities.PaymentType;
import ma.enset.demospringang.entities.Student;

import java.time.LocalDate;


//APRES AVOIR SUPPRIMER TOUS LES DONNER QUON A PAS BESOIN COTE UI INTERFACE
 @NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDTO {

    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
}
