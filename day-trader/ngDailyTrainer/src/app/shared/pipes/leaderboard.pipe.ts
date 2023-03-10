import { Pipe, PipeTransform } from '@angular/core';
import { LeaderboardItem } from '../../models/leaderboard-item';

@Pipe({
  name: 'leaderboard'
})
export class LeaderboardPipe implements PipeTransform {

  transform(rankings: LeaderboardItem[], n:number): LeaderboardItem[] {

    rankings.sort((a, b) => a.totalReturn > b.totalReturn ?  -1 : 1);
    return rankings;
  }

}
