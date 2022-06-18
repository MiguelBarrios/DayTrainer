
import { TradesService } from 'src/app/services/trades.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import DatalabelsPlugin from 'chartjs-plugin-datalabels';
import { ChartConfiguration, ChartData, ChartEvent, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Trade } from 'src/app/models/trade';
import { AccountService } from 'src/app/services/account.service';
import { forkJoin, Observable, throwError } from 'rxjs';
import { StockPosition } from 'src/app/models/stock-position';

@Component({
  selector: 'app-portfolio-chart',
  templateUrl: './portfolio-chart.component.html',
  styleUrls: ['./portfolio-chart.component.css']
})
export class PortfolioChartComponent implements OnInit {

   portfolio:StockPosition[]= [];
   accountBalance:any = 0;

   ngOnInit(): void {
    this.loadChart();
  }
  
  constructor(private tradesService: TradesService, private accountService:AccountService){
  }

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  // Pie
  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
        position: 'top',
      },
      datalabels: {
        formatter: (value, ctx) => {
          if (ctx.chart.data.labels) {
            return ctx.chart.data.labels[ctx.dataIndex];
          }
        },
      },
    }
  };

  loadChart(){
    forkJoin(
      {
        accountBalance : this.accountService.getUserAccountBalance(), 
        portfolio : this.tradesService.getUserPositions()
      }
    ).subscribe(({accountBalance,portfolio}) => {
        this.accountBalance = accountBalance;
        this.portfolio = portfolio;
        for(let position of portfolio){
          let sliceAmount = position.avgCostPerShare * position.numberOfShares;
          let sliceLable = [position.symbol];
          this.addSlice(sliceAmount, sliceLable);
        }
        this.addSlice(accountBalance, ["Cash"]);
        this.chart?.update();
      }      
    );
  }

  public pieChartData: ChartData<'pie', number[], string | string[]> = {
    labels: [],
    datasets: [ {
      data: []
    } ]
  };

  public pieChartType: ChartType = 'pie';
  public pieChartPlugins = [ DatalabelsPlugin ];

  // events
  public chartClicked({ event, active }: { event: ChartEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: ChartEvent, active: {}[] }): void {
    console.log(event, active);
  }

  changeLegendPosition(): void {
    if (this.pieChartOptions?.plugins?.legend) {
      this.pieChartOptions.plugins.legend.position = this.pieChartOptions.plugins.legend.position === 'left' ? 'top' : 'left';
    }
    this.chart?.render();
  }

  toggleLegend(): void {
    if (this.pieChartOptions?.plugins?.legend) {
      this.pieChartOptions.plugins.legend.display = !this.pieChartOptions.plugins.legend.display;
    }

    this.chart?.render();
  }

  addSlice(data:number, label:string[]):void {
    this.pieChartData.datasets[0].data.push(data);
    if(this.pieChartData.labels){
      this.pieChartData.labels?.push(label);
    }
  }
}

