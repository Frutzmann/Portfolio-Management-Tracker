export interface Coin {
    id: string;
    rank: number;
    symbol: string;
    name: string;
    supply: number;
    marketCapUsd: number;
    volumeUsd24Hr: number;
    changePercent24Hr: number;
    priceUsd: number;
}