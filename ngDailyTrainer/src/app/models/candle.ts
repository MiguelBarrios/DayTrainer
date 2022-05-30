export class Candle {
  open:number;
  high:number;
  low:number;
  close:number;
  volume:number;
  datetime:number;
  constructor(  open:number,
    high:number,
    low:number,
    close:number,
    volume:number,
    datetime:number){
      this.open = open;
      this.high = high;
      this.low = low;
      this.close = close;
      this.volume = volume;
      this.datetime = datetime;
    }
}


  // "open": 119.4,
  // "high": 119.4,
  // "low": 119.4,
  // "close": 119.4,
  // "volume": 100,
  // "datetime": 1652701380000
