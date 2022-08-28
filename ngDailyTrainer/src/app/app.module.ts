import { UserTradesPipe } from './pipes/user-trades.pipe';
import { TradesService } from './services/trades.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { NgChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule} from '@angular/material/card';
import { FilterTablePipe } from './pipes/filter-table.pipe';
import { FormsModule } from '@angular/forms';
import {MatGridListModule} from '@angular/material/grid-list';
import { CurrencyPipe, DatePipe } from '@angular/common';
import {MatTabsModule} from '@angular/material/tabs';
import {MatRadioModule} from '@angular/material/radio';
import { NgApexchartsModule } from 'ng-apexcharts';
import { TopMoversComponent } from './components/finance/top-movers/top-movers.component';
import { TradeComponent } from './components/finance/trade/trade.component';
import { TradesComponent } from './components/finance/trades/trades.component';
import { CandleGraphComponent } from './components/graphs/candle-graph/candle-graph.component';
import { PortfolioChartComponent } from './components/graphs/portfolio-chart/portfolio-chart.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { FooterComponent } from './components/page-components/footer/footer.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { NavbarComponent } from './components/page-components/navbar/navbar.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { UserUpdateFormComponent } from './components/user-components/user-update-form/user-update-form.component';
import { UsersComponent } from './components/user-components/users/users.component';
import { BuySellPipe } from './pipes/buy-sell.pipe';
import { LeaderboardPipe } from './pipes/leaderboard.pipe';
import { MoversPipe } from './pipes/movers.pipe';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MarketService } from './services/market.service';
import { SingleStockViewComponent } from './components/finance/single-stock-view/single-stock-view.component';
import { SettingsComponent } from './settings/settings.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';


@NgModule({
  declarations: [
    AppComponent,
    TopMoversComponent,
    PortfolioChartComponent,
    LeaderBoardComponent,
    FilterTablePipe,
    HomepageComponent,
    AccountHomeComponent,
    NavbarComponent,
    FooterComponent,
    TradeComponent,
    NotfoundComponent,
    UserUpdateFormComponent,
    UsersComponent,
    TradesComponent,
    UserTradesPipe,
    BuySellPipe,
    UserUpdateFormComponent,
    CandleGraphComponent,
    MoversPipe,
    LeaderboardPipe,
    SingleStockViewComponent,
    SettingsComponent
  ],
  imports: [
    MatProgressSpinnerModule,
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
    MatTabsModule,
    MatSnackBarModule
  ],
  providers: [TradesService, FilterTablePipe, DatePipe, MoversPipe, CurrencyPipe, MarketService],
  bootstrap: [AppComponent],
})
export class AppModule {}
