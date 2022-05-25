export class Contestant {
  username:string | null;
  totalWinnings: number | null;
  accountValue: number | null;
  profilePic: string | null;
  numberOfTrades: number | null;
  constructor(username: string | null = "",
    totalWinnings: number | null = 0,
    accountValue: number | null = 0,
    profilePic: string | null = "",
    numberOfTrades: number | null = 0){
      this.username = username;
      this.totalWinnings = totalWinnings;
      this.accountValue = accountValue;
      this.profilePic = profilePic;
      this.numberOfTrades = numberOfTrades;
    }
}
