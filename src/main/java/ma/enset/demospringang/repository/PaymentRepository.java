package ma.enset.demospringang.repository;

import ma.enset.demospringang.entities.Payment;
import ma.enset.demospringang.entities.PaymentStatus;
import ma.enset.demospringang.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
