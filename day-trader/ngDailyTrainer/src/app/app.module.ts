import { UserTradesPipe } from './shared/pipes/user-trades.pipe';
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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatGridListModule} from '@angular/material/grid-list';
import { CurrencyPipe, DatePipe } from '@angular/common';
import {MatTabsModule} from '@angular/material/tabs';
import {MatRadioModule} from '@angular/material/radio';
import { NgApexchartsModule } from 'ng-apexcharts'; 

import { PortfolioChartComponent } from './components/graphs/portfolio-chart/portfolio-chart.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { FooterComponent } from './components/page-components/footer/footer.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { NavbarComponent } from './components/page-components/navbar/navbar.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { UserUpdateFormComponent } from './components/user-components/user-update-form/user-update-form.component';
import { UsersComponent } from './components/user-components/users/users.component';

import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MarketService } from './services/market.service';
import { SettingsComponent } from './settings/settings.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { TradeTableComponent } from './components/widgets/trade-table/trade-table.component';
import { PositionTableComponent } from './components/widgets/position-table/position-table.component';
import { SharedModule } from './shared/shared.module';


@NgModule({
  declarations: [
    AppComponent,
    PortfolioChartComponent,
    LeaderBoardComponent,
    HomepageComponent,
    AccountHomeComponent,
    NavbarComponent,
    FooterComponent,
    NotfoundComponent,
    UserUpdateFormComponent,
    UsersComponent,
    UserUpdateFormComponent,
    SettingsComponent,
    TradeTableComponent,
    PositionTableComponent
  ],
  imports: [
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    MatProgressSpinnerModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    NgChartsModule,
    BrowserModule, 
    BrowserAnimationsModule,
    MatButtonToggleModule,
    FormsModule,
    MatRadioModule,
    MatCardModule,
    MatGridListModule,
    NgApexchartsModule,
    MatTabsModule,
    MatSnackBarModule, 
    SharedModule,
  ],
  providers: [TradesService, DatePipe, CurrencyPipe, MarketService],
  bootstrap: [AppComponent],
})
export class AppModule {}
