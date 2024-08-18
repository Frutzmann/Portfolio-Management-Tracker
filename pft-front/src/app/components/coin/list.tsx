"use client";
import getCoins from "@/app/api/coin/get";
import {useEffect, useState} from "react";
import {Coin} from "@/app/entities/Coin";
import Image from 'next/image';
// import Icons from "../../../../public/assets/icons";


const List: React.FC = () => {
    const [coins, setCoins] = useState<Coin[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [coinsPerPage] = useState<number>(20)

    useEffect(() => {
        const fetchCoins = async () => {
            try {
                const data = await getCoins();
                setCoins(data);
            } catch (error) {
                throw error;
            }
        };
        fetchCoins();
    }, [])

    const indexOfLastCoin = currentPage * coinsPerPage;
    const indexOfFirstCoin = indexOfLastCoin - coinsPerPage;
    const currentCoins = coins.slice(indexOfFirstCoin, indexOfLastCoin);

    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    return <div className={"w-full mt-10 pb-5"}>
        <table className={"w-[70%] h-full table rounded-xl"} align={"center"} data-theme={"light"}>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Rank</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>24h %</th>
                    <th>Market Cap</th>
                    <th>Volume (24h)</th>
                </tr>
            </thead>
            <tbody>
            {currentCoins.map((coin) => (
                <tr key={coin.id}>
                    <td><Image src={"/assets/icons/" + coin.symbol.toLowerCase() + ".png"} alt={coin.symbol} width={30} height={30}/></td>
                    <td>{coin.rank}</td>
                    <td>{coin.id}</td>
                    <td>{coin.priceUsd}</td>
                    <td>{coin.marketCapUsd}</td>
                    <td>{coin.changePercent24Hr}</td>
                    <td>{coin.volumeUsd24Hr}</td>
                </tr>
            ))}
            </tbody>
        </table>
        <div className={"flex justify-center mt-4"}>
            {[...Array(Math.ceil(coins.length / coinsPerPage)).keys()].map((number : number) => (
                <button
                    key={number + 1} onClick={() => paginate(number + 1)}
                    className={`mx-1 px-3 py-1 rounded ${number + 1 === currentPage ? 'bg-blue-500 text-darkzero' : 'bg-darkone'
                }`}> {number + 1} </button>
            ))}
        </div>
    </div>
}

export default List;