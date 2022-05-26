import { Stock } from './stock';
import { User } from './user';
import { QueryFlags } from "@angular/compiler/src/render3/view/compiler";
import { OrderType } from './order-type';

export class Trade {

  id: number;
  pricePerShare: number;
  quantity: number;
  createdAt: string;
  completionDate: string;
  user: User;
  stockSymbol: Stock;
  notes: string;
  comments: Comment[];
  strikePrice: number;
  orderType: OrderType;

constructor(id: number = 0, pricePerShare: number = 0, quantity: number = 0, createdAt: string = "",
completionDate: string = "", user: User, stockSymbol: Stock, notes: string = "",
comments: Comment[], strikePrice: number = 0, orderType: OrderType ) {
  this.id = id;
  this.pricePerShare =pricePerShare;
  this.quantity = quantity;
  this.createdAt = createdAt;
  this.completionDate = completionDate;
  this.user = user;
  this.stockSymbol = stockSymbol;
  this.notes = notes;
  this.comments = comments;
  this.strikePrice = strikePrice;
  this.orderType = orderType;
};

}
