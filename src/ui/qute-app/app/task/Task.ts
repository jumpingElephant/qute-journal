import { format } from 'date-fns';

export class Task {
    key!: string;
    title?: string;
    dueDate?: string;
}

export function dateToString(date: Date) {
    console.log('date', date, typeof date)
    if (date) {
        return format(date, 'dd.MM.yyyy');
    }
}