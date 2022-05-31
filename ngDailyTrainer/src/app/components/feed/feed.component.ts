import { TradesService } from 'src/app/services/trades.service';
import { CommentService } from './../../services/comment.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Trade } from 'src/app/models/trade';
import { Comment } from './../../models/comment';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  trades:Trade[] = [];
  comments:any[]=[];


  constructor(private auth:AuthService, private router:Router, private commServ:CommentService,private tradeServ:TradesService) { }

  ngOnInit(): void {
    this.isAuthorized();
    this.setComments()
    this.setTrades();
  }

  isAdmin(){
    return this.auth.isAdmin();
  }

  isAuthorized() {
    if(!this.auth.checkLogin()){
    this.router.navigateByUrl('home');
    }
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

  setComments(){
    this.commServ.index(this.trades).subscribe(
      success =>{
        console.log(Object.values(success))
        this.comments = success;
        console.log(success[0])

      },
      error =>{
        console.log(error)

      })
  }

}
