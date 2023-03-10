import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowseStocksComponent } from './components/browse-stocks/browse-stocks.component';
import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TradesComponent } from './components/trades/trades.component';

const routes: Routes = [
  { path: 'singleStockView/', component: SingleStockViewComponent},
  { path: 'singleStockView/:symbol', component: SingleStockViewComponent},
  { path: 'browse', component: BrowseStocksComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TradeRoutingModule { }
