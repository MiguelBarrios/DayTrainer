import { Pipe, PipeTransform } from '@angular/core';
import { Stock } from '../models/stock';

@Pipe({
  name: 'sectorPipe'
})
export class SectorPipePipe implements PipeTransform {

  transform(stocks: Stock[],searchTerm:string): Stock[] {
    if(searchTerm == ''){
      return [];
    }
    return stocks.filter(s => s.sector.startsWith(searchTerm));
  }

}
