import { Component, OnInit } from '@angular/core';
import { Trade } from 'src/app/models/trade';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-trade-table',
  templateUrl: './trade-table.component.html',
  styleUrls: ['./trade-table.component.css']
})
export class TradeTableComponent implements OnInit {

  pageNumber:string = "0";
  pageSize:string = "5";
  userTrades:Trade[] = [];
  constructor(private userService:UsersService) { }

  ngOnInit(): void {
    this.loadFirstPage();
  }

  loadFirstPage(){
    this.userService.getUserTrades(this.pageNumber, this.pageSize).subscribe(
      (data) => {
        this.userTrades = data;
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    )
  }

}
