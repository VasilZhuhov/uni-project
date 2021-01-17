import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AppService } from '../app.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  todayTimestamp: any;
  events: any;
  user;

  constructor(private activeRoute: ActivatedRoute,
    private router: Router,
    private appService: AppService) { }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('user'));

    this.activeRoute.params
      .subscribe(params => {
        this.todayTimestamp = params['date'];
        this.appService.getAcceptedEventsByTimestamp(this.todayTimestamp)
          .pipe(
            first()
          ).subscribe(events => {
            this.events = events.sort((a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
          });
      });
  }

  delete(index) {
    this.appService.deleteEvent(this.events[index].id)
      .pipe(
        first()
      ).subscribe(res => {
        location.reload();
      });
  }

  edit(index) {
    this.router.navigate([`${this.todayTimestamp}/events/${index}`]);
  }

}
