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
  constructor(private userSvc: UserService) {}

  ngOnInit(): void {
    this.getLeaders();
  }

  getLeaders() {
    this.userSvc.index().subscribe(
      (data) => {
        this.leaders = data;
        console.log('***new leaders request recieved');
        console.log(this.leaders)
      },
      (error) => {
        console.log('Observable got an error ' + error);
      }
    );
    this.leaders.forEach(user => {

    });
  }
}
