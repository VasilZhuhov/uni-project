import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form = {
    email: '',
    password: ''
  }

  constructor(private authentication: AuthenticationService,
    private router: Router) { }

  ngOnInit(): void {
  }

  login(){
    this.authentication.authenticate(this.form).subscribe(user => {
      if(user) {
        sessionStorage.setItem('user', JSON.stringify(user));
        this.router.navigateByUrl('/');
      }
    });
  }
}
