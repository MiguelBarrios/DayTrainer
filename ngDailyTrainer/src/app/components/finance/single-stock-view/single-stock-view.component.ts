
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';
import { StockPosition } from 'src/app/models/stock-position';
import { TDAQuote } from 'src/app/models/tdaquote';
import { TradesService } from 'src/app/services/trades.service';
import { TDAService } from 'src/app/services/tda.service';
import { StockService } from 'src/app/services/stock.service';

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

  constructor(private router: Router, private route: ActivatedRoute,
    private tradesService: TradesService,
    private tda:TDAService,
    private stockService:StockService) { }



  ngOnInit(): void {
      this.stockService.loadStocks();
      let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.getQuote(symbol);
        this.id = setInterval(() => {
          this.getQuote(symbol);
          this.flashPriceChange();
       }, 10000 / 2);
      }
    }

  ngOnDestroy() {
       if (this.id) {
         clearInterval(this.id);
       }
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
          this.totalReturn = (this.avgCostPerShare * this.numberOfShares) - (this.quote.lastPrice * this.numberOfShares);
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
    document.getElementById("currentStockPrice")?.classList.add("text-warning", "font-weight-bold");  //flash
    setTimeout(function(){
      document.getElementById("currentStockPrice")?.classList.remove("text-warning", "font-weight-bold");
    }, 700);
  }

}


