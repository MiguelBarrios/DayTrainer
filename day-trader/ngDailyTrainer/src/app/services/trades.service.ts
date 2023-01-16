import { StockPosition } from './../models/stock-position';
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

  private url = environment.baseUrl + 'api/v1/trades'

  constructor(private http:HttpClient, private auth:AuthService) { }

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
    return this.http.post<Trade>(this.url, trade, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error creating new Trade');
      })
    )
  }

  getUserTrades(){
    return this.http.get<Trade[]>(this.url, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in getUserTrades() request');
      })
    )
  }

  getStockPosition(symbol:string){
    var url = this.url + "/position/" + symbol;
    return this.http.get<StockPosition>(url, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in getStockPosition() request');
      })
    )
  }

  getUserPositions(){
    var url = this.url + "/position";
    return this.http.get<StockPosition[]>(url, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in getuserPositions() request');
      })
    )
  }

  getFriendsTrade(username:string){
    var url = this.url + "/users/" + username;
    return this.http.get<Trade[]>(url, this.auth.getHttpOptions()).pipe(
      catchError((err: any) => {
        return throwError('Error in getuserPositions() request');
      })
    )
  }

  isMarketOpen(){
    var url = this.url + "/isMarketOpen";
    return this.http.get<Boolean>(url, this.auth.getHttpOptions()).pipe(
      catchError((err:any) => {
        return throwError(err);
      })
    )
  }


}
