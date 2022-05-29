export class Position {
  symbol: string;
  amount: number;
  value: number;
  profit: number;

  constructor(symbol: string,
    amount: number,
    value: number,
    profit: number){
      this.symbol = symbol;
      this.amount = amount;
      this.value = value;
      this.profit = profit;
    }
}
