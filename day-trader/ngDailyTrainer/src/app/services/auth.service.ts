import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';
import { AccountService } from './account.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient, private router: Router) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  login(username: string |null, password: string | null): Observable<User> {
    // Make credentials
    const credentials = this.generateBasicAuthCredentials(username, password);
    // Send credentials as Authorization header specifying Basic HTTP authentication
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };

    // Create GET request to authenticate credentials
    return this.http.get<User>(this.url + 'authenticate', httpOptions).pipe(
      tap((newUser) => {
        // While credentials are stored in browser localStorage, we consider
        // ourselves logged in.
        localStorage.setItem('credentials', credentials);
        localStorage.setItem('role',newUser.role)
        localStorage.setItem('username',newUser.username);
        return newUser;
      }),
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('AuthService.login(): error logging in user.')
        );
      })
    );
  }

  register(user: User): Observable<User>  {
    // Create POST request to register a new account
    return this.http.post<User>(this.url + 'register', user).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('AuthService.register(): error registering user.')
        );
      })
    );
  }

  logout(): void {
    localStorage.removeItem('credentials');
    this.router.navigateByUrl('home');

  }

  checkLogin(): boolean {
    if (localStorage.getItem('credentials')) {
      return true;
    }
    return false;
  }

  generateBasicAuthCredentials(username: string | null, password: string | null): string {
    return btoa(`${username}:${password}`);
  }

  getCredentials(): string | null {
    return localStorage.getItem('credentials');
  }

  isAdmin():boolean{
    if(localStorage.getItem('role') === 'admin'){
      return true;
    }else{
      return false;
    }
  }
}
