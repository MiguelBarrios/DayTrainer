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
  invalidNewPasswordFlag = false;

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
    this.nullUserNameFlag = (this.newUsername.length == 0);
  

    // Check for correct password
    if(cred){
      let tokens = atob(cred).split(":");
      let oldUsername = tokens[0];
      let oldPassword = tokens[1];
      this.incorrectPasswordFlag = (this.oldPasswordInput != oldPassword)
    }


    if(!this.nullUserNameFlag && !this.usernameTakenFlag && !this.incorrectPasswordFlag && !this.invalidNewPasswordFlag){
      this.user.username = this.newUsername;
      this.user.password = this.newPassword;
      this.userServ.update2(this.user).subscribe(
        (updatedUser) => {
          this.authServ.login(this.newUsername, this.newPassword).subscribe(
            {
              next: (loggedinUser) => {
                this.nullUserNameFlag = false;
                this.usernameTakenFlag = false;
                this.incorrectPasswordFlag = false;
                this.invalidNewPasswordFlag = false;
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
    else{
      console.error("Error: info not updated")
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
