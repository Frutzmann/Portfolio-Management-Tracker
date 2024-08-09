import json


def to_tuple(coin_obj_list):
    return [(coin.id, coin.rank, coin.symbol,
            coin.name, coin.supply, coin.maxSupply,
            coin.marketCapUsd, coin.volumeUsd24Hr, coin.priceUsd)
            for coin in coin_obj_list]


class Coin:
    def __init__(self, id, rank, symbol, name, supply, max_supply,
                 market_cap, volume, price_usd):
        self.id = id
        self.rank = rank
        self.symbol = symbol
        self.name = name
        self.supply = supply
        self.maxSupply = max_supply
        self.marketCapUsd = market_cap
        self.volumeUsd24Hr = volume
        self.priceUsd = price_usd

    def __getitem__(self, item):
        return getattr(self, item)

    @classmethod
    def from_dict(cls, coins_dict):
        return cls(coins_dict['id'], coins_dict['rank'], coins_dict['symbol'],
                   coins_dict['name'], coins_dict['supply'], coins_dict['maxSupply'],
                   coins_dict['marketCapUsd'], coins_dict['volumeUsd24Hr'], coins_dict['priceUsd'])

    @classmethod
    def from_json(cls, json_str: str):
        return cls.from_dict(json.loads(json_str))

    @classmethod
    def to_tuple(cls, coin_obj_list):
        return [(coin.id, coin.rank, coin.symbol,
                coin.name, coin.supply, coin.maxSupply,
                coin.marketCapUsd, coin.volumeUsd24Hr, coin.priceUsd)
                for coin in coin_obj_list]
