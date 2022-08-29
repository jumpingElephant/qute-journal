import React, {Component} from 'react';
import './App.css';
import Tasks from "./components/Tasks";
import {deserializeDate, TaskDto} from "./components/Task";
import {get} from "./components/Http";
import {sort} from "fast-sort";

export default class App extends Component {
    state = {
        taskList: []
    };

    async componentDidMount() {
        const taskDataList = await get<TaskDto[]>('/tasks');
        this.setState({
            taskList:
                sort(taskDataList
                    .map(taskDataItem => ({
                        dueDate: deserializeDate(taskDataItem.dueDate),
                        key: taskDataItem.key,
                        title: taskDataItem.title
                    }))
                ).desc([
                    task => task.dueDate,
                    task => task.title
                ]).reverse() // to sort nil values to the top
        });
    }

    render() {
        return (
            <div className="App">
                <Tasks tasks={this.state.taskList}></Tasks>
            </div>
        );
    }
}
