import { TradesService } from 'src/app/services/trades.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Trade } from 'src/app/models/trade';
import { User } from 'src/app/models/user';
import { UsersService } from 'src/app/services/users.service';
import { AccountService } from 'src/app/services/account.service';
import { StockPosition } from 'src/app/models/stock-position';


@Component({
  selector: 'app-account-home',
  templateUrl: './account-home.component.html',
  styleUrls: ['./account-home.component.css']
})
export class AccountHomeComponent implements OnInit {
  
  user : User = new User();
  trades :Trade[] = [];
  accountBalance:any = -1;
  accountValue :number | null = null;
  userPositions: StockPosition[] | null= null;

  constructor(private auth:AuthService,
    private router:Router,
    private userServ:UsersService, 
    private tradeServ:TradesService,
    private accountService:AccountService
  ) { }

  ngOnInit(): void {
    if(!this.auth.checkLogin()){
      this.router.navigateByUrl('home');
    }
    this.setTrades()
    this.setUser()
    this.getUserBalance()
    this.getUserPositions();
  }

  setUser(){
    this.userServ.getUserByUsername().subscribe(
      (user) =>{
        this.user = user
      }, 
      (error) => {
        console.error(error);
      }
    )
  }

  setTrades(){
    this.tradeServ.getUserTrades().subscribe(
      (trades) =>{
        this.trades = trades
      },
      (error) =>{
        console.log(error);
      }
    )
  }

  getUserBalance(){
    this.accountService.getUserAccountBalance().subscribe(
      (balance) =>{
       this.accountBalance = balance;
      },
      (error) =>{
        console.log(error)
      }
    )
  }

  getUserPositions(){
    this.tradeServ.getUserPositions().subscribe(
      (positions) => {
          this.userPositions = positions;
      },
      (error) => {
        console.log(error);
      }
    )
  }


}
