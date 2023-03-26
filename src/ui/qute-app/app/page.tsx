import HomePage from './HomePage';
import { getAllTasks } from "./task/TaskService";

export const metadata = {
  title: 'TODO-App',
  themeColor: '#ffffff'
};

export default async function Page() {
  const recentTasks = await getAllTasks();

  return <HomePage tasks={recentTasks}/>;
}
