import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-event-requests',
    templateUrl: './event-requests.component.html',
    styleUrls: ['./event-requests.component.css']
})
export class EventRequestsComponent implements OnInit {
    events = [
        {
            eventTitle: 'title 1',
            eventOwner: 'owner 1',
            startTime: new Date(),
            endTime: new Date(),
            location: 'location 1',
            participants: ["participant1", "participant2"],
            additionalInfo: "additional info 1" 
        },
        {
            eventTitle: 'title 2',
            eventOwner: 'owner 2',
            startTime: new Date(),
            endTime: new Date(),
            location: 'location 2',
            participants: null,
            additionalInfo: "additional info 2" 
        }
    ];

    constructor() { }

    ngOnInit(): void {
    }
}