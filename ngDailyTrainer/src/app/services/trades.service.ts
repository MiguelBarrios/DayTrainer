import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Movers } from '../models/movers';
import { Trade } from '../models/trade';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TradesService {

  private tdaUrl = "https://api.tdameritrade.com/v1/marketdata/";

  private url = environment.baseUrl + 'api/trades'

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

  topMovers(){
    var url = "https://api.tdameritrade.com/v1/marketdata/$COMPX/movers?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40";
    return this.http.get<Movers[]>(url).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('KABOOM');
      })
    )
  }

  createTrade(trade:Trade){
    var url = "http://localhost:8089/api/trades";
    return this.http.post<Trade>(url, trade, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error creating new Trade');
      })
    )
  }

  getUserTrades(){
    return this.http.get<Trade[]>(this.url,this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in getUserTrades() request');
      })
    )
  }

  getUserPosition(){

  }

}
