import { format } from 'date-fns';

export class Task {
  key!: string;
  title?: string;
  dueDate?: string;
}

export function dateToString(date: Date) {
  if (date) {
    return format(date, 'dd.MM.yyyy');
  }
}

export function todayToValueString() {
  return format(new Date(), 'yyyy-MM-dd');
}
