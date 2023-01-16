import { Router } from '@angular/router';
import { Component, OnInit} from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  loginError = false;

  loggedin:boolean = false;

  loginUser: User = new User();

  newUser: User = new User();

  closeResult = '';

  constructor(private router: Router, private modalService: NgbModal, private authService:AuthService,private auth:AuthService) { }

  ngOnInit(): void {
    this.loggedin = this.loggedIn();
  }

  click() {
    this.router.navigateByUrl('home');
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

  login(user:User){
    this.authService.login(user.username, user.password).subscribe(
      {
          next: (loggedinUser) => {
            this.router.navigateByUrl('/accounthome');
            this.loginUser = new User();
            this.loggedin = true;
            this.modalService.dismissAll();
            this.loginError = false;
          },
          error: () => {
            console.error('loginFailed');
            this.loginError = true;
          }
      }
    )
  }

  loggedIn():boolean{
    this.loggedin = this.auth.checkLogin();
    return this.loggedin;
  }

  register(user: User): void {
    console.log('Registering user:');
    console.log(user);
    this.authService.register(user).subscribe({
      next: (registeredUser) => {
        this.authService.login(user.username, user.password).subscribe({
          next: (loggedInUser) => {
            this.router.navigateByUrl('/accounthome');
            this.newUser = new User();
            this.loggedin = true;
          },
          error: (problem) => {
            console.error('RegisterComponent.register(): Error logging in user:');
            console.error(problem);
          }
        });
      },
      error: (fail) => {
        console.error('RegisterComponent.register(): Error registering account');
        console.error(fail);
      }
    });
  }

  logout(){
    this.auth.logout();
    this.loggedin = false;
}
}
