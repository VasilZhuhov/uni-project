
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient,
    private router: Router) { }

  authenticate(user): Observable<any> {
    return this.http.post("http://localhost:8080/authenticate", user);
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('user')
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('user')
    this.router.navigateByUrl('/login');
  }
}