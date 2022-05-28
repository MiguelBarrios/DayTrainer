import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-account-home',
  templateUrl: './account-home.component.html',
  styleUrls: ['./account-home.component.css']
})
export class AccountHomeComponent implements OnInit {

  constructor(private auth:AuthService,
    private router:Router) { }

  ngOnInit(): void {
  }

  loggedIn():boolean{
    return this.auth.checkLogin();
  }

}
