import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { map, Observable, startWith } from 'rxjs';
import { Stock } from 'src/app/models/stock';
import { StockService } from 'src/app/services/stock.service';

@Component({
  selector: 'app-browse-stocks',
  templateUrl: './browse-stocks.component.html',
  styleUrls: ['./browse-stocks.component.css']
})
export class BrowseStocksComponent implements OnInit {

  
  searchQuery = "";
  selectedSector = "";
  a:Stock = new Stock('ABC');
  b:Stock = new Stock('ABCD');
  c:Stock = new Stock('ADE');
  d:Stock = new Stock('ADEF');

  constructor(private stockSvc:StockService, private route: Router) { 
  }

  stocks:Stock[] = [];
  myControl = new FormControl('');
  options: string[] = ['ABC', 'ABCD', 'ADE', "ADEF"];
  filteredOptions: string[] = [];
  selected:Stock | null = null;


  ngOnInit() {
    this.stockSvc.getStocks().subscribe(
      (data) => {
        this.stocks = data;
      },
      (error) => {
        console.log(error);
      }
    )

  }

  selectOption(stock:Stock){
    this.searchQuery = "";
    this.selected = stock; 
  }

  loadSelected(){
    if(this.selected){
      this.route.navigateByUrl('/singleStockView/' + this.selected.symbol);
    }
  }

  redirectBySelectedCard(symbol:string){
    this.route.navigateByUrl('/singleStockView/' + symbol);
  }

  browseBySector(sector:string){
    this.selectedSector = sector;

    let items = document.getElementsByClassName("sector");
    for(let i = 0; i < items.length; ++i){
      let element = items[i];
      let name = element.textContent
      if(name == this.selectedSector){
        element.classList.remove("btn-warning");
        element.classList.add("btn-light");
      }
      else{
        element.classList.remove("btn-light");
        element.classList.add("btn-warning");
      }
    }
  }



}
