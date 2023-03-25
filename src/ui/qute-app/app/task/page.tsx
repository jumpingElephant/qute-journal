import TaskPage from "./[taskid]/TaskPage";
import { Task, todayToValueString } from "./Task";

export type CreateTaskArguments = {
  params: {
    searchParams: any
  }
}

export default async function Page(args: CreateTaskArguments) {
  const task: Task = {key: '', dueDate: todayToValueString()};
  return <TaskPage task={task} isNewTask={true}/>;
};
