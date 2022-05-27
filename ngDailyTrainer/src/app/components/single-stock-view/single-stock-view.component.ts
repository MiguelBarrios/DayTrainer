import { AlphaVantageAPIService } from './../../services/alpha-vantage-api.service';
import { StockService } from './../../services/stock.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/models/stock';

@Component({
  selector: 'app-single-stock-view',
  templateUrl: './single-stock-view.component.html',
  styleUrls: ['./single-stock-view.component.css']
})
export class SingleStockViewComponent implements OnInit {
selected: Stock = new Stock();
symbol = "";

  constructor(private router: Router, private route: ActivatedRoute, private stockSvc: AlphaVantageAPIService) { }

  ngOnInit(): void {
   let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.show(symbol);
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

}
