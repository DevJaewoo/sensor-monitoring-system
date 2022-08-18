import React, { PureComponent } from 'react';
import {
    LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
} from 'recharts';

//this.props.data
class Chart extends PureComponent {

    render() {
        //console.log(this.props.data)
        return (
            <LineChart
                width={500}
                height={300}
                data={this.props.data}
                margin={{
                    top: 5, right: 30, left: 20, bottom: 5,
                }}
            >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey={this.props.name} />
                <YAxis />
                <Tooltip />
                <Legend />
                {Object.keys(this.props.data[0]).map((key, index) => {
                    if(key !== this.props.name) return <Line type="monotone" key={index} dataKey={key} stroke={this.props.stroke[key]}/>
                })}
            </LineChart>
        );
    }
}

export default Chart;