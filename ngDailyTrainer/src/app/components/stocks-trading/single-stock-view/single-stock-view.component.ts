
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';
import { StockPosition } from 'src/app/models/stock-position';
import { TDAQuote } from 'src/app/models/tdaquote';
import { AlphaVantageAPIService } from 'src/app/services/alpha-vantage-api.service';
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
quote:TDAQuote | null = null;

symbol = "";
searchTerm = "";

  constructor(private router: Router, private route: ActivatedRoute,
    private stockSvc: AlphaVantageAPIService, private tradesService: TradesService,
    private tdaService:TDAserviceService, private tda:TDAService) { }

  ngOnInit(): void {
      let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.getQuote(symbol);
      }
    }

  reload(){
    if(this.searchTerm.length > 0){
      this.router.navigateByUrl('/singleStockView/' + this.searchTerm);
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate(['/singleStockView/' + this.searchTerm]);
    }

  }

  getUserPositionInfo(ticker:string){
    this.tradesService.getStockPosition(ticker).subscribe(
      (data) => {
        console.error(data);
        console.log(this.quote);
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

  getQuote(symbol: string){
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
