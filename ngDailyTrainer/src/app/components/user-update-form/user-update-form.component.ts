import { User } from './../../models/user';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-update-form',
  templateUrl: './user-update-form.component.html',
  styleUrls: ['./user-update-form.component.css']
})
export class UserUpdateFormComponent implements OnInit {



  user:User = new User;


  constructor() { }

  ngOnInit(): void {
  }
updateUser(){
  console.log(this.user);
}

}
