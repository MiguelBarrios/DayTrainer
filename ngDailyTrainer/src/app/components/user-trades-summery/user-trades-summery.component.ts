import { Router } from '@angular/router';
import { StockPosition } from './../../models/stock-position';
import { TradesService } from './../../services/trades.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-trades-summery',
  templateUrl: './user-trades-summery.component.html',
  styleUrls: ['./user-trades-summery.component.css']
})
export class UserTradesSummeryComponent implements OnInit {

  positions:StockPosition[] = []

  constructor(private tradeService:TradesService, private route:Router) { }

  ngOnInit(): void {
    this.getUserStocks();
  }

  getUserStocks(){
    this.tradeService.getUserPositions().subscribe(
      (data) => {
        console.log(data);
        this.positions = data;
      },
      (error) => {
        console.log("getUserStocks() Observable got and error " + error)
      }
    )
  }

  forwardToSSV(stockSymbol: string) {
    this.route.navigateByUrl('/singleStockView/' + stockSymbol);
  }

}
