from scripts.Gather_coin_data import connect_to_database, get_coins_data, update_coin_data

if __name__ == '__main__':
    coins = get_coins_data()
    conn = connect_to_database()
    update_coin_data(conn, coins)
