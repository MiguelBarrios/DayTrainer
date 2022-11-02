export class Movers {
  change:number | null;
  description: string | null;
  direction:string | null;
  last:number | null;
  symbol:string | null;
  totalVolume:number | null;
  constructor(  change:number | null, description: string | null,
                direction:string | null, last:number | null,
                symbol:string | null, totalVolume:number | null){
      this.change = change;
      this.description = description;
      this.direction = direction;
      this.last = last;
      this.symbol = symbol;
      this.totalVolume = totalVolume;
    }


}
