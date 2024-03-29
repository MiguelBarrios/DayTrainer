import { TradesService } from 'src/app/services/trades.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Trade } from 'src/app/models/trade';
import { User } from 'src/app/models/user';
import { UsersService } from 'src/app/services/users.service';
import { AccountService } from 'src/app/services/account.service';
import { StockPosition } from 'src/app/models/stock-position';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-account-home',
  templateUrl: './account-home.component.html',
  styleUrls: ['./account-home.component.css']
})
export class AccountHomeComponent implements OnInit {
  
  user : User = new User();
  trades :Trade[] = [];
  userPositions: StockPosition[] | null= null;

  accountBalance:any = -1;
  accountDeposits = 0;
  portfolioPurchaseValue = 0;
  portfolioCurrentValue = 0;

  updatedUser: User = new User();
  closeResult = '';


  constructor(private auth:AuthService,
    private router:Router,
    private userServ:UsersService, 
    private tradeServ:TradesService,
    private accountService:AccountService ) { }

  ngOnInit(): void {
    if(!this.auth.checkLogin()){
      this.router.navigateByUrl('home');
    }
    this.setTrades()
    this.setUser()
    this.getUserPositions();
    this.getUserAccount();
  }

  calcPortValue(){
    let purchaseValue = 0;
    let currentValue = 0;
    if(this.userPositions){
      for(let pos of this.userPositions){
        purchaseValue += pos.numberOfShares * pos.avgCostPerShare;
        currentValue += pos.numberOfShares * pos.lastPrice;
      }
    }
    this.portfolioCurrentValue = currentValue;
    this.portfolioPurchaseValue = purchaseValue;

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

  getUserAccount(){
    this.accountService.getUserAccount().subscribe(
      (account) => {
        this.accountDeposits = account.deposit;
        this.accountBalance = account.balance;
      },
      (error) => {
        console.log(error);
      }
    )
  }


  getUserPositions(){
    this.tradeServ.getUserPositions().subscribe(
      (positions) => {
          this.userPositions = positions;
          this.calcPortValue();
      },
      (error) => {
        console.log(error);
      }
    )
  }

  getUser(): User{
    return this.user;
  }

}
