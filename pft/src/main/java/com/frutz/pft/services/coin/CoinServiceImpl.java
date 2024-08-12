package com.frutz.pft.services.coin;

import com.frutz.pft.entity.Coin;
import com.frutz.pft.repository.CoinRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;
    @Override
    public List<Coin> getAllCoins() {
        return coinRepository.findAll().stream()
                .sorted(Comparator.comparing(Coin::getRank))
                .collect(Collectors.toList());
    }

    @Override
    public Coin getCoinById(String id) {
        Coin coin = coinRepository.findById(id).orElse(null);

        if (coin == null)
                throw new EntityNotFoundException("Coin with id " + id + " not found");

        return coin;
    }
}
