import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Trade } from '../models/trade';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private url = environment.baseUrl + 'api/comments'


  constructor(private http: HttpClient, private auth: AuthService) { }

  index(trades:Trade[]) {
    return this.http.get<Comment[]>(this.url, this.auth.getHttpOptions()).pipe(
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('KABOOM - comment list cannot be retrieved.'));
      }));

}
}
