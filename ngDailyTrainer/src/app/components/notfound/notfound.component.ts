import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { timeout } from 'rxjs';


@Component({
  selector: 'app-notfound',
  templateUrl: './notfound.component.html',
  styleUrls: ['./notfound.component.css']
})
export class NotfoundComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.countdown();
  }
countdown(){
  let time:string[]=["9","8","7","6","5","4","3","2","1"]
  setTimeout(() => {
    this.router.navigateByUrl('home')
  }, 10000);

 setTimeout(() => {
   let count = document.getElementById('count')
   if(count?.textContent != null){
    let counter = parseInt(count.textContent)
  counter -= 1
  console.log(counter);

   }

 }, 1000);
}

}
