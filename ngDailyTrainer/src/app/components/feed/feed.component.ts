import { CommentService } from './../../services/comment.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Comment } from 'src/app/models/comment';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  comments:Comment[] = [
    {
      id: 0,
      content:"Nice Trade!!",
      createdAt:null,
      user: null,
      trade:null
    },
    {
      id: 0,
      content:"Nice Trade!!",
      createdAt:null,
      user: null,
      trade:null
    }
  ]

  constructor(private auth:AuthService, private router:Router, private commServ:CommentService) { }

  ngOnInit(): void {
    this.isAuthorized();
    this.setComments()
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
    this.commServ.index().subscribe(
      success =>{
        console.log(success)

      },
      error =>{
        console.log(error)

      })
  }

}
