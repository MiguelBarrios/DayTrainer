import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { AuthService } from 'src/app/services/auth.service';
import { TradesService } from 'src/app/services/trades.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private auth:AuthService,
    private router:Router,
    private userServ:UsersService, 
    private tradeServ:TradesService,
    private accountService:AccountService) { }

  ngOnInit(): void {
    if(!this.auth.checkLogin()){
      this.router.navigateByUrl('home');
    }
  }

}
