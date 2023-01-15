import { useState } from "react";
import { deleteTask, getAllTasks } from "./TaskService";
import { Task } from "./Task";

const quarkusUrl = `http://localhost:8080`;

export default function Home({data}) {
    const [tasks, setTasks] = useState<Task[]>(data);
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
        <div>
            <h1>Tasks</h1>
            <ul>
                {tasks.map(task =>
                    <li onClick={() => onDeleteTask(task.key)}
                        key={task.key}>{task.dueDate ? task.dueDate : ''}{task.dueDate && task.title ? ': ' : ''}{task.title ? task.title : ''}</li>
                )}
            </ul>
        </div>
    );
}

export async function getServerSideProps(): Promise<{ props: { data: Task[] } }> {
    const response = await fetch(`${quarkusUrl}/tasks`)
    const data: Task[] = await response.json();

    return {props: {data}}
}
