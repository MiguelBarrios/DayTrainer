import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']


})
export class HomepageComponent implements OnInit {

  loginUser: User = new User();
  closeResult = '';

  constructor(private auth:AuthService,private router: Router, private modalService: NgbModal) { }

  ngOnInit(): void {
  }

  loggedIn():boolean{
    return this.auth.checkLogin();
  }

  login(user:User){
    console.log("Login user");
    console.log(user);
    this.auth.login(user.username, user.password).subscribe(
      {
          next: (loggedinUser) => {
            this.router.navigateByUrl('/accounthome');
            this.loginUser = new User();
          },
          error: () => {
            console.error('loginComponent.login(): login failed');
          }
      }
    )
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

}
