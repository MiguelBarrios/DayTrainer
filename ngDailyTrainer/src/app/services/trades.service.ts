import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Movers } from '../models/movers';

@Injectable({
  providedIn: 'root'
})
export class TradesService {

  constructor(private http:HttpClient) { }

  topMovers(){
    var url = "https://api.tdameritrade.com/v1/marketdata/$COMPX/movers?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40";
    return this.http.get<Movers[]>(url).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('KABOOM');
      })
    )
  }
}
