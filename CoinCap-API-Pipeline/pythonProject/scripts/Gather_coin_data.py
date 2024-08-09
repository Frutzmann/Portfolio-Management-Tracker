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
    """Updating the coins list"""
    cursor = connection.cursor()
    columns = ["id", "rank", "symbol",
               "name", "supply", "maxSupply",
               "marketCapUsd", "volumeUsd24Hr", "priceUsd"]
    sql_cols = ','.join(columns)
    placeholders = ','.join(['%s'] * len(columns))
    cTuple = Coin.to_tuple(coin_object_data)
    try:
        query = f'INSERT INTO coin VALUES ({placeholders});'
        cursor.executemany(query, cTuple)
        connection.commit()
    except Exception as e:
        raise Exception("Error inserting the coins list: ", e)


def update_coin_data(connection, coin_object_data):
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
insert_coin_data(conn, get_coins_data())
