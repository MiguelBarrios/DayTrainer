import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'buySell'
})
export class BuySellPipe implements PipeTransform {

  transform(isBuy: boolean | null): string {
    if(null){
      return "";
    }
    return (isBuy) ? "Buy" : "Sell";
  }

}
