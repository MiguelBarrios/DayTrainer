
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SingleStockViewComponent } from './components/finance/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/finance/top-movers/top-movers.component';
import { TradesComponent } from './components/finance/trades/trades.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { SettingsComponent } from './components/page-components/settings/settings.component';
import { FeedComponent } from './components/social/feed/feed.component';
import { FreindDetailViewComponent } from './components/social/freind-detail-view/freind-detail-view.component';
import { FriendsComponent } from './components/social/friends/friends.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { UsersComponent } from './components/user-components/users/users.component';



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
  {path:  'settings', component: SettingsComponent},
  {path:  'users', component: UsersComponent},
  {path:  'trades', component: TradesComponent},
  {path:  'friendView/:username', component: FreindDetailViewComponent},
  {path:  '**', component: NotfoundComponent}



];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
