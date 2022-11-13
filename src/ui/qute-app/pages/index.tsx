import {useEffect, useState} from "react";
import {getAllTasks} from "./TaskService";
import {Task} from "./Task";

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [isLoading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getAllTasks()
      .then((tasks) => {
        setTasks(tasks)
        setLoading(false)
      })
  }, []);

  if (isLoading) return <p>Loading...</p>
  if (!tasks) return <p>No tasks</p>

  return (
    <div>
      <h1>Tasks</h1>
      <ul>
        {tasks.map(task =>
          <li
            key={task.key}>{task.dueDate ? task.dueDate : ''}{task.dueDate && task.title ? ': ' : ''}{task.title ? task.title : ''}</li>
        )}
      </ul>
    </div>
  );
}
