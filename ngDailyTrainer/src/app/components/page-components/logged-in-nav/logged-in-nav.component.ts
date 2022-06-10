import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-logged-in-nav',
  templateUrl: './logged-in-nav.component.html',
  styleUrls: ['./logged-in-nav.component.css']
})
export class LoggedInNavComponent implements OnInit {

  constructor(private auth:AuthService,  private router:Router) { }

  ngOnInit(): void {
    this.isAuthorized();
    this.loggedIn();
    this.isAdmin();

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

}
