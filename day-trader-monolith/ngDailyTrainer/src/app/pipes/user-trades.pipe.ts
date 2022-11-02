import { Trade } from './../models/trade';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'userTrades'
})
export class UserTradesPipe implements PipeTransform {

  transform(trades: Trade[], ticker: string | null): Trade[] {
    if(!trades || ! ticker || ticker == "all"){
      return trades;
    }

    return trades.filter(trade => trade.stock.symbol == ticker)

  }

}
