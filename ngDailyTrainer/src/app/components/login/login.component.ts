import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  loginUser: User = new User();

  constructor(private authService:AuthService, private router:Router) {}

  ngOnInit(): void {
  }

  login(user:User){
    console.log("Login user");
    console.log(user);
    this.authService.login(user.username, user.password).subscribe(
      {
          next: (loggedinUser) => {
            //this.router.navigateByUrl('/todo');
          },
          error: () => {
            console.error('loginComponent.login(): login failed');
          }
      }
    )
  }


}
