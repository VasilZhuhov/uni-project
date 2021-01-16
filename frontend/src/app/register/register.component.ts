import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { first } from 'rxjs/operators';

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

  constructor(private appService: AppService) { }

  ngOnInit(): void {
  }

  register() {
    if(this.form.password == this.passwordConfirm) {
      this.appService.register(this.form).pipe(
        first()
      ).subscribe(res => {
        console.log(res);
      });
    }
  }

}
