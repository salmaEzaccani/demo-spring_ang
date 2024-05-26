package ma.enset.demospringang;

import ma.enset.demospringang.entities.Payment;
import ma.enset.demospringang.entities.PaymentStatus;
import ma.enset.demospringang.entities.PaymentType;
import ma.enset.demospringang.entities.Student;
import ma.enset.demospringang.repository.PaymentRepository;
import ma.enset.demospringang.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoSpringAngApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoSpringAngApplication.class, args);
	}

	@Bean   //permette dexecuter un traitement au demmarage
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										PaymentRepository paymentRepository){
		return args -> {

			//**********CREATION DES ETUDIANTS**********
           studentRepository.save(Student.builder().id(UUID.randomUUID().toString())  //genere id automatiquement
				   .firstName("Salma").code("112233").programId("SDIA")
				   .build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Hassan").code("112244").programId("SDIA")
					.build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Fatina").code("112255").programId("GLSID")
					.build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Assia").code("112266").programId("BDCC")
					.build());

			//**********CREATION PAYMENT POUR LES ETUDIANTS**********
			PaymentType[] paymentType = PaymentType.values();//permet de returner la liste des valeurs
			Random random = new Random();
			studentRepository.findAll().forEach(st->{ //je vais returner la liste des etudiants cree
				int index = random.nextInt(paymentType.length);
				for (int i=0;i<10;i++){ //pour chaque student crre 10 payment
					Payment payment = Payment.builder().amount(1000+(int) (Math.random()*20000))//generer un montant aleatoire
							.type(paymentType[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
                     paymentRepository.save(payment);
				}
			});
		};
	}
}
