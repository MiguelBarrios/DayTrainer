import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Account } from '../models/account';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private url = environment.baseUrl +  'api/v1/account';
  private userAccount: Account | null= null;

  constructor(private http:HttpClient, private auth:AuthService) {}

  getUserAccount(){
    return this.http.get<Account>(this.url, this.auth.getHttpOptions()).pipe(
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('Error getting user account'));
      })
    )
  }
}
