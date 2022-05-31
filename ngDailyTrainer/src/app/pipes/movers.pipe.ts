import { Movers } from './../models/movers';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'movers'
})
export class MoversPipe implements PipeTransform {

  transform(movers: Movers[],direction:string): Movers[] {

    if(direction == "up"){
      return movers.filter(s => s.direction == "up").slice(0,5);
    }else{
      return movers.filter(s => s.direction == "down").slice(0,5);
    }
  }

}
