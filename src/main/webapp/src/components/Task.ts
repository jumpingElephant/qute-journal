import {parse} from "date-fns";

export interface Task {
    key: string
    title: string
    dueDate?: Date
}
export interface TaskDto {
    key: string
    title: string
    dueDate?: string
}

export function deserializeDate(dateString?: string) {
    return dateString ? parse(dateString, 'yyyy-mm-dd', new Date()) : undefined;
}