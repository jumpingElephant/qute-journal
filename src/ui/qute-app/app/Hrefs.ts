export const HREF_TASKS = '/';
export const HREF_NOT_IMPLEMENTED = '/not-implemented';
export const HREF_CREATE_TASK = '/task';

export function HREF_TASK_BY_ID(taskId: string) {
  return `/task/${taskId}`;
}
