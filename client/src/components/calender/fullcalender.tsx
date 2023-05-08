import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useState, useEffect } from "react";
import axios from "axios";
import { v4 as uuidv4 } from "uuid";

interface Event {
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

interface ServerEvent {
  title: string;
  allDay: boolean;
  schedule: {
    start: string;
    end: string;
  }[];
  hour: {
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

function transformData(serverData: ServerEvent): Event {
  const { title, allDay, schedule, hour, description, overlap, extendedProps, color } = serverData;

  const start = new Date(`${schedule[0].start}T${hour[0].start.replace("24:00:00", "00:00:00")}`).toISOString();
  const end = new Date(`${schedule[0].end}T${hour[0].end.replace("24:00:00", "00:00:00")}`).toISOString();

  return {
    id: uuidv4(),
    title,
    allDay,
    start,
    end,
    description,
    overlap: overlap === "true",
    extendedProps,
    color,
  };
}

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);

  useEffect(() => {
    axios
      .get<ServerEvent[]>("http://localhost:3001/event")
      .then((response) => {
        const transformedData = response.data.map(transformData);
        setEvents(transformedData);
      })
      .catch((error) => console.error(error));
  }, []);

  const handleEventClick = (info: any) => {
    alert("clicked");
    console.log(info.event);
  };

  return (
    <>
      <FullCalendar
        plugins={[timeGridPlugin]}
        initialView="timeGridWeek"
        allDaySlot={true}
        weekends={true}
        events={events}
        eventClick={handleEventClick}
      />
    </>
  );
};

export default Calendar;
