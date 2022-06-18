export class StockPosition {
  symbol:string;
  numberOfShares:number;
  avgCostPerShare:number;
  lastPrice:number;
  constructor(  symbol:string,
    numberOfShares:number,
    avgCostPerShare:number,
    lastPrice:number){
      this.symbol = symbol;
      this.numberOfShares = numberOfShares;
      this.avgCostPerShare = avgCostPerShare;
      this.lastPrice = lastPrice;
    }
}
