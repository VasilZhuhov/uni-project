import { AddEventDataModel } from '../add-event/add-event-data-model';
import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { debounceTime, distinctUntilChanged, first, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.css']
})
export class EditEventComponent implements OnInit {

  submitted = false;
  model = new AddEventDataModel();
  users: any;
  currentTime: any;
  emails: any;
  currentUser: any;
  disabledInputs: boolean[] = [];
  isEdit: boolean = false;
  eventId: any;

  constructor(private appService: AppService,
      private activeRoute: ActivatedRoute,
      private router: Router) { }

  ngOnInit(): void {
      this.disabledInputs.push(true);

      this.currentUser = JSON.parse(sessionStorage.getItem('user'));
      this.model.participants.push(this.currentUser.email);
      this.activeRoute.params
          .subscribe(params => {
              this.currentTime = params.date;
              this.eventId = params.eventId;
          });

      this.appService.getEvent(this.eventId)
          .pipe(
            first()
          ).subscribe(event => {
            this.model = event;
            this.model['startTime'] = {
              hour: new Date(this.model['startTime']).getHours(),
              minute: new Date(this.model['startTime']).getMinutes()
            };

            this.model['endTime'] = {
              hour: new Date(this.model['endTime']).getHours(),
              minute: new Date(this.model['endTime']).getMinutes()
            };

            this.model.participants = {}
          });

      this.appService.getUsers()
          .pipe(
              first()
          ).subscribe(users => {
              this.users = users;
              this.emails = users.map(u => u.email);
          });
  }

  onSubmit() {
      this.model['usersEvent'] = [];
      let startDate = new Date();
      startDate.setTime(this.currentTime);
      startDate.setHours(this.model.startTime.hour);
      startDate.setMinutes(this.model.startTime.minute);
      this.model.startTime = startDate.valueOf();

      let endDate = new Date();
      endDate.setTime(this.currentTime);
      endDate.setHours(this.model.endTime.hour);
      endDate.setMinutes(this.model.endTime.minute);
      this.model.endTime = endDate.valueOf();

      this.appService.updateEvent(this.eventId, this.model)
          .pipe(
              first()
          ).subscribe(res => {
              this.router.navigateByUrl('/');
          });
      this.submitted = true;
  }

  trackByFn(index: any, item: any) {
      return index;
   }

  addEmail() {
      this.model.participants.push('');
      this.disabledInputs = this.disabledInputs.map(di => true);

      this.disabledInputs.push(false);
  }

  removeEmail(index) {
      this.disabledInputs.splice(index, 1);
      this.model.participants.splice(index, 1);
  }

  editEmail(index) {
      this.disabledInputs[index] = !this.disabledInputs[index];
  }
}
