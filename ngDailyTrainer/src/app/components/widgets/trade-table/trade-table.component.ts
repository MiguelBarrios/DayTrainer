import { Component, OnInit } from '@angular/core';
import { Trade } from 'src/app/models/trade';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-trade-table',
  templateUrl: './trade-table.component.html',
  styleUrls: ['./trade-table.component.css']
})
export class TradeTableComponent implements OnInit {

  numberOfTrades:number = 0;
  pageNumber:number = 0;
  pageSize:number = 5;
  numberOfPages:number = 0;
  userTrades:Trade[] = [];
  currentPage:number = 0;

  pagesArray:any = [];
  constructor(private userService:UsersService) { }

  ngOnInit(): void {
    this.getNumUserTrades();
    this.loadPage(this.numberOfPages, this.pageSize);
  }

  updateNumberOfPages(event: any){
    this.pageSize = event.value;
    this.numberOfPages = Math.ceil(this.numberOfTrades / this.pageSize);
    this.pagesArray = new Array(this.numberOfPages);
    this.loadPage(this.pageNumber, this.pageSize);
  }

  getNumUserTrades(){
    this.userService.getNumUserTrades().subscribe(
      (data) => {
        this.numberOfTrades = data;
        this.numberOfPages = Math.ceil(this.numberOfTrades / this.pageSize);
        this.pagesArray = new Array(this.numberOfPages);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  loadSelectedPage(pageNum:number){
    this.currentPage = pageNum;
    this.loadPage(pageNum, this.pageSize);
  }

  loadNextPage(){
    if(this.currentPage + 1 < this.numberOfPages){
      this.currentPage = this.currentPage + 1;
      this.loadPage(this.currentPage, this.pageSize);
    }
  }

  loadPreviousPage(){
    if(this.currentPage - 1 >= 0){
      this.currentPage = this.currentPage - 1;
      this.loadPage(this.currentPage, this.pageSize);
    }
  }

  loadPage(pageNum:number, pageSize: number){
    this.userService.getUserTrades(pageNum, pageSize).subscribe(
      (data) => {
        this.userTrades = data;
      },
      (error) => {
        console.log(error);
      }
    )
  }

}
