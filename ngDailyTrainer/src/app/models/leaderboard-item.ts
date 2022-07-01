export class LeaderboardItem {
  userid:string;
  firstname:string;
  lastname:string;
  profilePicture:string;
  username:string;
  totalNumberOfShares:number;
  portfolioValue:number;
  totalReturn:number;
  constructor(
    userid:string,
    firstname:string,
    lastname:string,
    profilePicture:string,
    username:string,
    totalNumberOfShares:number,
    portfolioValue:number,
    totalReturn:number,
  ){
    this.userid = userid;
    this.firstname = firstname;
    this.lastname = lastname;
    this.profilePicture = profilePicture;
    this.username = username;
    this.totalNumberOfShares = totalNumberOfShares;
    this.portfolioValue = portfolioValue;
    this.totalReturn = totalReturn;
  }
}
