import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlphaVantageAPIService {

  private url = 'https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=';
  private endUrl = '&apikey=EQWYNBU1CC0IOGR0';

  constructor(private http:HttpClient) {};


 retrieveAll() {
   return this.http.get<any>(this.url + this.endUrl);
 }


  search(keyword: string) {
  return this.http.get<any[]>(this.url+ keyword + this.endUrl);
  }
}
