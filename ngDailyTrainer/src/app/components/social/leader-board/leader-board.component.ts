import { getTestBed } from '@angular/core/testing';
import { first, last } from 'rxjs';
import { TradesService } from 'src/app/services/trades.service';

import { User } from 'src/app/models/user';
import { Component, OnInit } from '@angular/core';
import { LeaderboardItem } from 'src/app/models/leaderboard-item';
import { TDAserviceService } from 'src/app/services/tdaservice.service';
import { UserService } from 'src/app/services/user.service';



@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css'],
})
export class LeaderBoardComponent implements OnInit {
  leaders: User[] = [];
  //userTrades: Position[] = [];
  users:User[] = [];
  activeStocks:string[] = [];
  quotes = new Map();
  rankings:LeaderboardItem[] = [];




  constructor(private userSvc: UserService, private trade: TradesService, private tdaService:TDAserviceService) {}

  ngOnInit(): void {
    // this.getLeaders();
    this.getLeaderBoard();

    // this.getUserTrades(this.leaders);
  }

  getLeaderBoard(){
      this.userSvc.getLeaderBoard().subscribe(
        (data) => {
            var arr = Object.values(data);
            this.users = Object.values(arr[0]);
            this.activeStocks = Object.values(arr[1]);
            this.tdaService.getQuotes(this.activeStocks).subscribe(
                (quotes) => {
                  this.quotes = new Map();
                  var quotesArr = Object.values(quotes);
                  for(let quote of quotesArr){
                    this.quotes.set(quote.symbol, quote.lastPrice);
                  }

                  this.generateLeaderBoard();
                },
                (error) => {
                  console.log("Error getting quotes");
                }
            )

        },
        (error) => {
            console.log("Error in getLeaderboarMethod")
        }
      )
  }

  generateLeaderBoard(){
      this.rankings = [];
      for(let user of this.users){
        console.log(user);
        if(user.positions){
          let userTotalProfit = 0;
          let totalNumberOfShares = 0;
          for(let pos of user.positions){
            var data = Object.values(pos);
            let symbol = data[0];
            let numShares = data[1];
            let pricePerShare = data[2];
            let posValue = numShares * pricePerShare;
            let posCurValue = numShares * this.quotes.get(symbol);
            let posFinValue = posCurValue - posValue;
            userTotalProfit += posFinValue;
            totalNumberOfShares += numShares;
          }
          let arr = Object.values(user);
          let username = arr[0].username;
          let userid = arr[0].id;
          let firstName = arr[0].firstName;
          let lastname = arr[0].lastName;
          let pic = arr[0].profilePicture;

          let ranking = new LeaderboardItem(userid,firstName, lastname, pic,username,totalNumberOfShares, userTotalProfit);
          this.rankings.push(ranking);
        }
      }
      console.log(this.rankings);
  }
}
