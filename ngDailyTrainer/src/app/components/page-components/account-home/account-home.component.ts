import { TradesService } from 'src/app/services/trades.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Trade } from 'src/app/models/trade';
import { User } from 'src/app/models/user';
import { UsersService } from 'src/app/services/users.service';
import { PortfolioChartComponent } from '../../graphs/portfolio-chart/portfolio-chart.component';
import { AccountService } from 'src/app/services/account.service';



@Component({
  selector: 'app-account-home',
  templateUrl: './account-home.component.html',
  styleUrls: ['./account-home.component.css']
})
export class AccountHomeComponent implements OnInit {

  constructor(private auth:AuthService,
    private router:Router, private userServ:UsersService, 
    private tradeServ:TradesService,
    private accountService:AccountService) { }

    accountBalance:any = -1;
    accountValue :number | null = null;
    user : User = new User();
    trades :Trade[] = [];
    users: User[]  =  [];

  ngOnInit(): void {
    this.isAuthorized()
    this.setTrades()
    this.setUser()
    this.setUsers()
    this.getUserBalance()
  }
  isAuthorized() {
    if(!this.auth.checkLogin()){
    this.router.navigateByUrl('home');
    }
  }

  loggedIn():boolean{
    return this.auth.checkLogin();
  }

  isAdmin(){
    return this.auth.isAdmin();
  }

  setUser(){
    this.userServ.getUserByUsername().subscribe(
      success =>{
        this.user = success
       // this.accountValue = success.account
       //make account model and set accont to be an account or null in user
      }
    )
  }

  setUsers(){
    this.userServ.getAllUsers().subscribe(
      success =>{
        this.users = success
       //make account model and set accont to be an account or null in user
      }
    )
  }

  setTrades(){
    this.tradeServ.getUserTrades().subscribe(
      success =>{
        this.trades = success
      },
      err=>{
        console.log(err)
      }
    )
  }
  getUserBalance(){
    this.accountService.getUserAccountBalance().subscribe(
      data =>{
       this.accountBalance = data;
      },
      err=>{
        console.log(err)
      }
    )
  }
}
