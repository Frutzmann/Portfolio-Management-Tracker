#!/usr/bin/env python
import json

import requests
import psycopg2 as pg

from types import SimpleNamespace

from scripts.Coin import Coin


def connect_to_database():
    """Database connection"""
    # Database connection needs to be defined as environment variables / secrets
    try:
        connection = pg.connect(database='pft', user='postgres', port='5432', host='localhost')
        return connection
    except Exception as e:
        raise Exception("Error connecting database: ", e)


def insert_coin_data(connection, coin_object_data):
    """Inserting the coins list"""
    cursor = connection.cursor()
    columns = ["id", "rank", "symbol",
               "name", "supply", "maxSupply",
               "marketCapUsd", "volumeUsd24Hr", "priceUsd"]
    placeholders = ','.join(['%s'] * len(columns))
    coin_tuple = Coin.to_tuple(coin_object_data)
    try:
        query = f'INSERT INTO coin VALUES ({placeholders});'
        cursor.executemany(query, coin_tuple)
        connection.commit()
    except Exception as e:
        raise Exception("Error inserting the coins list: ", e)


def update_coin_data(connection, coin_object_data):
    cursor = connection.cursor()

    for coin in coin_object_data:
        try:
            query = f"""
                UPDATE coin
                SET rank = %s,
                    supply = %s,
                    "marketCapUsd" = %s,
                    "volumeUsd24Hr" = %s,
                    "changePercent24Hr" = %s,
                    "priceUsd" = %s
                WHERE id = %s
            """

            data = (
                coin["rank"], coin["supply"], coin["marketCapUsd"],
                coin["volumeUsd24Hr"], coin["changePercent24Hr"], coin["priceUsd"],
                coin["id"]
            )
            cursor.execute(query, data)
            connection.commit()

        except Exception as e:
            raise Exception("Error updating the coins list: ", e)

    return


def get_coins_data():
    """Get CoinCap Data"""
    coins = []
    offset = 99  # Further accept command line
    limit = 200  # Further accept command line
    payload = {
        "limit": limit,
        "offset": offset
    }

    try:
        coins_json_request = requests.get("https://api.coincap.io/v2/assets", params=payload).json()['data']
        list_of_coins = convert_data(coins_json_request)
        print(list_of_coins)
        return list_of_coins
    except Exception as ex:
        raise Exception("There has been an error connecting to API: ", ex)


def convert_data(data_to_convert):
    coins = []
    for token in data_to_convert:
        coins.append(Coin.from_dict(token))
    return coins


get_coins_data()
conn = connect_to_database()
update_coin_data(conn, get_coins_data())