import { SingleStockViewComponent } from './components/single-stock-view/single-stock-view.component';
import { TopMoversComponent } from './components/top-movers/top-movers.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'topMovers', component: TopMoversComponent},
  { path: 'singleStockView', component: SingleStockViewComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
