import { EventRequestsComponent } from './event-requests/event-requests.component';
import { AddEventComponent } from './add-event/add-event.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarViewComponent } from './calendar-view/calendar-view.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EventsComponent } from './events/events.component';
import {AppService} from './app.service'
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from './authentication.service';
import { EditEventComponent } from './edit-event/edit-event.component';

@NgModule({
  declarations: [
    AppComponent,
    CalendarViewComponent,
    LoginComponent,
    RegisterComponent,
    EventsComponent,
    AddEventComponent,
    EventRequestsComponent,
    EditEventComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AppService,
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
