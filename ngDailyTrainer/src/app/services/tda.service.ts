import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Candle } from '../models/candle';
import { TDAQuote } from '../models/tdaquote';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TDAService {

  private tda = "https://api.tdameritrade.com/v1/marketdata";
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

  getCandles10Day(symbol:string){
    var url = this.tda + "/" + symbol + "/pricehistory?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40&periodType=day&period=10&frequencyType=minute&frequency=60";
    return this.http.get<Object>(url).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in TDAService.getCandles10day10min()');
      })
    )

  }


}
