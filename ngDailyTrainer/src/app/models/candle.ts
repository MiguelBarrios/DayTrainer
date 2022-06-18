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
