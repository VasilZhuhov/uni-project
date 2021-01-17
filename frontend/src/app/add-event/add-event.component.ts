import { AddEventDataModel } from './add-event-data-model';
import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { debounceTime, distinctUntilChanged, first, map } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-add-event',
    templateUrl: './add-event.component.html',
    styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
    submitted = false;
    model = new AddEventDataModel();
    users: any;
    currentTime: any;
    emails: any;
    currentUser: any;
    disabledInputs: boolean[] = [];
    isEdit: boolean = false;

    search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.emails.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    )

    constructor(private appService: AppService,
        private activeRoute: ActivatedRoute,
        private router: Router) { }

    ngOnInit(): void {
        this.activeRoute.queryParams
            .subscribe(params => {
                if(params.isEdit) {
                    this.isEdit = params.isEdit;
                }
            })

        this.disabledInputs.push(true);

        this.currentUser = JSON.parse(sessionStorage.getItem('user'));
        this.model.participants.push(this.currentUser.email);
        this.activeRoute.params
            .subscribe(params => {
                this.currentTime = params.date;
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

        this.model['usersEvent'].push(
            {
            user: JSON.parse(sessionStorage.getItem('user'))
            });

        for(let user of this.model.participants) {
            let dbUser = this.users.find(u => u.email == user.email);
            if(dbUser){
                this.model['usersEvent'].push(
                    {
                        user: dbUser
                    }
                );
            }
        }

        this.appService.createEvent(this.model)
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