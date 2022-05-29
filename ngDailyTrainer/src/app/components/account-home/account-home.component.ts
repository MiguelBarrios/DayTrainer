import { TradesService } from 'src/app/services/trades.service';
import { UsersService } from './../../services/users.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Trade } from 'src/app/models/trade';
import { User } from 'src/app/models/user';


@Component({
  selector: 'app-account-home',
  templateUrl: './account-home.component.html',
  styleUrls: ['./account-home.component.css']
})
export class AccountHomeComponent implements OnInit {

  constructor(private auth:AuthService,
    private router:Router, private userServ:UsersService, private tradeServ:TradesService) { }

    accountValue :number | null = null;
    user : User = new User();
    trades :Trade[] = [];

  ngOnInit(): void {
    this.setTrades()
    this.setUser()
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
}
