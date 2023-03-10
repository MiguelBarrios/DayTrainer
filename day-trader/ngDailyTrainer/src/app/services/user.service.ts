import { HttpClient } from '@angular/common/http';
import { Injectable, Pipe } from '@angular/core';
import { catchError, of, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Trade } from '../models/trade';
import { User } from '../models/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.baseUrl + 'api/users'
  private leaders: User[] | null = null;

  constructor(private http: HttpClient, private auth: AuthService) { }

 getLeaderBoard(){
  if(this.leaders){
    return of(this.leaders);
  }
  else{
    return this.http.get<User[]>(this.url + '/leaders', this.auth.getHttpOptions()).pipe(
      tap(
        (users) => {
          this.leaders = users;
        }
      ),
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('KABOOM - Stock list cannot be retrieved.'));
      }));
   }
  }



}
