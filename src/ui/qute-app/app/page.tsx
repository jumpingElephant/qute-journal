// Import your Client Component
import HomePage from './HomePage';
import { Task } from "./Task";

const quarkusUrl = `http://localhost:8080`;

async function getTasks(): Promise<Task[]> {
    const response = await fetch(`${quarkusUrl}/tasks`)
    return await response.json();
}

export default async function Page() {
    // Fetch data directly in a Server Component
    const recentTasks = await getTasks();
    // Forward fetched data to your Client Component
    return <HomePage tasks={recentTasks}/>;
}
