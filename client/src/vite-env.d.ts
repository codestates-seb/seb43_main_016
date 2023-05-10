/// <reference types="vite/client" />
export interface Event {
  id: string;
  title: string;
  allDay: boolean;
  start: string;
  end: string;
  description: string;
  overlap: boolean;
  extendedProps: {
    department: string;
  };
  color?: string;
}

export interface ServerEvent {
  title: string;
  allDay: boolean;
  schedules: {
    start: string;
    end: string;
  }[];
  hours: {
    start: string;
    end: string;
  }[];
  description: string;
  overlap: string;
  extendedProps: {
    department: string;
  };
  color?: string;
}