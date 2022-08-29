import React from "react";
import {Task} from "./Task";
import {format} from 'date-fns';

export default function Tasks({tasks}: { tasks: Task[] }) {
    return (
        <div>
            <h1>Particles List</h1>
            <table className="pf-c-table pf-m-grid-md" role="grid" aria-label="Supersonic Subatomic Tasks"
                   id="table-basic">
                <caption>Supersonic Subatomic Tasks</caption>
                <thead>
                <tr role="row">
                    <th role="columnheader" scope="col">Name</th>
                </tr>
                </thead>
                {tasks.map((task) => (
                    <tbody role="rowgroup">
                    <tr role="row">
                        <td role="cell" data-label="Particle name">{task.title}</td>
                        <td role="cell"
                            data-label="Particle due date">{task.dueDate ? format(task.dueDate, 'dd.mm.yyyy') : ''}</td>
                    </tr>
                    </tbody>
                ))}
            </table>
        </div>
    );
};