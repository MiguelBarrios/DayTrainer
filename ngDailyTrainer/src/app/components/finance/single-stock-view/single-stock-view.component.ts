
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';
import { StockPosition } from 'src/app/models/stock-position';
import { TDAQuote } from 'src/app/models/tdaquote';
import { TDAserviceService } from 'src/app/services/tdaservice.service';
import { TradesService } from 'src/app/services/trades.service';
import { TDAService } from 'src/app/services/tda.service';

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

  constructor(private router: Router, private route: ActivatedRoute,
    private tradesService: TradesService,
    private tdaService:TDAserviceService, private tda:TDAService) { }



  ngOnInit(): void {
      let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.getQuote(symbol);
       this.id = setInterval(() => {
        this.getQuote(symbol);
       }, 10000);
      }
    }

  ngOnDestroy() {
       if (this.id) {
         clearInterval(this.id);
       }
  }

  reload(){
    if(this.searchTerm.length > 0){
      this.searchTerm = this.searchTerm.toUpperCase();
      this.router.navigateByUrl('/singleStockView/' + this.searchTerm);
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate(['/singleStockView/' + this.searchTerm]);
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
            this.flashPriceChange();

        },
        (error) => {
          console.error("Error getting quote");
        }
      )
    }

  }

  flashPriceChange(){
    document.getElementById("currentStockPrice")?.classList.add("flash");
    setTimeout(function(){
      document.getElementById("currentStockPrice")?.classList.remove("flash");
    }, 400);
  }
}


