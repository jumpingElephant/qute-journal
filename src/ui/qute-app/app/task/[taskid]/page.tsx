import TaskPage from "./TaskPage";
import { getTaskById } from "../TaskService";

export type TaskArguments = {
  params: {
    taskid: string,
    searchParams: any
  }
}

export default async function Page(args: TaskArguments) {

  const taskId = args.params.taskid;
  let task = undefined;
  await getTaskById(taskId)
      .then(value => task = value)
      .catch(reason => {
        console.log('reason', reason)
      });
  if (task) {
    return <TaskPage task={task} isNewTask={false}/>;
  }
};
