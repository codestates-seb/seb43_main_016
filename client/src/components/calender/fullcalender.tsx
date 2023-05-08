import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useState, useEffect } from "react";
import axios from "axios";
import { v4 as uuidv4 } from "uuid";
import { Event, ServerEvent } from "./FullcalenderType";

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);

  const transformData = (serverEvent: ServerEvent): Event[] => {
    const transformedEvents = serverEvent.schedule.map((schedule, index) => {
      return {
        id: uuidv4(),
        title: serverEvent.title,
        allDay: serverEvent.allDay,
        start: `${schedule.start.replaceAll(".","-")}T${serverEvent.hour[index].start}`,
        end: `${schedule.end.replaceAll(".","-")}T${serverEvent.hour[index].end}`,
        description: serverEvent.description,
        overlap: serverEvent.overlap === "true",
        extendedProps: serverEvent.extendedProps,
        color: serverEvent.color,
      };
    });
    // console.log(transformedEvents); // ! Debug
    return transformedEvents;
  };

  useEffect(() => {
    axios
      .get<ServerEvent[]>("http://localhost:3001/event")
      .then((res) => {
        const transformedData = res.data.flatMap(transformData);
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
