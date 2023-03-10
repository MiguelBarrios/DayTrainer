
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';
import { StockPosition } from 'src/app/models/stock-position';
import { TDAQuote } from 'src/app/models/tdaquote';
import { TradesService } from 'src/app/services/trades.service';
import { TDAService } from 'src/app/services/tda.service';
import { StockService } from 'src/app/services/stock.service';
import { NONE_TYPE } from '@angular/compiler';
import { JsonPipe } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MarketService } from 'src/app/services/market.service';
import { MatGridTileHeaderCssMatStyler } from '@angular/material/grid-list';

@Component({
  selector: 'app-single-stock-view',
  templateUrl: './single-stock-view.component.html',
  styleUrls: ['./single-stock-view.component.css']
})
export class SingleStockViewComponent implements OnInit {
userPosition: StockPosition | null = null;
numberOfShares = 0;
marketValue = 0;
totalReturn = 0;
avgCostPerShare = 0;
quote:TDAQuote = new TDAQuote("",1,1,1,1,1,1,1,"",1,"",1,1,1,1,1);

symbol = "";
searchTerm = "";
id:any | null = null;
unsuportedStockFlag = false;

marketOpen: Date  = new Date(2000, 1);
marketClose: Date = new Date(2000, 2);


  constructor(private router: Router, private route: ActivatedRoute,
    private tradesService: TradesService, private tda:TDAService,
    private stockService:StockService, private marketService: MarketService,
    private _snackBar:MatSnackBar) { }

  ngOnInit(): void {
      this.refreshMarketHours();
      this.stockService.getStocks
      let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.getQuote(symbol);

        this.id = setInterval(() => {
          if( this.marketIsOpen() || this.quote.symbol == ""){
            this.getQuote(symbol);
            this.flashPriceChange();
          }          
       }, 15000);
      }
    }

  ngOnDestroy() {
       if (this.id) {
         clearInterval(this.id);
       }
  }

  marketIsOpen(): boolean{
    let today = new Date();
    let day = today.getDay();
    let open = this.marketOpen?.getDay();
    
    if(day != open){
      this.refreshMarketHours();
    }

    let now = new Date();

    return now.getTime() >= this.marketOpen.getTime() &&
           now.getTime() <= this.marketClose.getTime();

  }

  parseDate(dateStr:string): Date {
    let arr = dateStr.split("T");
    let tokens = arr[0].split('-');

    let year = parseInt(tokens[0])
    let month = parseInt(tokens[1]) - 1;
    let day = parseInt(tokens[2]);

    tokens = arr[1].split(':');
    let hour = parseInt(tokens[0]);
    let minute = parseInt(tokens[1]);
    
    return new Date(year, month, day, hour, minute);
  }

  refreshMarketHours(){

    var today = new Date();
    // Check to see if it is the weekened
    if(today.getDay() == 6 || today.getDay() == 0){
      this.marketClose = new Date(2020);
      this.marketOpen = new Date(2020);
      return;
    }

    this.marketService.getMarketHours().subscribe(
      (data) => {
        let jsonString = JSON.stringify(data);
        let obj = JSON.parse(jsonString);
        let regularMarketHours = obj['sessionHours']['regularMarket'][0];
        let marketOpen = regularMarketHours['start'];
        let marketClose = regularMarketHours['end'];

       this.marketOpen = this.parseDate(marketOpen.substring(0,19));
       this.marketClose = this.parseDate(marketClose.substring(0,19));
      },
      (error) => {
        console.log(error);
      }
    )
  }


  reload(){
    this.searchTerm = this.searchTerm.toUpperCase();
    let stock = this.stockService.contains(this.searchTerm);

    if(this.stockService.contains(this.searchTerm)){
      this.unsuportedStockFlag = false;
      this.router.navigateByUrl('/singleStockView/' + this.searchTerm);
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate(['/singleStockView/' + this.searchTerm]);
    }
    else{
      this.unsuportedStockFlag = true; 
    }
  }

  getUserPositionInfo(ticker:string){
    this.tradesService.getStockPosition(ticker).subscribe(
      (data) => {
        if(this.quote){
          this.userPosition = data;
          this.numberOfShares = data.numberOfShares;
          this.marketValue = data.numberOfShares * this.quote?.lastPrice;
          this.avgCostPerShare = data.avgCostPerShare;
          this.totalReturn = (this.quote.lastPrice * this.numberOfShares) - (this.avgCostPerShare * this.numberOfShares) ;
        }

      },
      (error) => {
        console.log("getUserPositionInfo() Observable got and error " + error)
      }
    )
  }

  getQuote(symbol: string | null){
    if(symbol){
      this.tda.getQuote(symbol).subscribe(
        (quote) => {
            this.quote = quote;
            let keys = Object.keys(quote);
            let data = Object.values(quote);
            let low = keys.indexOf("52WkLow");
            let high = keys.indexOf("52WkHigh");
            this.quote.WkHigh52 = data[high];
            this.quote.WkLow52 = data[low];
            this.getUserPositionInfo(symbol);
        },
        (error) => {
          console.error("Error getting quote");
        }
      )
    }

  }

  flashPriceChange(){
    document.getElementById("currentStockPrice")?.classList.add("text-warning", "font-weight-bold");  
    setTimeout(function(){
      document.getElementById("currentStockPrice")?.classList.remove("text-warning", "font-weight-bold");
    }, 700);
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

}


