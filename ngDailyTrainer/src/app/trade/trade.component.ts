import { TradesService } from 'src/app/services/trades.service';
import { OrderType } from './../models/order-type';
import { Component, OnInit } from '@angular/core';
import { Trade } from '../models/trade';
import { DatePipe, DATE_PIPE_DEFAULT_TIMEZONE } from '@angular/common';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {

  newTrade = new Trade();
  action = "Buy";
  orderType = "";


  constructor(private date:DatePipe, private tradeService: TradesService) { }

  ngOnInit(): void {
  }


  submitTrade(){
    this.newTrade.buy = (this.action == "Buy");
    this.newTrade.createdAt = this.date.transform(Date.now(),"YYYY-MM-ddThh:mm:ss");
    if(this.orderType != ""){
      this.newTrade.orderType.name = this.orderType;
      this.newTrade.orderType.id = (this.orderType == "Market") ? 1 : 2;
    }
    console.log(this.newTrade);

    this.tradeService.createTrade(this.newTrade).subscribe(
      (data) => {
        console.log("New Trade Created");
        console.log(data);
      },
      (error) => {
        console.log("Observable got and error " + error)
      }
    )


  }




}
