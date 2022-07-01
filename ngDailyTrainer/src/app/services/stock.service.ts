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

  constructor(private http: HttpClient, private auth: AuthService) { 

  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  getStockList(){
    return this.stocks;
  }


  loadStocks(){
    this.getAvailableStocks().subscribe(
      (data) => {
        this.stocks = data;
      },
      (err) => {
        console.log("Error getting stocks");
      }
    )
  }

  getAvailableStocks() {
    return this.http.get<Stock[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err:any) => {
        return throwError(() => new Error('Error getting stock list'));
      }));

  }

  contains(symbol:string): boolean{
    for(let stock of this.stocks){
      if(stock.symbol == symbol){
        return true;
      }
    }
    return false;
  }
}
