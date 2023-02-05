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

export function deleteTask(key: string): Promise<any> {
    return fetch(`${getHostPath()}/tasks/${key}`, {
        method: 'DELETE'
    })
}
