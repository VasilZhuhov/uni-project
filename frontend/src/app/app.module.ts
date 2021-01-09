import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarViewComponent } from './calendar-view/calendar-view.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EventsComponent } from './events/events.component';

@NgModule({
  declarations: [
    AppComponent,
    CalendarViewComponent,
    LoginComponent,
    RegisterComponent,
    EventsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
