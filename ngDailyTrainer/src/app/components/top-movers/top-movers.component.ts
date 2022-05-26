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

  constructor(private tradeService:TradesService, private stockService: AlphaVantageAPIService) { }

  ngOnInit(): void {

  this.getAllStocks(this.searchValue);
    this.getTopMovers();
    this.id = setInterval(() => {
      this.getTopMovers();
    }, 5000);
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

  getAllStocks(searchValue: string) {
    this.stockService.search(searchValue).subscribe(res => {this.stocks = res; console.log(res)});
    // this.stockService.retrieveAll().subscribe(
    //   (data) =>{ this.stocks = data;
    //   console.log("***new stocks request recieved")},

    //   (err) =>{console.log(err)
    //   console.log(this.stocks) }
    // )
  }


  }
