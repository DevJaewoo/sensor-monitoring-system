import React, { PureComponent } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import './Chart.css'

class Chart extends PureComponent {
    render() {
        return (
            <div className="chart__container">
                <h2 className="chart__title">{this.props.title}</h2>
                <ResponsiveContainer width="100%" height="90%">
                    <LineChart data={this.props.data}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey={this.props.name} />
                        <YAxis domain={this.props.domain}/>
                        <Tooltip />
                        <Legend />
                        {Object.keys(this.props.data[0]).map((key, index) => {
                            if(key !== this.props.name) return <Line type="monotone" key={index} dataKey={key} stroke={this.props.stroke[key]} strokeWidth={1}/>
                        })}
                    </LineChart>
                </ResponsiveContainer>
            </div>
        );
    }
}

export default Chart;