import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private url = environment.baseUrl + 'api/users/comments'


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
    //i think i should either user users id or call this per individual trade
    return this.http.get<Comment[]>(this.url+'/', this.getHttpOptions()).pipe(
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('KABOOM - comment list cannot be retrieved.'));
      }));

}
}
