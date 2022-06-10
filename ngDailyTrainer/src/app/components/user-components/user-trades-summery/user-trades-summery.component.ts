import { Router } from '@angular/router';

import { Component, OnInit } from '@angular/core';
import { StockPosition } from 'src/app/models/stock-position';
import { TradesService } from 'src/app/services/trades.service';

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
