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

  }

  getTopWinners(){

  }

}
