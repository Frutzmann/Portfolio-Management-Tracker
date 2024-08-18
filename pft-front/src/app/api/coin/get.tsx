"use client";

import axios from 'axios';
import {Coin} from "@/app/entities/Coin";

const axiosInstance = axios.create({
    baseURL: "https://localhost:8080",
})

const getCoins = async(): Promise<Coin[]> => {
    const response = await axios.get('http://localhost:8080/api/coin/all');
    return response.data;
}

export default getCoins;

