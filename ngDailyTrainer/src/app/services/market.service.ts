import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class MarketService {

  private baseurl = environment.baseUrl + "api/market";

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

  getMarketHours(){
    var url = this.baseurl + "/hours";
    return this.http.get<string>(url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('KABOOM');
      })
    )
  }
}
