export class Stock {

  symbol: string;
  name: string;
  exchangeName: string;

  constructor(symbol: string = "", name: string =  "", exchangeName: string = "") {
   this.symbol = symbol,
   this.name = name,
   this.exchangeName = exchangeName
  }

}
