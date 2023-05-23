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
        alert("스터디 일정을 불러오는 데 실패했습니다")
      }
    };
    fetchEvents();
  }, []);

  const handleEventClick = (info: { event: any }) => {
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
        slotMinTime={"09:00"} // 시작 시간을 09:00으로 설정
        slotMaxTime={"33:00"} // 종료 시간을 24:00으로 설정
        slotEventOverlap={true} // 이벤트가 겹치지 않도록 설정
        height={"100%"}
      />
    </>
  );
};

export default Calendar;