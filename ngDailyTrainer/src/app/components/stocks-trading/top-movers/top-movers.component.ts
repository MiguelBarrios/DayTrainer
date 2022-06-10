import { Router } from '@angular/router';

import { Component, OnInit } from '@angular/core';
import { TimeoutInfo } from 'rxjs';
import { Movers } from 'src/app/models/movers';
import { TradesService } from 'src/app/services/trades.service';
import { Stock } from 'src/app/models/stock';
import { StockService } from 'src/app/services/stock.service';

@Component({
  selector: 'app-top-movers',
  templateUrl: './top-movers.component.html',
  styleUrls: ['./top-movers.component.css']
})
export class TopMoversComponent implements OnInit {

  movers:Movers[] = [];
  id:any | null = null;

  stocks: Stock[] = [];
  searchValue: string ="";
  stock: Stock = new Stock;

  constructor(private tradeService:TradesService, private route: Router, private stockService: StockService) { }

  ngOnInit(): void {

   this.getAllStocks();
    this.getTopMovers();
    this.id = setInterval(() => {
      this.getTopMovers();
    }, 10000);
  }

  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
  }

  getTopMovers(){
    this.tradeService.topMovers().subscribe(
      (data) => {
        this.movers = data;
        console.log("***new movers request recieved");
      },
      (error) => {
        console.log("Observable got an error " + error)
      }
    )
  }

  checkTrend(mover:Movers){
    if(mover.direction == "up"){
      return "trendingUp";
    }else{
      return "trendingDown";
    }
  }

  getAllStocks() {
    this.stockService.index().subscribe( data => {
    this.stocks = data;
    console.log("*** stock list retrieved");
    }, (err) => { console.log(err);
    console.log(this.stocks)});
}

onClickRow(stockSymbol: string|null) {
  console.log("here");
  if(stockSymbol){
    this.route.navigateByUrl('/singleStockView/' + stockSymbol);
  }
}



}
