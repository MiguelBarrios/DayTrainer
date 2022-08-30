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
  constructor(private userService:UsersService) { }

  ngOnInit(): void {
    this.getNumUserTrades();
    this.loadPage(this.numberOfPages, this.pageSize);
  }

  updateNumberOfPages(event: any){
    this.pageSize = event.value;
    this.loadPage(this.pageNumber, this.pageSize);
  }

  getNumUserTrades(){
    this.userService.getNumUserTrades().subscribe(
      (data) => {
        this.numberOfTrades = data;
        this.numberOfPages = Math.ceil(this.numberOfTrades / this.pageSize);
      },
      (error) => {
        console.log(error);
      }
    )
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
