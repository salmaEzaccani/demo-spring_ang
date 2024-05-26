import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit{
   public payments :any; //pour stocker les donnes recuperer
    public  dataSource: any;
    public displayedColumns = ['id','date','amount','type','status','firstName']; //pour liste des colonnes quon veut afficher

   @ViewChild(MatPaginator) paginator! : MatPaginator;
   @ViewChild(MatSort) sort! : MatSort;  //!signifie que je vais pas linitialiser

  constructor(private http: HttpClient) {
  }

  ngOnInit(){
    //je vais envoyer une requete au backend
    this.http.get("http://localhost:8021/payments") //CA c le PORT du backend
      .subscribe({
        next : data => {
         this.payments = data;
         this.dataSource=new MatTableDataSource(this.payments);//cad que les payments je vai les stocker dans table
         this.dataSource.paginator=this.paginator;
         this.dataSource.sort=this.sort;
        },
        error : err=>{
          console.log(err);
        }
      })
  }

}
