import { UserTradesPipe } from './pipes/user-trades.pipe';
import { FooterComponent } from './components/footer/footer.component';
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
import { MatCardModule} from '@angular/material/card';

import { NavbarComponent } from './components/navbar/navbar.component';

import { FilterTablePipe } from './pipes/filter-table.pipe';
import { FormsModule } from '@angular/forms';

import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AccountHomeComponent } from './components/account-home/account-home.component';
import {MatGridListModule} from '@angular/material/grid-list';



import { TradeComponent } from './trade/trade.component';
import { DatePipe } from '@angular/common';
import {MatRadioModule} from '@angular/material/radio';
import { LogoutComponent } from './components/logout/logout.component';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { BuySellPipe } from './pipes/buy-sell.pipe';
import { UserUpdateFormComponent } from './components/user-update-form/user-update-form.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { CandleGraphComponent } from './components/candle-graph/candle-graph.component';
import { UserTradesSummeryComponent } from './components/user-trades-summery/user-trades-summery.component';
import {MatTabsModule} from '@angular/material/tabs';



@NgModule({
  declarations: [
    AppComponent,
    TopMoversComponent,
    PortfolioChartComponent,
    LeaderBoardComponent,
    FilterTablePipe,
    SingleStockViewComponent,
    HomepageComponent,
    AccountHomeComponent,
    NavbarComponent,
    FooterComponent,
    TradeComponent,
    LogoutComponent,
    NotfoundComponent,
    UserTradesPipe,
    BuySellPipe,
    UserUpdateFormComponent,
    CandleGraphComponent,
    UserTradesSummeryComponent
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
    MatRadioModule,
    MatCardModule,
    MatGridListModule,
    NgApexchartsModule,
    MatTabsModule
  ],
  providers: [TradesService, FilterTablePipe, DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
