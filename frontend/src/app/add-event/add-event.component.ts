import { AddEventDataModel } from './add-event-data-model';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-add-event',
    templateUrl: './add-event.component.html',
    styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
    submitted = false;
    model = new AddEventDataModel();
    
    constructor() { }

    ngOnInit(): void {
    }

    onSubmit() {
        console.log("submitted model: ", this.model.eventTitle, this.model.startTime, this.model.endTime,
             this.model.location, this.model.participants, this.model.additionalInfo);
        this.submitted = true;
    }
}