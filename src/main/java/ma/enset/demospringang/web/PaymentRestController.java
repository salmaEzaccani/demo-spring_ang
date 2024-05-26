package ma.enset.demospringang.web;

import ma.enset.demospringang.entities.Payment;
import ma.enset.demospringang.entities.PaymentStatus;
import ma.enset.demospringang.entities.PaymentType;
import ma.enset.demospringang.entities.Student;
import ma.enset.demospringang.repository.PaymentRepository;
import ma.enset.demospringang.repository.StudentRepository;
import ma.enset.demospringang.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*") //jajoute ca apres que je fais frontend cad que cote backend jautorise tous les domaines qui fait appel a ce service
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    //ON VA FAIRE LINJECTION DE CA VIA LE CONSTRUCTEUR
    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping (path="/payments")  //pour acceder a cette methode on doit utiliser GetMapping
public List<Payment> allPayments(){
        return paymentRepository.findAll();
}

    @GetMapping (path="/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code){ //les payments dun etudiant
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping (path="payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){ //les payments dun etudiant
        return paymentRepository.findByStatus(status);
    }

    @GetMapping (path="payments/byType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type){ //les payments dun etudiant
        return paymentRepository.findByType(type);
    }



@GetMapping(path="/payments/{id}")//on suppose que id est dans path
public Payment getPaymentById(@PathVariable Long id){//on supose que id est dans path et pour le recupere on utilise @PathVariable
        return paymentRepository.findById(id).get();
}

    @GetMapping(path="/students")
public List<Student> allStudents(){
        return studentRepository.findAll();
}

    @GetMapping(path="/students/{code}")
public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
}

    @GetMapping(path="/studentsByProgramId/{id}")
public List<Student> getStudentByProgramId(@RequestParam String programId){//cad qon va lenvoyer sous forme dun parametre
        return studentRepository.findByProgramId(programId);
}



//*****PUISQUE IL YA DES TREITEMENTS LOONG ON LES METS DANS LA COUCHE SERVICE*******
@PutMapping("/payments/{id}")   //puisque il sagit dune maj update on va utiliseer **PUTMAPPING**
public Payment updatePaymentStatus(@RequestParam PaymentStatus status,@PathVariable Long id){
    return paymentService.updatePaymentStatus(status, id);
}


  @PostMapping(path="/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  //puisquil sagit dun ajour on fait POST  et on va specifier le type par CONSUMES
public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,double amount,  //ajouter un payment avec un fichier pour ca on fait ****MULTIPARTFILE****
                           PaymentType type,String studentCode) throws IOException {

  return this.paymentService.savePayment(file,date,amount,type,studentCode);
    }


    //creer une methode qui permet de consulter un fichier
    @GetMapping(path="/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)//sil ya image je met
     public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {  //un tableau de byte pour consuletr le fichier
        return paymentService.getPaymentFile(paymentId);
    }

}
