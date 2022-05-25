import { Component, OnInit } from '@angular/core';
import { TimeoutInfo } from 'rxjs';
import { Movers } from 'src/app/models/movers';
import { TradesService } from 'src/app/services/trades.service';

@Component({
  selector: 'app-top-movers',
  templateUrl: './top-movers.component.html',
  styleUrls: ['./top-movers.component.css']
})
export class TopMoversComponent implements OnInit {

  movers:Movers[] | null = null;
  id:any | null = null;

  constructor(private tradeService:TradesService) { }

  ngOnInit(): void {
    this.getTopMovers();
    this.id = setInterval(() => {
      this.getTopMovers();
    }, 5000);
  }

  ngOnDestroy() {
    if (this.id) {
      clearInterval(this.id);
    }
  }

  getTopMovers(){
    this.tradeService.topMovers().subscribe(
      (data) => {
        this.movers = data;
        console.log("***new movers request recieved");
      },
      (error) => {
        console.log("Observable got and error " + error)
      }
    )
  }

  checkTrend(mover:Movers){
    if(mover.direction == "up"){
      return "trendingUp";
    }else{
      return "trendingDown";
    }
  }



}
