import { Component, OnInit } from '@angular/core';
import { StockPosition } from 'src/app/models/stock-position';
import { Trade } from 'src/app/models/trade';
import { TradesService } from 'src/app/services/trades.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-position-table',
  templateUrl: './position-table.component.html',
  styleUrls: ['./position-table.component.css']
})
export class PositionTableComponent implements OnInit {

  math = Math;
  numberOfPositions:number = 0;
  pageNumber:number = 0;
  pageSize:number = 5;
  numberOfPages:number = 0;
  positionsAll:StockPosition[] = [];
  userPositions:StockPosition[] = [];
  currentPage:number = 0;

  pagesArray:any = [];
  constructor(private userService:UsersService, private tradeService: TradesService) { }

  ngOnInit(): void {
    this.loadData();
  }

  updateNumberOfPages(event: any){
    this.pageSize = event.value;
    this.numberOfPages = Math.ceil(this.numberOfPositions / this.pageSize);
    this.pagesArray = new Array(this.numberOfPages);
    this.loadPage(this.currentPage);
  }

  getNumUserTrades(){
    this.userService.getNumUserTrades().subscribe(
      (data) => {
        this.numberOfPositions = data;
        this.numberOfPages = Math.ceil(this.numberOfPositions / this.pageSize);
        this.pagesArray = new Array(this.numberOfPages);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  loadSelectedPage(pageNum:number){
    this.currentPage = pageNum;
    this.loadPage(this.currentPage);
  }

  loadNextPage(){
    if(this.currentPage + 1 < this.numberOfPages){
      this.currentPage = this.currentPage + 1;
      this.loadPage(this.currentPage);
    }
  }

  loadPage(pageNumber: number){
    let startIndex = this.currentPage * this.pageSize;
    let endIndex = Math.min(startIndex + this.pageSize, this.numberOfPositions);
    this.userPositions = [];
    for(let i = startIndex; i < endIndex; ++i){
      this.userPositions.push(this.positionsAll[i]);
    }
  }

  loadPreviousPage(){
    if(this.currentPage - 1 >= 0){
      this.currentPage = this.currentPage - 1;
      this.loadPage(this.currentPage);
    }
  }

  loadData(){
    this.tradeService.getUserPositions().subscribe(
      (data) => {
        this.numberOfPositions = data.length;
        this.numberOfPages = Math.ceil(this.numberOfPositions / this.pageSize);
        this.pagesArray = new Array(this.numberOfPages);
        this.positionsAll = data;
        this.userPositions = [];
        for(let i = 0; i < this.positionsAll.length; ++i){
          if(i >= this.pageSize){
            break;
          }
          this.userPositions.push(this.positionsAll[i]);
        }
      }, 
      (error) => {
        console.log(error);
      }
    )
  }

}
