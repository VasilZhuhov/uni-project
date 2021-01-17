import { first } from 'rxjs/operators';
import { EventRequestsDataModel } from './event-requests-data-model';
import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';

@Component({
    selector: 'app-event-requests',
    templateUrl: './event-requests.component.html',
    styleUrls: ['./event-requests.component.css']
})
export class EventRequestsComponent implements OnInit {
    events: Array<EventRequestsDataModel> = [];

    constructor(private appService: AppService) { }

    ngOnInit(): void {
        this.appService.getUnacceptedEvents()
            .subscribe(events => {
                this.events = events;
            });
    }

    acceptEvent(event, index) {
        this.appService.acceptEvent(event.id)
            .pipe(
                first()
            )
            .subscribe(res => {
                this.events.splice(index, 1);
            });
    }

    ignoreEvent(event, index) {
        this.appService.ignoreEvent(event.id)
            .pipe(
                first()
            )
            .subscribe(res => {
                this.events.splice(index, 1);
            });
    }
}