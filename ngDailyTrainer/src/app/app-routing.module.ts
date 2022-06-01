import { UsersComponent } from './components/users/users.component';
import { MarketComponent } from './components/market/market.component';
import { TradeComponent } from './trade/trade.component';
import { SettingsComponent } from './components/settings/settings.component';
import { FriendsComponent } from './components/friends/friends.component';
import { FeedComponent } from './components/feed/feed.component';
import { AccountHomeComponent } from './components/account-home/account-home.component';
import { LeaderBoardComponent } from './components/leader-board/leader-board.component';
import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { TradesComponent } from './components/trades/trades.component';

const routes: Routes = [
  { path: 'home', component: HomepageComponent},
  { path: '', component: HomepageComponent},
  { path: 'leaderBoard', component: LeaderBoardComponent},
  { path: 'topMovers', component: TopMoversComponent},
  { path: 'singleStockView/', component: SingleStockViewComponent},
  { path: 'singleStockView/:symbol', component: SingleStockViewComponent},
  { path: 'dashboard', component: AccountHomeComponent},
  { path: 'accounthome', component: AccountHomeComponent},
  {path:  'feed', component: FeedComponent},
  {path:  'friends', component: FriendsComponent},
  {path:  'market', component: MarketComponent},
  {path:  'settings', component: SettingsComponent},
  {path:  'users', component: UsersComponent},
  {path:  'trades', component: TradesComponent},
  {path:  '**', component: NotfoundComponent}



];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
