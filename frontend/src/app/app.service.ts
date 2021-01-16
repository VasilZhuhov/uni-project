import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AppService {

  constructor(private http: HttpClient) { }

  register(user): Observable<any> {
    return this.http.post('http://localhost:8080/users', user);
  }
}
