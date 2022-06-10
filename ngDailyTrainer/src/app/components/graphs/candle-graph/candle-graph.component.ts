import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';

import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexYAxis,
  ApexXAxis,
  ApexTitleSubtitle
} from "ng-apexcharts";
import { TDAserviceService } from 'src/app/services/tdaservice.service';
import { TDAService } from 'src/app/services/tda.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-candle-graph',
  templateUrl: './candle-graph.component.html',
  styleUrls: ['./candle-graph.component.css']
})
export class CandleGraphComponent implements OnInit {

  @ViewChild("chart") chart?: ChartComponent;
  public chartOptions: Partial<ChartOptions> | any;
  candles:any[] = [];

  symbol = "";
  isLoaded = false;

  ngOnInit(): void {
  }

  loadData(){
    this.chartOptions = {
      series: [
        {
          name: "candle",
          data: [...this.candles]
        }
      ],
      chart: {
        type: "candlestick",
        height: 350
      },
      title: {
        text: "",
        align: "left"
      },
      xaxis: {
        type: "datetime"
      },
      yaxis: {
        tooltip: {
          enabled: true
        }
      }
    };
  }

  constructor(private router:Router, private route: ActivatedRoute,
        private tdaService:TDAserviceService, private tda:TDAService) {
    let symbol = this.route.snapshot.paramMap.get('symbol');
    if (symbol) {
      this.getCandles(symbol);
    }


  }

  // getCandles(symbol:string){
  //   this.tda.getCandles10Day10Minute(symbol).subscribe(
  //     (data) => {
  //         let info = Object.values(data)[0];
  //          for(let i = 0; i < info.length; ++i){
  //            let fields = Object.values(info[i]);
  //            let cur  = {
  //               x: new Date(info[i]["datetime"]),
  //               y: [info[i]["open"], info[i]["high"], info[i]["low"], info[i]["close"]]
  //             }
  //             this.candles.push(cur);
  //          }
  //          console.log("Candles: " + this.candles.length);
  //          this.loadData();
  //          this.isLoaded = true;
  //     },
  //     (error) => {
  //       console.error("Error in observable");
  //     }
  //   )
  // }

  getCandles(symbol:string){
    this.tda.getCandles10Day10Minute(symbol).subscribe(
    //this.tdaService.getCandleBasic(symbol).subscribe(
      (data) => {
          let info = Object.values(data)[0];
           for(let i = 0; i < info.length; ++i){
             let fields = Object.values(info[i]);
             let cur  = {
                x: new Date(info[i]["datetime"]),
                y: [info[i]["open"], info[i]["high"], info[i]["low"], info[i]["close"]]
              }
              this.candles.push(cur);
           }
           console.log("Candles: " + this.candles.length);
           this.loadData();
           this.isLoaded = true;

      },
      (error) => {
        console.log("getUserPositionInfo() Observable got and error " + error)
      }
    )

  }

  public generateDayWiseTimeSeries(baseval: number, count: number, yrange: { max: number; min: number; }) {
    var i = 0;
    var series = [];
    while (i < count) {
      var y =
        Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

      series.push([baseval, y]);
      baseval += 86400000;
      i++;
    }
    return series;
  }

}
