export class Account {
    id:number;
    balance: number;
    marginEnable: boolean;
    marginAmount: number;
    deposit: number;
    constructor(id: number = 0, balance: number = 0, marginEnable: boolean = false, marginAmount: number = 0, deposit: number = 0){
        this.id = id;
        this.balance = balance;
        this.marginAmount = marginAmount;
        this.marginEnable = marginEnable;
        this.deposit = deposit;
    }
}
