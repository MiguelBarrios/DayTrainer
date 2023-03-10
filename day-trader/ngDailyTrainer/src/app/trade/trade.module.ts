import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TradeRoutingModule } from './trade-routing.module';
import { BrowseStocksComponent } from './components/browse-stocks/browse-stocks.component';
import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { TradeComponent } from './components/trade/trade.component';
import { TradesComponent } from './components/trades/trades.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule } from '@angular/common/http';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgApexchartsModule } from 'ng-apexcharts';
import { NgChartsModule } from 'ng2-charts';
import { AppRoutingModule } from '../app-routing.module';
import { CandleGraphComponent } from './components/candle-graph/candle-graph.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    BrowseStocksComponent,
    SingleStockViewComponent, 
    TopMoversComponent,
    TradeComponent, 
    TradesComponent, 
    CandleGraphComponent
  ],
  imports: [
    CommonModule,
    TradeRoutingModule, 
    FormsModule, 
    MatFormFieldModule, 
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    MatProgressSpinnerModule,
    NgbModule,
    NgChartsModule,
    MatButtonToggleModule,
    FormsModule,
    MatRadioModule,
    MatCardModule,
    MatGridListModule,
    NgApexchartsModule,
    MatTabsModule,
    MatSnackBarModule, 
    SharedModule
  ]
})
export class TradeModule { }
