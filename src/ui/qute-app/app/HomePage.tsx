'use client';
import { useState } from "react";
import { deleteTask, getAllTasks } from "./TaskService";
import { Task } from "./Task";

export type HomeProps = {
    tasks: Task[]
}

export default function HomePage(data: HomeProps) {
    const [tasks, setTasks] = useState<Task[]>(data.tasks);
    const [isLoading, setLoading] = useState(false);

    const onDeleteTask = (key: string) => deleteTask(key)
        .finally(() => {
            setLoading(true);
            getAllTasks()
                .then((tasks) => {
                    setTasks(tasks)
                    setLoading(false)
                })
        })

    if (isLoading) return <p>Loading...</p>
    if (!tasks) return <p>No tasks</p>

    return (
        <div className="container">
            <div className="row">
                <div className="col s12 xl7 offset-m1">
                    <div className="section">
                        <div className="row">
                            <div className="col s12">
                                <h3 className="header">Aufgaben</h3>

                                <article>
                                    <div className="col s12">
                                        <div className="row">
                                            <div className="col s7">
                                                <p className="caption">Erstelle deine Aufgaben mit Quarkus RESTEasy &
                                                    <a href={'https://nextjs.org'} target={'_blank'}>next.js</a></p>
                                            </div>
                                            <div className="col s3 right-align">
                                                <a className="waves-effect waves-light btn" href="{path:root}/create"><i
                                                    className="material-icons left">add</i>Hinzufügen</a>
                                            </div>
                                        </div>
                                        {tasks.map(task =>
                                            <section key={task.key}>
                                                <div className="row" aria-label="list of tasks">
                                                    <div className="col s10">
                                                        <div className="card">
                                                            <div className="card-content">
                                                                <div className="row">
                                                                    <div className="col s6 card-title">
                                                                        <span className="card-title"
                                                                              aria-label="title">{task.title}</span>
                                                                    </div>
                                                                    {task.dueDate &&
                                                                        <div className="col s6 right-align">
                                                                        <span className="card-title"
                                                                              aria-label="due date">{task.dueDate}</span>
                                                                        </div>
                                                                    }
                                                                </div>
                                                                <div className="card-action">
                                                                    <a className="waves-effect waves-light btn"
                                                                       href="{path:root}/edit/{task.key}"><i
                                                                        className="material-icons left">edit</i>Bearbeiten</a>
                                                                    <a className="waves-effect waves-red btn"
                                                                       onClick={() => onDeleteTask(task.key)}><i
                                                                        className="material-icons left">delete</i>Entfernen</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </section>
                                        )}
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
