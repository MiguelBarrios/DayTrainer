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
     userBalance: string = '';
     username:string | null = '';

  ngOnInit(): void {
      this.username = this.route.snapshot.paramMap.get('username');
      this.show(this.username);
      console.log(this.username)
    this.isAuthorized()
    this.setTrades(this.username)
    // this.setUsers()
    this.getUserBalance()
  }

  show(username:string|null){
    if(username){
      this.userServ.getUserWithUsername(username).subscribe(
        success =>{
          this.user = success;
          console.log(this.user)
        },
        error=>{
          console.log(error);
        }
      )
    }
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

  setTrades(username:string|null){
    if(username){
      this.tradeServ.getFriendsTrade(username).subscribe(
        success =>{
          this.trades = success
        },
        err=>{
          console.log(err)
        }
      )
    }
  }

  getUserBalance(){
    this.userServ.accountBalance().subscribe(
      success =>{
      this.userBalance = success + '';
      },
      err=>{
        console.log(err)
      }
    )
  }


}

