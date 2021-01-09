import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CalendarViewComponent } from './calendar-view/calendar-view.component';
import { EventsComponent } from './events/events.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {path: '', component: CalendarViewComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: ':date/events', component: EventsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule { }
