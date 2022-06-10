
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
stats: TDAQuote  | null = null;
selected: Stock = new Stock();
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
        this.show(symbol);
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


  show (symbol: string) {
    this.stockSvc.search(symbol).subscribe(
      data => {
    let stock = new Stock();
     stock.symbol = data['Global Quote']['01. symbol'];
     stock.open = data['Global Quote']['02. open'];
     stock.high = data['Global Quote']['03. high'];
     stock.low = data['Global Quote']['04. low'];
     stock.price = data['Global Quote']['05. price'];
     stock.change = data['Global Quote']['09. change'];
     stock.volume = data['Global Quote']['06. volume'];
     stock.previousClose = data['Global Quote']['08. previous close'];
      this.selected=stock;
      this.getUserPositionInfo(symbol);


        //console.log(this.selected);
      },
      err => {console.log(err)})

  }

  getUserPositionInfo(ticker:string){
    this.tradesService.getStockPosition(ticker).subscribe(
      (data) => {
        this.userPosition = data;
        this.numberOfShares = data.numberOfShares;
        this.marketValue = data.numberOfShares * this.selected.price;
        this.avgCostPerShare = data.avgCostPerShare;
        this.totalReturn = (this.avgCostPerShare * this.numberOfShares) - (this.selected.price * this.numberOfShares);
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
      },
      (error) => {
        console.error("Error getting quote");
      }
    )
  }

}
