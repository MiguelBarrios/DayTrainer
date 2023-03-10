import { Pipe, PipeTransform } from '@angular/core';
import { startWith } from 'rxjs';
import { Stock } from '../../models/stock';

@Pipe({
  name: 'stockFilter'
})
export class StockFilterPipe implements PipeTransform {



  transform(stocks: Stock[],searchTerm:string): Stock[] {
    searchTerm = searchTerm.toUpperCase();
    if(searchTerm == ''){
      return [];
    }
    return stocks.filter(s => s.symbol.startsWith(searchTerm));
  }

}
