import { AccountHomeComponent } from './components/account-home/account-home.component';
import { LeaderBoardComponent } from './components/leader-board/leader-board.component';
import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';

const routes: Routes = [
  { path: 'home', component: HomepageComponent},
  { path: 'leaderBoard', component: LeaderBoardComponent},
  { path: 'topMovers', component: TopMoversComponent},
  { path: 'singleStockView/:symbol', component: SingleStockViewComponent},
  { path: 'accounthome', component: AccountHomeComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
