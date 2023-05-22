import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useState, useEffect } from "react";
import { generateStudyEvents, Event } from "../../apis/CalendarApi";

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const generatedEvents = await generateStudyEvents(true);
        setEvents(generatedEvents);
      } catch (error) {
        console.error(error);
      }
    };

    fetchEvents();
  }, []);

  const handleEventClick = (info: { event: any; }) => {
    alert("clicked");
    console.log(info.event);
  };

  return (
    <>
      <FullCalendar
        plugins={[timeGridPlugin]}
        initialView="timeGridWeek"
        allDaySlot={false}
        weekends={true}
        events={events}
        eventClick={handleEventClick}
      />
    </>
  );
};

export default Calendar;
