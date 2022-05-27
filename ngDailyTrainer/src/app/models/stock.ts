export class Stock {

  symbol: string;
  name: string;
  open: number;
  high: number;
  low: number;
  price: number;
  volume: number;
  previousClose: number;
  change: number;

  constructor(
    symbol: string = '', name: string = '', open: number = 0,high: number = 0, low: number = 0,
    price: number = 0,volume: number = 0,previousClose: number = 0,change: number = 0 ) {
    this.symbol = symbol;
    this.name = name;
    this.open = open;
    this.high = high;
    this.low = low;
    this.price = price;
    this.volume = volume;
    this.previousClose = previousClose;
    this.change = change;
  };}
