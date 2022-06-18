import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private url = environment.baseUrl +  'api/users';

  constructor(private http:HttpClient, private auth:AuthService) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  getUserAccountBalance() {
      return this.http.get<number>(this.url + '/accountbalance', this.getHttpOptions()).pipe(
        catchError((err:any) => {
          console.log(err);
          return throwError(() => new Error('Error getting user account balance'));
        })
      )
  }
}
