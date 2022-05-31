import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  comments:Comment[] = []

  constructor(private auth:AuthService, private router:Router) { }

  ngOnInit(): void {
    this.isAuthorized();
  }

  isAdmin(){
    return this.auth.isAdmin();
  }

  isAuthorized() {
    if(!this.auth.checkLogin()){
    this.router.navigateByUrl('home');
    }
  }

  setComments(){
//comment service to get all comments
  }

}
