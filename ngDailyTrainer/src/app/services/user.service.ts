import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.baseUrl + 'api/users'

  constructor(private http: HttpClient, private auth: AuthService) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

index() {
  return this.http.get<User[]>(this.url, this.getHttpOptions()).pipe(
    catchError((err:any) => {
      console.log(err);
      return throwError(() => new Error('KABOOM - Stock list cannot be retrieved.'));
    }));
}

getLeaders() {
  return this.http.get<User[]>(this.url + '/leaders', this.getHttpOptions()).pipe(
    catchError((err:any) => {
      console.log(err);
      return throwError(() => new Error('KABOOM - Stock list cannot be retrieved.'));
    }));
}

}
