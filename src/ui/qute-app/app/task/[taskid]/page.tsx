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
    console.log('taskid', taskId);
    const task = await getTaskById(taskId);
    return <TaskPage task={task}/>;
    console.log(task)

};
