import HomePage from './HomePage';
import { getAllTasks } from "./TaskService";

export default async function Page() {
    const recentTasks = await getAllTasks();

    return <HomePage tasks={recentTasks}/>;
}
