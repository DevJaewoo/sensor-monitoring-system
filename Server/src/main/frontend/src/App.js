import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Chart from "./modules/Chart";

const testdata = [
    {
        name: 'Page A', uv: 4000, pv: 2400, amt: 2400,
    },
    {
        name: 'Page B', uv: 3000, pv: 1398, amt: 2210,
    },
    {
        name: 'Page C', uv: 2000, pv: 9800, amt: 2290,
    },
    {
        name: 'Page D', uv: 2780, pv: 3908, amt: 2000,
    },
    {
        name: 'Page E', uv: 1890, pv: 4800, amt: 2181,
    },
    {
        name: 'Page F', uv: 2390, pv: 3800, amt: 2500,
    },
    {
        name: 'Page G', uv: 3490, pv: 4300, amt: 2100,
    },
];

function App() {
    const [eCO2, setECO2] = useState([{eCO2: 0, date: new Date().toISOString()}])
    const [TVOC, setTVOC] = useState([{TVOC: 0, date: new Date().toISOString()}])
    const [temp, setTemp] = useState([{Temp: 0, date: new Date().toISOString()}])
    const [accel, setAccel] = useState([{x: 0, y:0, z:0, date: new Date().toISOString()}])

    useEffect(() => {
        const refresh = () => {
            const from = new Date(Date.now() - 20000).toISOString()
            axios.get(`/api/1/sensor?size=10`)
                .then(response => {
                    const data = response.data["sensorData"];
                    setECO2(data.map(col => ({eCO2: col["eco2"], date: col["createdDate"]})).reverse())
                    setTVOC(data.map(col => ({TVOC: col["tvoc"], date: col["createdDate"]})).reverse())
                    setTemp(data.map(col => ({Temp: col["temp"], date: col["createdDate"]})).reverse())
                    setAccel(data.map(col => ({x: col["accel"]["x"], y: col["accel"]["y"], z: col["accel"]["z"], date: col["createdDate"]})).reverse())
                })
                .catch(error => console.log(error))
        }

        setInterval(refresh, 1000)
    }, []);

    return (
        <div>
            <Chart data={eCO2} name="date" stroke={{eCO2: "#FF0000"}}/>
            <Chart data={TVOC} name="date" stroke={{TVOC: "#00FF00"}}/>
            <Chart data={temp} name="date" stroke={{Temp: "#0000FF"}}/>
            <Chart data={accel} name="date" stroke={{x: "#FF0000", y: "#00FF00", z: "#0000FF"}}/>
        </div>
    );
}

export default App;