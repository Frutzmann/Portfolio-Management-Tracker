import axios from "axios";
import * as path from "node:path";
import * as fs from "fs";

const getSymbols = async () => {
    const url = 'http://localhost:8080/api/coin/all/symb';
    try {
        return axios.get(url).then((res) => res.data);
    } catch (error) {
        throw error;
    }
}
const publicDir = path.join(__dirname, '..', '..', '..' ,"public/assets/icons");


const fetchImages = async () => {
    const symbols = await getSymbols();
    console.log(symbols);
    for (let symbol of symbols){
        var url = `https://assets.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png`;

        if(symbol == 'BORG')
            url = "https://assets.coincap.io/assets/icons/chsb@2x.png";

        try {
            const response = await axios.get(url, {responseType: 'arraybuffer'});

            const imagePath = path.join(publicDir, `${symbol.toLowerCase()}.png`);

            fs.writeFileSync(imagePath, response.data);
        } catch (error) {
            throw error;
        }
    }
}

fetchImages();