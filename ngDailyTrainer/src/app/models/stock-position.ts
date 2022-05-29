export class StockPosition {
  symbol:string;
  numberOfShares:number;
  avgCostPerShare:number;
  constructor(  symbol:string,
    numberOfShares:number,
    avgCostPerShare:number){
      this.symbol = symbol;
      this.numberOfShares = numberOfShares;
      this.avgCostPerShare = avgCostPerShare;
    }
}
