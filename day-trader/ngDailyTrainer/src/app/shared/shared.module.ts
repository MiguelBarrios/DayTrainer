import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BuySellPipe } from './pipes/buy-sell.pipe';
import { FilterTablePipe } from './pipes/filter-table.pipe';
import { LeaderboardPipe } from './pipes/leaderboard.pipe';
import { MoversPipe } from './pipes/movers.pipe';
import { StockFilterPipe } from './pipes/stock-filter.pipe';
import { UserTradesPipe } from './pipes/user-trades.pipe';



@NgModule({
  declarations: [BuySellPipe, FilterTablePipe, LeaderboardPipe, MoversPipe, StockFilterPipe, UserTradesPipe],
  exports: [BuySellPipe, FilterTablePipe, LeaderboardPipe, MoversPipe, StockFilterPipe, UserTradesPipe],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
