import { AuthService } from 'src/app/services/auth.service';
import { environment } from './../../environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Stock } from '../models/stock';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class StockService {

private url = environment.baseUrl + 'api/stocks'
stocks: Stock[] = [];

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
    return this.http.get<Stock[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err:any) => {
        console.log(err);
        return throwError(() => new Error('KABOOM - Stock list cannot be retrieved.'));
      }));

}
}
