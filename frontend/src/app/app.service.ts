import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AppService {

  constructor(private http: HttpClient) { }

  register(user): Observable<any> {
    return this.http.post('http://localhost:8080/users', user);
  }

  getUsers(): Observable<any> {
    return this.http.get('http://localhost:8080/users');
  }

  createEvent(event) {
    const user = JSON.parse(sessionStorage.getItem('user'));
    return this.http.post('http://localhost:8080/' + user.id + '/events', event);
  }

  getAcceptedEventsByTimestamp(timestamp): Observable<any> {
    const user = JSON.parse(sessionStorage.getItem('user'));
    return this.http.get('http://localhost:8080/' + user.id + '/events/' + timestamp + '?isAccepted=true');
  }

  getUnacceptedEvents(): Observable<any> {
    const user = JSON.parse(sessionStorage.getItem('user'));
    return this.http.get('http://localhost:8080/' + user.id + '/events');
  }

  acceptEvent(eventId): Observable<any> {
    const user = JSON.parse(sessionStorage.getItem('user'));
    return this.http.put('http://localhost:8080/' + user.id + '/events/' + eventId, null);
  }

  ignoreEvent(eventId): Observable<any> {
    const user = JSON.parse(sessionStorage.getItem('user'));
    return this.http.delete('http://localhost:8080/' + user.id + '/events/' + eventId);
  }

  updateEvent(eventId, newEvent): Observable<any> {
    return this.http.put('http://localhost:8080/events/' + eventId, newEvent);
  }

  deleteEvent(eventId): Observable<any> {
    return this.http.delete('http://localhost:8080/events/' + eventId);
  }

  getEvent(eventId): Observable<any> {
    return this.http.get('http://localhost:8080/events/' + eventId);
  }
}
