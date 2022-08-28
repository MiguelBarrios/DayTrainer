import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AccountHomeComponent } from '../components/page-components/account-home/account-home.component';
import { User } from '../models/user';
import { AuthService } from '../services/auth.service';
import { UsersService } from '../services/users.service';



@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  user:User = new User();
  closeResult = '';

  newUsername = "";
  oldPasswordInput = "";
  newPassword = "";
  nullUserNameFlag = false;
  usernameTakenFlag = false;
  incorrectPasswordFlag = false;

  constructor(private modalService: NgbModal,
    private userServ:UsersService,
    private authServ:AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.setUser();
  }

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  updateUserInfo(user:User){
    console.log(user);
    this.userServ.update(user).subscribe(
      (data) => {
        this.user = user;
        location.reload();
      }
    )
  }

  updateLoginInfo(){
    let cred = this.authServ.getCredentials();

    // check for username field is accounted for
    if(this.newUsername.length == 0){
      this.nullUserNameFlag = true;
    }
    else{
      this.nullUserNameFlag = false;
    }

    // Check if username is not taken




    // Check for correct password
    if(cred){
      let tokens = atob(cred).split(":");
      let oldUsername = tokens[0];
      let oldPassword = tokens[1];
      if(this.oldPasswordInput != oldPassword){
        console.error("Incorrect password");
        this.incorrectPasswordFlag = true;
      }
      else{
        console.log("correct passwored");
        this.incorrectPasswordFlag = false;
        this.oldPasswordInput = "";
        this.modalService.dismissAll();
      }
    }


    if(!this.nullUserNameFlag && !this.usernameTakenFlag){
      this.user.username = this.newUsername;
      this.userServ.update2(this.user).subscribe(
        (updatedUser) => {
          this.authServ.login(this.newUsername, this.newPassword).subscribe(
            {
              next: (loggedinUser) => {
                location.reload();
              },
              error: () => {
                console.error('loginFailed');
              }
            }
          )
        }
      )
    }
  }

  setUser(){
    this.userServ.getUserByUsername().subscribe(
      (success) =>{
        this.user = success
        this.newUsername = this.user.username;
      }
    )
  }

}
