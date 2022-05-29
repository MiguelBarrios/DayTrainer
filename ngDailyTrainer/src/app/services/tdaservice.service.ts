import { AuthService } from 'src/app/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TDAserviceService {

  private url = "https://api.tdameritrade.com/v1/marketdata/";

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

  getStockStats(symbol:string){
    var targetUrl = this.url + symbol + "/quotes?apikey=V6DTLTMJNGVWTXDOGACC59RLTM6NTQGH%40";
    return this.http.get<Object>(targetUrl).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error in TDAService.getstockStats()');
      })
    )

  }

}
