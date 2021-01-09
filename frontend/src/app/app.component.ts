import { AppService } from './app.service';
import { Component } from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  
  constructor(private appService : AppService){}

  ngOnInit() {
    this.appService.test()
      .subscribe(res => {
        console.log(res);
      });
  }
}
