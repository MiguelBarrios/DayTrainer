
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UsersService } from 'src/app/services/users.service';
import { User } from 'src/app/models/user';



@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {

  constructor(private auth:AuthService,private router:Router, private userSvc: UsersService) { }


  user : User = new User();
  userId: number = 0;
  friends: User[]  =  [] ;

ngOnInit(): void {
  this.isAuthorized()

  this.setUser()

}
isAuthorized() {
  if(!this.auth.checkLogin()){
  this.router.navigateByUrl('home');
  }
}

loggedIn():boolean{
  return this.auth.checkLogin();
}

isAdmin(){
  return this.auth.isAdmin();
}

setUser(){
  this.userSvc.getUserByUsername().subscribe(
    success =>{
      this.user = success
      this.userId = success.id;
      console.log(this.userId)
      this.getUsersFriends(this.userId);
     // this.accountValue = success.account
     //make account model and set accont to be an account or null in user
    }
  )
}

  getUsersFriends(userId: number) {
    this.userSvc.getUserFollowingList(userId).subscribe(
      data => {
        this.friends = data;
        console.log(this.friends);
      }
    ) }

    loadFriendDetailView(username:string){
      this.router.navigateByUrl('friendView/'+username);
    }
}
