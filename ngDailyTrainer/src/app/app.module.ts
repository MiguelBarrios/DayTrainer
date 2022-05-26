import { TradesService } from './services/trades.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { NgChartsModule } from 'ng2-charts';
import { PortfolioChartComponent } from './components/portfolio-chart/portfolio-chart.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { LeaderBoardComponent } from './components/leader-board/leader-board.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
<<<<<<< HEAD
import { NavbarComponent } from './components/navbar/navbar.component';

=======
import { FilterTablePipe } from './pipes/filter-table.pipe';
import { FormsModule } from '@angular/forms';
>>>>>>> 8206c09ce7b48ca6fc3a08d11cf1eda2cf645d1a

@NgModule({
  declarations: [
    AppComponent,
    TopMoversComponent,
    PortfolioChartComponent,
    LeaderBoardComponent,
<<<<<<< HEAD
    NavbarComponent,
=======
    FilterTablePipe,
>>>>>>> 8206c09ce7b48ca6fc3a08d11cf1eda2cf645d1a
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    NgbModule,
    HttpClientModule,
    NgChartsModule,
    BrowserAnimationsModule,
    MatButtonToggleModule,
    FormsModule
  ],
  providers: [TradesService,
  FilterTablePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
