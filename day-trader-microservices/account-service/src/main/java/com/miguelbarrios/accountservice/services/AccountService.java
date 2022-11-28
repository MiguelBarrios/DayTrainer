package com.miguelbarrios.accountservice.services;

import com.miguelbarrios.accountservice.models.Account;
import com.miguelbarrios.accountservice.models.TradeTransaction;
import com.miguelbarrios.tradeservice.models.Trade;

public interface AccountService {

    Account getAccount(String username);

    TradeTransaction processTrade(Trade Trade);

}
