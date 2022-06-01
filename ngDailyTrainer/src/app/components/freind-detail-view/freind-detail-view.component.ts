import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Trade } from 'src/app/models/trade';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { TradesService } from 'src/app/services/trades.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-freind-detail-view',
  templateUrl: './freind-detail-view.component.html',
  styleUrls: ['./freind-detail-view.component.css']
})
export class FreindDetailViewComponent implements OnInit {

  constructor(private auth:AuthService,
    private router:Router, private userServ:UsersService, private tradeServ:TradesService, private route:ActivatedRoute) { }

     accountValue :number | null = null;
     user : User = new User();
    trades :Trade[] = [];
    //  users: User[]  =  [];
     userBalance: Number = 0;
     username:string = '';

  ngOnInit(): void {
    if (!this.user && this.route.snapshot.paramMap.get('username')) {
      let id = this.route.snapshot.paramMap.get('username');
      if (id) {
        this.show(this.username);
      }
    }
    this.isAuthorized()
    this.setTrades()
    this.setUser()
    // this.setUsers()
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

  // setUsers(){
  //   this.userServ.getAllUsers().subscribe(
  //     success =>{
  //       console.log(success)
  //       //this.users = success
  //      //make account model and set accont to be an account or null in user
  //     }
  //   )
  // }

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
    this.userServ.newAccountBalance().subscribe(
      success =>{
       //this.userBalance = success
      },
      err=>{
        console.log(err)
      }
    )
  }

  show(username:string){
    this.userServ.getUserByUsername(username).subscribe(
      success =>{
        this.user = success;

      },
      error=>{
        console.log(error);
      }
    )
  }
}

