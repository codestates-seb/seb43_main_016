import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useState, useEffect } from "react";
import axios from "axios";

interface Event {
  id: string;
  title: string;
  allDay: boolean;
  daysOfWeek: number[];
  start: string;
  end: string;
  description: string;
  overlap: boolean;
  extendedProps: {
    department: string;
  };
}

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);

  useEffect(() => {
    axios
      .get("http://localhost:3001/event")
      .then((response) => setEvents(response.data))
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
