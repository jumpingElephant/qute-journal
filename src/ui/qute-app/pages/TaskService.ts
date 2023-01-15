import { Task } from "../app/Task";

export function getAllTasks(): Promise<Task[]> {
  return fetch('/tasks')
    .then(response => {
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      return response.json() as Promise<Task[]>;
    })
}

export function deleteTask(key: string): Promise<any> {
  return fetch('/tasks/' + key, {
    method: 'DELETE'
  })
}