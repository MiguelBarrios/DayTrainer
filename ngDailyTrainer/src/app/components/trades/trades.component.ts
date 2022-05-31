import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-trades',
  templateUrl: './trades.component.html',
  styleUrls: ['./trades.component.css']
})
export class TradesComponent implements OnInit {

  constructor(private auth:AuthService,private router:Router) { }

  ngOnInit(): void {
    this.isAuthorized()
  }

  isAdmin(){
    return this.auth.isAdmin();
  }

  isAuthorized() {
    if(!this.auth.checkLogin()){
    this.router.navigateByUrl('home');
    }
  }

}
