import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { generateStudyEvents, Event } from "../../apis/CalendarApi";
import AddEventModal from "../modal/AddEvent";
import ViewCalendarModal from "../modal/ViewCalendarEvent";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [addEventModalOpen, setAddEventModalOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const [viewCalendarEventModalOpen, setViewCalendarEventModalOpen] = useState<boolean>(false);
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null);
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
      alert("로그인이 필요합니다");
    } else {
      const fetchEvents = async () => {
        try {
          const generatedEvents = await generateStudyEvents(true);
          setEvents(generatedEvents);
        } catch (error) {
          alert("스터디 일정을 불러오는 데 실패했습니다");
        }
      };
      fetchEvents();
    }
  }, []);

  console.log(events);

  const handleDateClick = (info: { dateStr: string }) => {
    setAddEventModalOpen(true);
    setSelectedDate(info.dateStr);
  };

  const handleEventClick = (event : any) => {
    setSelectedEvent(event.event._def.publicId);
    setViewCalendarEventModalOpen(true);
  };

  const closeModal = () => {
    setAddEventModalOpen(false);
    setSelectedDate(null);
    setViewCalendarEventModalOpen(false);
    setSelectedEvent(null);
  };

  return (
    <>
      <FullCalendar
        plugins={[timeGridPlugin, interactionPlugin]}
        initialView="timeGridWeek"
        allDaySlot={false}
        weekends={true}
        events={events}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
        slotMinTime={"09:00"}
        slotMaxTime={"33:00"}
        slotEventOverlap={true}
        height={"100%"}
      />
      {selectedDate && (
        <AddEventModal isOpen={addEventModalOpen} closeModal={closeModal} />
      )}
      {selectedEvent && (
        <ViewCalendarModal
          isOpen={viewCalendarEventModalOpen}
          closeModal={closeModal}
          id={Number(selectedEvent)}
        />
      )}
    </>
  );
};

export default Calendar;
