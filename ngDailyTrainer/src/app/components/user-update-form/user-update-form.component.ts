import { TradesService } from 'src/app/services/trades.service';
import { User } from './../../models/user';
import { Component, Input, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-user-update-form',
  templateUrl: './user-update-form.component.html',
  styleUrls: ['./user-update-form.component.css']
})
export class UserUpdateFormComponent implements OnInit {

  @Input()
  user:User = new User;

  constructor(private auth:AuthService,
    private router:Router, private userServ:UsersService) { }

  ngOnInit(): void {
    this.setUser()
  }

updateUser(user:User){
  this.userServ.update(user).subscribe(
    data => {
      //this needs to be played with
      localStorage.setItem('role',data.role)
      localStorage.setItem('username',data.username);
      this.user = data;
    },
    err => console.error(err)
  );

}

setUser(){
  this.userServ.getUserByUsername().subscribe(
    success =>{
      this.user = success
     // this.accountValue = success.account
     //make account model and set accont to be an account or null in user
    }
  )
}

}
