import { AuthService } from 'src/app/services/auth.service';
import { environment } from './../../environments/environment';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Stock } from '../models/stock';
import { catchError, tap, throwError } from 'rxjs';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class StockService {

private url = environment.baseUrl + 'api/stocks'
stocks: Stock[] | null = null;

  constructor(private http: HttpClient, private auth: AuthService) { }

  getStocks(){
      if(this.stocks){        
        return of(this.stocks);
      }
      else{
        return this.http.get<Stock[]>(this.url, this.auth.getHttpOptions()).pipe(
          tap(
            (stocks) => {
              this.stocks = stocks;
            }
          ),
          catchError((err:any) => {
            return throwError(() => new Error('Error getting stock list'));
          }));
      }
  }

  contains(symbol:string): boolean{
    if(this.stocks)
    for(let stock of this.stocks){
      if(stock.symbol == symbol){
        return true;
      }
    }
    return false;
  }

}
