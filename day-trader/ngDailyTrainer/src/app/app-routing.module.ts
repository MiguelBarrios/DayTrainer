
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowseStocksComponent } from './trade/components/browse-stocks/browse-stocks.component';
import { AccountHomeComponent } from './components/page-components/account-home/account-home.component';
import { HomepageComponent } from './components/page-components/homepage/homepage.component';
import { NotfoundComponent } from './components/page-components/notfound/notfound.component';
import { LeaderBoardComponent } from './components/social/leader-board/leader-board.component';
import { UsersComponent } from './components/user-components/users/users.component';

const routes: Routes = [
  { path: 'home', component: HomepageComponent},
  { path: '', component: HomepageComponent},
  { path: 'leaderBoard', component: LeaderBoardComponent},
  { path: 'dashboard', component: AccountHomeComponent},
  { path: 'accounthome', component: AccountHomeComponent},
  {path:  'users', component: UsersComponent},
  {path: 'trade', loadChildren:() => import('./trade/trade.module')
    .then(mod => mod.TradeModule)
  },
  {path:  '**', component: NotfoundComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
