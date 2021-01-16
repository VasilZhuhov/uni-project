import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { first } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form = {
    username: '',
    password: '',
    email: ''
  }

  passwordConfirm = '';

  constructor(private appService: AppService,
    private router: Router) { }

  ngOnInit(): void {
  }

  register() {
    if(this.form.password == this.passwordConfirm) {
      this.appService.register(this.form).pipe(
        first()
      ).subscribe(res => {
        sessionStorage.setItem('user', JSON.stringify(this.form));
        this.router.navigateByUrl('/');
      });
    }
  }

}
