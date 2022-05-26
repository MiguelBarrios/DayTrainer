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
import { MatButtonToggleModule } from '@angular/material/button-toggle';

import { NavbarComponent } from './components/navbar/navbar.component';

import { FilterTablePipe } from './pipes/filter-table.pipe';
import { FormsModule } from '@angular/forms';

import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { HomepageComponent } from './homepage/homepage.component';
import { CreateUserComponent } from './components/create-user/create-user.component';


@NgModule({
  declarations: [
    AppComponent,
    TopMoversComponent,
    PortfolioChartComponent,
    LeaderBoardComponent,
    NavbarComponent,
    FilterTablePipe,
    SingleStockViewComponent,

    HomepageComponent,

    CreateUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    NgChartsModule,
    BrowserAnimationsModule,
    MatButtonToggleModule,
    FormsModule,
  ],
  providers: [TradesService, FilterTablePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
