import { AppService } from './app.service';
import { Component } from '@angular/core';
import * as moment from 'moment';
import { AuthenticationService } from './authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(private authService : AuthenticationService){}

  get session() {

    return JSON.parse(sessionStorage.getItem('user'));
  }

  ngOnInit() {

    // this.appService.test()
    //   .subscribe(res => {
    //     console.log(res);
    //   });
  }

  logout() {
    this.authService.logOut();
  }
}
