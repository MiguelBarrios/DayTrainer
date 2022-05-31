import { Pipe, PipeTransform } from '@angular/core';
import { LeaderboardItem } from '../models/leaderboard-item';

@Pipe({
  name: 'leaderboard'
})
export class LeaderboardPipe implements PipeTransform {

  transform(rankings: LeaderboardItem[], n:number): LeaderboardItem[] {

    rankings.sort((a, b) => a.portfolioValue > b.portfolioValue ?  1 : 0);
    return rankings;
  }

}
