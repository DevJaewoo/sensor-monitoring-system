import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Chart from "./modules/Chart";
import './App.css'

const timeFormat = (date) => date.substring(11, 19);

function App() {
    const initialDate = timeFormat(new Date().toISOString());
    const [eCO2, setECO2] = useState([{eCO2: 0, date: initialDate}])
    const [TVOC, setTVOC] = useState([{TVOC: 0, date: initialDate}])
    const [temp, setTemp] = useState([{Temp: 0, date: initialDate}])
    const [accel, setAccel] = useState([{x: 0, y:0, z:0, date: initialDate}])

    useEffect(() => {
        const refresh = () => {
            axios.get(`/api/1/sensor?size=10`)
                .then(response => {
                    const data = response.data["sensorData"];
                    if(data === undefined) return;

                    setECO2(data.map(col => ({eCO2: col["eco2"], date: timeFormat(col["createdDate"])})).reverse())
                    setTVOC(data.map(col => ({TVOC: col["tvoc"], date: timeFormat(col["createdDate"])})).reverse())
                    setTemp(data.map(col => ({Temp: col["temp"], date: timeFormat(col["createdDate"])})).reverse())
                    setAccel(data.map(col => ({x: col["accel"]["x"], y: col["accel"]["y"], z: col["accel"]["z"], date: timeFormat(col["createdDate"])})).reverse())
                })
                .catch(error => console.log(error))
        }

        setInterval(refresh, 2000)
    }, []);

    return (
        <div className="app">
            <h1 className="app__title">Sensor Monitoring Panel</h1>
            <div className="app__chart">
                <Chart title="Equivalent CO2 (ppm)" data={eCO2} name="date" domain={[0, 2000]} stroke={{eCO2: "#7900FF"}}/>
                <Chart title="Total Volatile Organic Compounds (ppb)" data={TVOC} name="date" domain={[0, 500]} stroke={{TVOC: "#548CFF"}}/>
                <Chart title="Temperature (&deg;C)" data={temp} name="date" domain={[20, 40]} stroke={{Temp: "#FF00BE"}}/>
                <Chart title="Accelerometer (m/s)" data={accel} name="date" domain={[-15, 15]} stroke={{x: "#FF0000", y: "#00FF00", z: "#0000FF"}}/>
            </div>
        </div>
    );
}

export default App;