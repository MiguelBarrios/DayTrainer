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

getUserByUsername(){
  return this.http.get<User>(this.url + '/' +'name' +'/'+ localStorage.getItem('username'), this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

update(user:User){
  return this.http.put<User>(this.url, user, this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error updating user');
    })
  )
}

update2(user:User){
  let url = this.url + "/info"
  return this.http.put<User>(url, user,this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      return throwError("Error update user login info");
    })
  )
}

getAllUsers(){
  return this.http.get<User[]>(this.url, this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

delete(id:number){
  return this.http.delete<void>(this.url+ '/'+ id, this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
}

getUserFollowingList(userId: number) {
  return this.http.get<User[]>(this.url + '/' + userId +  '/following', this.auth.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError('Error creating new Trade');
    })
  )
  }

  getUserWithUsername(username:string){
    return this.http.get<User>(this.url +'/' +'name/'+ username, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error finding username with username');
      })
    )
  }

}



