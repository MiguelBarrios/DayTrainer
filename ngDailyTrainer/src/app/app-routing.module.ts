import { LeaderBoardComponent } from './components/leader-board/leader-board.component';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // { path: '/home', component: HomeComponent}
  // { path: 'leaderBoard', LeaderBoardComponent}
  { path: 'topMovers', component: TopMoversComponent},
  { path: 'singleStockView', component: SingleStockViewComponent},
  { path: 'create-user', component: CreateUserComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
