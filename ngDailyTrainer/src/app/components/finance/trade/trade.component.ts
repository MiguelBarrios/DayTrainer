import { TradesService } from 'src/app/services/trades.service';
import { Component, OnInit } from '@angular/core';
import { DatePipe, DATE_PIPE_DEFAULT_TIMEZONE } from '@angular/common';
import { ThisReceiver } from '@angular/compiler';
import { ActivatedRoute } from '@angular/router';
import { Trade } from 'src/app/models/trade';
import { UsersService } from 'src/app/services/users.service';
import { SingleStockViewComponent } from '../single-stock-view/single-stock-view.component';


@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {

  errorSMS:string = ""
  errorFlag: boolean = false;

  accountBalance: Number = 0;
  symbol:string | null = "";
  userTrades:Trade[] = [];

  newTrade = new Trade();
  action = "Buy";
  orderType = "Market";

  quantity: number = 0;
  totalCost:number = 0;


  missingQuantitySMS = "";
  missingSymbolSMS = "";

  constructor( private route: ActivatedRoute,private date:DatePipe, private tradeService: TradesService,
    private ssv:SingleStockViewComponent,
    private userService:UsersService) { }


  ngOnInit(): void {
    this.symbol = this.route.snapshot.paramMap.get('symbol');
    this.getUserTrades();
    this.getAccountBalance();
    if(this.symbol){
      this.newTrade.stock.symbol = this.symbol;
    }
  }

  updateExtimate(){

    if(this.action == "Buy"){
      if(this.newTrade.quantity){
        if(this.ssv.quote){
          this.totalCost = this.newTrade.quantity * this.ssv.quote.lastPrice;
        }
      }
      console.log(this.totalCost);

      if(this.totalCost >  this.accountBalance){
        this.errorSMS = "incuficient funds";
        this.errorFlag = true;
      }else{
        this.errorSMS = "";
        this.errorFlag = false;
      }
    }
    else{
      var sharesToSell = this.newTrade.quantity;
      if(sharesToSell){
        if(sharesToSell > this.ssv.numberOfShares){
          console.error("Can only sell: " + this.ssv.numberOfShares);
          this.errorSMS = "Only " + this.ssv.numberOfShares + " available for sale";
          this.errorFlag = true;
          return;
        }else{
          this.errorSMS = '';
          this.errorFlag = false;
        }
      }
    }

  }



  submitTrade(){
    if(this.totalCost > this.accountBalance){
      console.error("Incuficiunt funds");
      return;
    }
    this.newTrade.createdAt = this.date.transform(Date.now(),"YYYY-MM-ddThh:mm:ss");
    this.newTrade.buy = (this.action == "Buy");
    this.newTrade.orderType.name = this.orderType;
    this.newTrade.orderType.id = (this.orderType == "Market") ? 1 : 2;
    let currentPrice = document.getElementById("currentStockPrice")?.textContent;
    if(currentPrice){
      this.newTrade.pricePerShare = parseFloat(currentPrice);
    }


    this.missingQuantitySMS = (!this.newTrade.quantity) ? "Quantity Required" : "";
    this.missingSymbolSMS = (!this.newTrade.stock.symbol) ? "Symbol Required" : "";

    if(!this.newTrade.quantity || !this.newTrade.stock.symbol){
      console.log("Error returned");
      return;
    }
    console.log("Not error");

    this.tradeService.createTrade(this.newTrade).subscribe(
      (data) => {
        console.log("New Trade Created");
        console.log(data);
        this.userTrades.push(data);
        this.userTrades = [...this.userTrades]
        if(this.symbol){
          this.ssv.getUserPositionInfo(this.symbol);
        }
        this.getAccountBalance();
      },
      (error) => {
        console.log("Observable got and error " + error)
      }
    )
  }

  getUserTrades(){
    this.tradeService.getUserTrades().subscribe(
      (data) => {
        this.userTrades = data;
      },
      (error) => {
        console.log("Observable got and error " + error)
      }
    )
  }

  getAccountBalance(){
    this.userService.accountBalance().subscribe(
      (data) => {
        this.accountBalance = data;
      },
      (error) => {

      }
    )
  }



}
