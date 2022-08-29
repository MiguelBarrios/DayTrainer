
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowseStocksComponent } from './components/finance/browse-stocks/browse-stocks.component';
import { SingleStockViewComponent } from './components/finance/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/finance/top-movers/top-movers.component';
import { TradesComponent } from './components/finance/trades/trades.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { UsersComponent } from './components/user-components/users/users.component';



const routes: Routes = [
  { path: 'home', component: HomepageComponent},
  { path: '', component: HomepageComponent},
  { path: 'leaderBoard', component: LeaderBoardComponent},
  { path: 'browse', component: BrowseStocksComponent},
  { path: 'singleStockView/', component: SingleStockViewComponent},
  { path: 'singleStockView/:symbol', component: SingleStockViewComponent},
  { path: 'dashboard', component: AccountHomeComponent},
  { path: 'accounthome', component: AccountHomeComponent},
  {path:  'users', component: UsersComponent},
  {path:  'trades', component: TradesComponent},
  {path:  '**', component: NotfoundComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
