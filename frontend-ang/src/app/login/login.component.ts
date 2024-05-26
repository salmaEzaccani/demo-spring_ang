import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  public loginForm! :FormGroup;

  constructor(private fb : FormBuilder, private autService : AuthService, private router:Router ) {
  }

  ngOnInit(): void {
    this.loginForm =this.fb.group({ //c pour initialiser loginForm
      username : this.fb.control(''),
      password : this.fb.control('')
    });
  }

  login() {
    let username=this.loginForm.value.username;
    let password=this.loginForm.value.password;
    //on va faire un systeme dauth dans service quon a cree
    let auth:boolean = this.autService.login(username,password);
    if(auth==true){ //si true alors je vais passer vers admintemplate  donc jai besoin dinjecter routage routeur
     this.router.navigateByUrl("/admin")
    }
  }
}
