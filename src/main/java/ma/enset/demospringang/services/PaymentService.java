package ma.enset.demospringang.services;

import ma.enset.demospringang.entities.Payment;
import ma.enset.demospringang.entities.PaymentStatus;
import ma.enset.demospringang.entities.PaymentType;
import ma.enset.demospringang.entities.Student;
import ma.enset.demospringang.repository.PaymentRepository;
import ma.enset.demospringang.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service @Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }


    public Payment savePayment(MultipartFile file, LocalDate date, double amount,  //ajouter un payment avec un fichier pour ca on fait ****MULTIPARTFILE****
                               PaymentType type, String studentCode) throws IOException {

        //une fois quon recupere les donnes on va envoyer le fichier dans un dossier
        Path folderPath= Paths.get(System.getProperty("user.home"),"enset-data","payments");   //on va specifier le path dabord ou on va stocker le fihcier ****System.getProperty("user.home") """le chemin des dossier de utilisateurs"""et dans ce chemin on va chercher le doosier quon va appeler par ex enset et dans enset on va maitre dossier payments
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName= UUID.randomUUID().toString();//cree un nom de fihcier qui est unique
        Path filePath= Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);//on va copier le fichier quon a passer dans les parametres dans FILEPATH

        Student student=studentRepository.findByCode(studentCode);
        Payment payment= Payment.builder()     //APRS QUON A ENREGISTRER LE FIHCIER ON VA CRER LE PAYMENT QUON VA STOCKER DANS BASE DE DONNES
                .date(date).type(type).student(student)
                .amount(amount)
                .file(filePath.toUri().toString())  //le convertir en URI
                .status(PaymentStatus.CREATED)
                .build();

        return paymentRepository.save(payment);
    }



    public Payment updatePaymentStatus(PaymentStatus status,@PathVariable Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }



    public byte[] getPaymentFile( Long paymentId) throws IOException {
        Payment payment=paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
