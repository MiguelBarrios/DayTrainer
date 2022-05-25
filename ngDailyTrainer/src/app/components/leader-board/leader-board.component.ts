import { Contestant } from './../../models/contestant';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css']
})
export class LeaderBoardComponent implements OnInit {



  topWinners:Contestant[]  = [];
  topLosers:Contestant[] = [];

  constructor() { }

  ngOnInit(): void {
    this.getTopWinners();
  }

  getTopWinners(){
    // tmp
    this.topWinners = [
      new Contestant("Winner A", 3700, 312.0),
      new Contestant("Winner B", 2300, 121.2),
      new Contestant("Winner C", 230, 89.5),
      new Contestant("Winner D", 12, 21.3),
    ]

    this.topLosers = [
      new Contestant("Loser A", -1230, -67.3),
      new Contestant("Loser B",434.56, -89.1),
      new Contestant("Loser C",-1232, -92.3),
      new Contestant("Loser D",-54.34, -99.91),
    ]
  }


}
