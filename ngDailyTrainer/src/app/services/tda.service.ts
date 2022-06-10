import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TDAQuote } from '../models/tdaquote';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TDAService {

  private url = environment.baseUrl + 'api/tda'

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

  getQuote(symbol:string){
    var url =   this.url + "/quote/" + symbol;
    return this.http.get<TDAQuote>(url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in TDAService.getQuote()');
      })
    )
  }

}
