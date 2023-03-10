import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Account } from '../models/account';
import { AuthService } from './auth.service';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private url = environment.baseUrl +  'api/v1/account';
  private userAccount: Account | null= null;

  constructor(private http:HttpClient, private auth:AuthService) {}

  refreshAccountData(){
    return this.http.get<Account>(this.url, this.auth.getHttpOptions()).pipe(
      tap(
        (account) => {
          console.log("tapping into account request");
          console.log(account);
          this.userAccount = account;
        }
      ),
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('Error getting user account'));
      })
    )
  }

  getUserAccount(){
    if(this.userAccount){      
      return of(this.userAccount);
    }
    else{
      return this.http.get<Account>(this.url, this.auth.getHttpOptions()).pipe(
        tap(
          (account) => {
            this.userAccount = account;
          }
        ),
        catchError((err:any) => {
          return throwError(() => new Error('Error getting user account'));
        })
      )
    }
  }
}
