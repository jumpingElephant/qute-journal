import { Task } from "./Task";

const quarkusUrl = `http://localhost:8080`;

function getHostPath(): string {
    return typeof window === 'undefined' ? quarkusUrl : '';
}

export function getAllTasks(): Promise<Task[]> {
    // let hostPath = getHostPath();
    // console.log("hostPath",hostPath)
    return fetch(`${getHostPath()}/tasks`)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            return response.json() as Promise<Task[]>;
        })
}

export function getTaskById(taskId: string): Promise<Task> {
    return fetch(`${getHostPath()}/tasks/${taskId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.statusText);
            }
            return response.json() as Promise<Task>;
        })
}

export function updateTask(task: Task): Promise<any> {
    let headers = new Headers();
    headers.set('Content-Type', 'application/json');
    return fetch(`${getHostPath()}/tasks/${task.key}`, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(task)
    })
}

export function deleteTask(key: string): Promise<any> {
    return fetch(`${getHostPath()}/tasks/${key}`, {
        method: 'DELETE'
    })
}

export function resetTasks(): Promise<any> {
    return fetch(`${getHostPath()}/dev/reset`, {
        method: 'POST'
    })
}
