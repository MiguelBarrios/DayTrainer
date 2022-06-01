import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private url = environment.baseUrl + 'api/users'

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

getUserByUsername(){
  return this.http.get<User>(this.url + '/' +'name' +'/'+ localStorage.getItem('username'), this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

update(user:User){
  return this.http.put<User>(this.url, user, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

getAllUsers(){
  return this.http.get<User[]>(this.url, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

delete(id:number){
  return this.http.delete<void>(this.url+ '/'+ id, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}


getUserFollowingList(userId: number) {
  return this.http.get<User[]>(this.url + '/' + userId +  '/following', this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

}
