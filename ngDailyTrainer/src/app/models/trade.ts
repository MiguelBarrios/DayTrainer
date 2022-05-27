import { Stock } from './stock';
import { User } from './user';
import { QueryFlags } from "@angular/compiler/src/render3/view/compiler";
import { OrderType } from './order-type';

export class Trade {

  id: number | null;
  buy: boolean | null;
  pricePerShare: number | null;
  quantity: number | null;
  createdAt: string | null;
  completionDate: string | null;
  stock: Stock;
  notes: string | null;
  strikePrice: number | null;
  orderType: OrderType;

constructor(id: number | null = 0, buy: boolean | null = null, pricePerShare: number | null = 0, quantity: number | null = null, createdAt: string | null = "",
completionDate: string | null = "", notes: string | null= "",
 strikePrice: number | null = 0) {
  this.id = id;
  this.pricePerShare =pricePerShare;
  this.quantity = quantity;
  this.createdAt = createdAt;
  this.completionDate = completionDate;
  this.stock = new Stock();
  this.notes = notes;
  this.strikePrice = strikePrice;
  this.orderType = new OrderType();
  this.buy = buy;
};

}
