package com.frutz.pft.services.coin;

import com.frutz.pft.entity.Coin;

import java.util.List;

public interface CoinService {

    List<Coin> getAllCoins();
    Coin getCoinById(String id);
    List<String> getCoinsSymbol();

}
