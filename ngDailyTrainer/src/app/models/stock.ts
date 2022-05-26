export class Stock {

  id: string;
  name: string;
  exchangeName: string;

  constructor(id: string = "", name: string =  "", exchangeName: string = "") {
   this.id = id,
   this.name = name,
   this.exchangeName = exchangeName
  }

}
