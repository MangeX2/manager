/* 
 * Copyright Axemtum 2024
 */
import ManagerChart from "./gantt/managerChart";
const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

    render() {
        return (<ManagerChart/>);
    }
}

ReactDOM.render(
        <App />,
        document.getElementById('react')
        );