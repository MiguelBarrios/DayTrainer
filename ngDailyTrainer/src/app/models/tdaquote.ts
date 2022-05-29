export class TDAQuote {
  netChange:number;
  volatility:number;
  WkHigh52:number;
  WkLow52:number;
  peRatio:number;
  divAmount:number;
  divYield:number;
  divDate:string;
  constructor(  netChange:number,
    volatility:number,
    WkHigh52:number,
    WkLow52:number,
    peRatio:number,
    divAmount:number,
    divYield:number,
    divDate:string){
      this.netChange = netChange;
      this.volatility = volatility;
      this.WkHigh52 = WkHigh52;
      this.WkLow52 = WkLow52;
      this.peRatio = peRatio;
      this.divAmount = divAmount;
      this.divYield = divYield;
      this.divDate = divDate;
    }



}
