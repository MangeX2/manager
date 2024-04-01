/* 
 * Copyright Axemtum 2024
 */
import ManagerChart from "./gantt/managerChart";
import { createRoot } from 'react-dom/client';
const React = require('react');

class App extends React.Component {

    render() {
        return (<ManagerChart/>);
    }
}

const container = document.getElementById('react');
const root = createRoot(container);
root.render(<App />);