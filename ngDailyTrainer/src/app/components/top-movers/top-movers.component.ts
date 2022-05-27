import { StockService } from './../../services/stock.service';
import { AlphaVantageAPIService } from './../../services/alpha-vantage-api.service';
import { Component, OnInit } from '@angular/core';
import { TimeoutInfo } from 'rxjs';
import { Movers } from 'src/app/models/movers';
import { TradesService } from 'src/app/services/trades.service';
import { Stock } from 'src/app/models/stock';

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

  constructor(private tradeService:TradesService, private stockService: StockService) { }

  ngOnInit(): void {

   this.getAllStocks();
    this.getTopMovers();
    this.id = setInterval(() => {
      this.getTopMovers();
    }, 15000);
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
        console.log("Observable got and error " + error)
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
}
