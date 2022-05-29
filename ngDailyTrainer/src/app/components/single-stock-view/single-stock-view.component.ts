import { TDAQuote } from './../../models/tdaquote';
import { TDAserviceService } from './../../services/tdaservice.service';
import { TradesService } from 'src/app/services/trades.service';
import { AlphaVantageAPIService } from './../../services/alpha-vantage-api.service';
import { StockService } from './../../services/stock.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';
import { StockPosition } from 'src/app/models/stock-position';

@Component({
  selector: 'app-single-stock-view',
  templateUrl: './single-stock-view.component.html',
  styleUrls: ['./single-stock-view.component.css']
})
export class SingleStockViewComponent implements OnInit {
stats: TDAQuote  | null = null;
selected: Stock = new Stock();
userPosition: StockPosition | null = null;
symbol = "";

  constructor(private router: Router, private route: ActivatedRoute,
    private stockSvc: AlphaVantageAPIService, private tradesService: TradesService,
    private tdaService:TDAserviceService) { }

  ngOnInit(): void {
      let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.show(symbol);
        this.getStockStats(symbol);
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

        console.log(this.selected);},
      err => {console.log(err)})

  }

  getUserPositionInfo(ticker:string){
    this.tradesService.getStockPosition(ticker).subscribe(
      (data) => {
        this.userPosition = data;
      },
      (error) => {
        console.log("getUserPositionInfo() Observable got and error " + error)
      }
    )
  }

  getStockStats(symbol:string){
      this.tdaService.getStockStats(symbol).subscribe(
        (data) => {
          let stats = Object.values(data)[0];
          let netChange = stats["netChange"];
          let volatility = stats["volatility"];
          let WkHigh52 = stats["52WkHigh"];
          let WkLow52 = stats["52WkLow"];
          let peRatio = stats["peRatio"];
          let divAmount = stats["divAmount"];
          let divYield = stats["divYield"];
          let divDate = stats["divDate"];
          this.stats = new TDAQuote(netChange, volatility, WkHigh52, WkLow52, peRatio, divAmount, divYield, divDate);
        },
        (error) => {
          console.log("getStockStats() Observable got and error " + error)
        }
      )
  }



}

  // netChange:number;
  // volatility:number;
  // 52WkHigh:number;
  // 52WkLow:number;
  // peRatio:number;
  // divAmount:number;
  // divYield:number;
  // divDate:string;



  // // Manufacturer and model are both of type string,
  // // so we can pluck them both into a typed string array
  // let makeAndModel: string[] = pluck(taxi, ["manufacturer", "model"]);

