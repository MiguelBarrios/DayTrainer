export class LeaderboardItem {
  userid:string;
  firstname:string;
  lastname:string;
  profilePicture:string;
  username:string;
  totalNumberOfShares:number;
  portfolioValue:number;
  constructor(
    userid:string,
    firstname:string,
    lastname:string,
    profilePicture:string,
    username:string,
    totalNumberOfShares:number,
    portfolioValue:number
  ){
    this.userid = userid;
    this.firstname = firstname;
    this.lastname = lastname;
    this.profilePicture = profilePicture;
    this.username = username;
    this.totalNumberOfShares = totalNumberOfShares;
    this.portfolioValue = portfolioValue;
  }
}
