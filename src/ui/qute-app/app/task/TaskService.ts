import { Task } from "./Task";

const quarkusUrl = `http://0.0.0.0:8080`;

function getHostPath(): string {
  return typeof window === 'undefined' ? quarkusUrl : '';
}

export function getAllTasks(): Promise<Task[]> {
  let headers = new Headers();
  headers.set('Cache-Control', 'no-store');
  return fetch(`${getHostPath()}/tasks`, {headers})
      .then(response => {
        if (!response.ok) {
          throw new Error(response.statusText);
        }
        return response.json() as Promise<Task[]>;
      })
}

export function getTaskById(taskId: string): Promise<Task> {
  let headers = new Headers();
  headers.set('Cache-Control', 'no-store');
  return fetch(`${getHostPath()}/tasks/${taskId}`, {headers})
      .then(response => {
        console.log('status', response.status);
        if (!response.ok) {
          throw new Error(response.statusText);
        }
        return response.json() as Promise<Task>;
      })

}

export function createTask(task: Task): Promise<any> {
  let headers = new Headers();
  headers.set('Cache-Control', 'no-store');
  headers.set('Content-Type', 'application/json');
  return fetch(`${getHostPath()}/tasks`, {
    method: 'POST',
    headers,
    body: JSON.stringify(task)
  })
}

export function updateTask(task: Task): Promise<any> {
  let headers = new Headers();
  headers.set('Cache-Control', 'no-store');
  headers.set('Content-Type', 'application/json');
  return fetch(`${getHostPath()}/tasks/${task.key}`, {
    method: 'PUT',
    headers,
    body: JSON.stringify(task)
  })
}

export function deleteTask(key: string): Promise<any> {
  let headers = new Headers();
  headers.set('Cache-Control', 'no-store');
  headers.set('Content-Type', 'application/json');
  return fetch(`${getHostPath()}/tasks/${key}`, {
    method: 'DELETE',
    headers
  })
}

export function resetTasks(): Promise<any> {
  return fetch(`${getHostPath()}/dev/reset`, {
    method: 'POST'
  })
}
