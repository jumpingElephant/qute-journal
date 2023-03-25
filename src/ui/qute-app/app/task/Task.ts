import { format, parse } from 'date-fns';

export class Task {
  key!: string;
  title?: string;
  dueDate?: string;
}

export function convertDtoDateValueToDisplayString(s: string) {
  if (s) {
    return format(parse(s, 'yyyy-MM-dd', new Date()), 'dd.MM.yyyy');
  } else {
    return <string>s;
  }
}

export function todayToValueString() {
  return format(new Date(), 'yyyy-MM-dd');
}
