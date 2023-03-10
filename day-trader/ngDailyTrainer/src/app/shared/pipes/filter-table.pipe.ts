import { Stock } from '../../models/stock';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterTable'
})
export class FilterTablePipe implements PipeTransform {

  transform(stocks: Stock[], searchValue: string): Stock[] {
    if(!stocks || !searchValue) {
      return stocks;
    }
    return stocks.filter(stock =>stock.symbol.toLocaleUpperCase().includes(searchValue.toLocaleUpperCase())
    || stock.name.toLocaleUpperCase().includes(searchValue.toLocaleUpperCase()));

    }

}
