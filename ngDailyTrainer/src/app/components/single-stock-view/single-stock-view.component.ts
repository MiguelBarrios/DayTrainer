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
    this.show('AAPL');

    if (!this.selected && this.route.snapshot.paramMap.get('symbol')) {
       let symbol = this.route.snapshot.paramMap.get('symbol');
      if (symbol) {
        this.show(symbol);
      }
    }

  }

  show (symbol: string) {
    this.stockSvc.search(symbol).subscribe(
      data => {this.selected = data;
        //this console log shows a full item, but html "selected" is empty
        console.log(this.selected)

        ;},
      err => {console.log(err)})

  }

}
