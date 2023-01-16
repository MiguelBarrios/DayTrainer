import { UsersService } from 'src/app/services/users.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users:User[] = [];
  selectedUser : User | null = null;

  constructor(private auth:AuthService,private userServ:UsersService,private router:Router) { }

  ngOnInit(): void {
    this.isAuthorized()
    this.setUsers();
  }

  isAdmin(){
    return this.auth.isAdmin();
  }

  isAuthorized() {
    if(!this.auth.checkLogin()){
    this.router.navigateByUrl('home');
    }
  }

  setUsers(){
    this.userServ.getAllUsers().subscribe(
      success =>{
        console.log(success)
        this.users = success
       //make account model and set accont to be an account or null in user
      }
    )
  }

  setSelectedUser(user:User){
    console.log(user)
    this.selectedUser = user;
  }

  deleteUser(user:User){
    this.userServ.delete(user.id).subscribe(
      success =>{
        this.setUsers();
        console.log(success)
      }
    )


  }


}
