import { Position } from './../../models/position';
import { Trade } from './../../models/trade';
import { TradesService } from 'src/app/services/trades.service';
import { UserService } from './../../services/user.service';
import { User } from 'src/app/models/user';
import { Contestant } from './../../models/contestant';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css'],
})
export class LeaderBoardComponent implements OnInit {
  leaders: User[] = [];
  userTrades: Position[] = [];
  constructor(private userSvc: UserService, private trade: TradesService) {}

  ngOnInit(): void {
    this.getLeaders();

    // this.getUserTrades(this.leaders);
  }

  getLeaders() {
    this.userSvc.getLeaders().subscribe(
      (data) => {
        this.leaders = data;
        console.log('***new leaders request recieved');
        console.log(this.leaders)

              for (let index = 0; index < this.leaders.length; index++) {

                this.userSvc.getUserTrades(this.leaders[index].username).subscribe(
                  (data) => {
                    this.leaders[index].positions = data;

                    console.log('***new leaders request recieved');
                    console.log(this.userTrades)
                  });

              }

      },
      (error) => {
        console.log('Observable got an error ' + error);
      }
    );
  }

getUserTrades(username: string| null) {
this.userSvc.getUserTrades(username).subscribe(
  (data) => {
    this.userTrades = data;
    console.log('***new leaders request recieved');
    console.log(this.userTrades)
  },
  (error) => {
    console.log('Observable got an error ' + error);
  });

}



}
