import { last } from "rxjs";

export class TDAQuote {
  symbol:string;
  netChange:number;
  volatility:number;
  WkHigh52:number;
  WkLow52:number;
  peRatio:number;
  divAmount:number;
  divYield:number;
  divDate:string;
  lastPrice:number;
  description:string;
  totalVolume:number;
  openPrice:number;
  highPrice:number;
  lowPrice:number;
  closePrice:number;
  constructor(  symbol:string,
    netChange:number,
    volatility:number,
    WkHigh52:number,
    WkLow52:number,
    peRatio:number,
    divAmount:number,
    divYield:number,
    divDate:string,
    lastPrice:number,
    description:string,
    totalVolume:number, 
    openPrice:number,
    highPrice:number,
    lowPrice:number,
    closePrice:number){
      this.symbol = symbol;
      this.netChange = netChange;
      this.volatility = volatility;
      this.WkHigh52 = WkHigh52;
      this.WkLow52 = WkLow52;
      this.peRatio = peRatio;
      this.divAmount = divAmount;
      this.divYield = divYield;
      this.divDate = divDate;
      this.lastPrice = lastPrice;
      this.description = description;
      this.totalVolume = totalVolume;
      this.openPrice = openPrice;
      this.highPrice = highPrice;
      this.lowPrice = lowPrice;
      this.closePrice = closePrice;
    }



}
