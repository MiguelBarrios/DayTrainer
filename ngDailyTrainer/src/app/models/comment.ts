import { Trade } from "./trade";
import { User } from "./user";

export class Comment {

  id :number;
  content:string;
  createdAt:Date | null;
  user:User | null;
  trade:Trade | null;




  constructor(id:number =0,content:string ='',createdAt = null, user = null, trade = null){
    this.id = id
    this.content = content
    this.createdAt = createdAt
    this.user = user
    this.trade = trade
  }
}
