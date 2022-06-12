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
import { DatePipe } from '@angular/common';
import {MatRadioModule} from '@angular/material/radio';
import { BuySellPipe } from './pipes/buy-sell.pipe';
import { NgApexchartsModule } from 'ng-apexcharts';
import { MoversPipe } from './pipes/movers.pipe';
import { LeaderboardPipe } from './pipes/leaderboard.pipe';
import { CandleGraphComponent } from './components/graphs/candle-graph/candle-graph.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { LoggedInNavComponent } from './components/page-components/logged-in-nav/logged-in-nav.component';
import { NavbarComponent } from './components/page-components/navbar/navbar.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { SettingsComponent } from './components/page-components/settings/settings.component';
import { FeedComponent } from './components/social/feed/feed.component';
import { FreindDetailViewComponent } from './components/social/freind-detail-view/freind-detail-view.component';
import { FriendsComponent } from './components/social/friends/friends.component';
import { SingleStockViewComponent } from './components/stocks-trading/single-stock-view/single-stock-view.component';
import { TradeComponent } from './components/stocks-trading/trade/trade.component';
import { TradesComponent } from './components/stocks-trading/trades/trades.component';
import { UserUpdateFormComponent } from './components/user-components/user-update-form/user-update-form.component';
import { UsersComponent } from './components/user-components/users/users.component';
import { PortfolioChartComponent } from './components/graphs/portfolio-chart/portfolio-chart.component';
import { FooterComponent } from './components/page-components/footer/footer.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { TopMoversComponent } from './components/stocks-trading/top-movers/top-movers.component';
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
    NotfoundComponent,
    UserUpdateFormComponent,
    FeedComponent,
    SettingsComponent,
    UsersComponent,
    FriendsComponent,
    TradesComponent,
    UserTradesPipe,
    BuySellPipe,
    UserUpdateFormComponent,
    CandleGraphComponent,
    MoversPipe,
    LoggedInNavComponent,
    LeaderboardPipe,
    FreindDetailViewComponent,
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
    MatTabsModule,

  ],
  providers: [TradesService, FilterTablePipe, DatePipe, MoversPipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
