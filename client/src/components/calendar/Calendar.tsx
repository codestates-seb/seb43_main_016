import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
<<<<<<< HEAD
import {
  generateStudyEvents,
  StudyEvent,
  getCustomEvent,
  FullCalendarEvent,
} from "../../apis/CalendarApi";
import AddEventModal from "../modal/AddEvent";
import ViewCalendarModal from "../modal/ViewCalendarEvent";
=======
import { generateStudyEvents, Event } from "../../apis/CalendarApi";
import AddEventModal from "../modal/AddEvent";
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";
import ViewCustomCalendarEvent from "../modal/ViewCustomCalendarEvent";

const Calendar = () => {
<<<<<<< HEAD
  const [studyEvents, setStudyEvents] = useState<StudyEvent[]>([]);
  const [customEvents, setCustomEvents] = useState<FullCalendarEvent[]>([]);
  const [addEventModalOpen, setAddEventModalOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const [viewCalendarEventModalOpen, setViewCalendarEventModalOpen] =
    useState<boolean>(false);
  const [
    viewCustomCalendarEventModalOpen,
    setViewCustomCalendarEventModalOpen,
  ] = useState<boolean>(false);
  const [selectedStudyEvent, setSelectedStudyEvent] = useState<number | null>(
    null
  );
  const [selectedCustomEvent, setSelectedCustomEvent] = useState<number | null>(
    null
  );
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();

  console.log(customEvents[0]);
=======
  const [events, setEvents] = useState<Event[]>([]);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const isLoggedIn = useRecoilValue(LogInState);
  const navigate = useNavigate();
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
      alert("로그인이 필요합니다");
    } else {
      const fetchEvents = async () => {
        try {
          const generatedStudyEvents = await generateStudyEvents(true);
          setStudyEvents(generatedStudyEvents);
        } catch (error) {
          alert("스터디 일정을 불러오는 데 실패했습니다");
        }
      };
      fetchEvents();
    }
  }, [isLoggedIn, navigate]);

<<<<<<< HEAD
  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
      alert("로그인이 필요합니다");
    } else {
      const fetchCustomEvents = async () => {
        try {
          const generatedCustomEvents = await getCustomEvent(true);
          setCustomEvents(generatedCustomEvents);
        } catch (error) {
          alert("커스텀 이벤트를 불러오는 데 실패했습니다");
          console.log(error);
        }
      };
      fetchCustomEvents();
    }
  }, [isLoggedIn, navigate]);

  const reloadCustomEvents = async () => {
    try {
      const generatedCustomEvents = await getCustomEvent(true);
      setCustomEvents(generatedCustomEvents);
    } catch (error) {
      alert("커스텀 이벤트를 불러오는 데 실패했습니다");
      console.log(error);
    }
  };

  useEffect(() => {
    if (isLoggedIn) reloadCustomEvents();
  }, [isLoggedIn, navigate]);

  const everyEvents = [...studyEvents, ...customEvents];

  const handleDateClick = (info: { dateStr: string }) => {
    setAddEventModalOpen(true);
    setSelectedDate(info.dateStr);
  };

  const handleEventClick = (event: any) => {
    if (event.event._def.extendedProps.divide === "studyGroup") {
      setSelectedStudyEvent(Number(event.event._def.publicId));
      setViewCalendarEventModalOpen(true);
    } else if (event.event._def.extendedProps.divide === "customEvent") {
      setSelectedCustomEvent(Number(event.event._def.publicId));
      setViewCustomCalendarEventModalOpen(true);
    }
  };

  const closeModal = () => {
    setAddEventModalOpen(false);
    setSelectedDate(null);
    setViewCalendarEventModalOpen(false);
    setViewCustomCalendarEventModalOpen(false);
    setSelectedStudyEvent(null);
    setSelectedCustomEvent(null);
=======
  const handleDateClick = (info: { dateStr: string }) => {
    setIsModalOpen(true);
    setSelectedDate(info.dateStr);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedDate(null);
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  };

  return (
    <>
      <FullCalendar
        plugins={[timeGridPlugin, interactionPlugin]}
        initialView="timeGridWeek"
        allDaySlot={false}
        weekends={true}
<<<<<<< HEAD
        events={everyEvents}
        dateClick={handleDateClick}
        eventClick={handleEventClick}
=======
        events={events}
        dateClick={handleDateClick}
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
        slotMinTime={"09:00"}
        slotMaxTime={"33:00"}
        slotEventOverlap={true}
        height={"100%"}
      />
      {selectedDate && (
        <AddEventModal
<<<<<<< HEAD
          isOpen={addEventModalOpen}
          closeModal={closeModal}
          onNewEvent={reloadCustomEvents}
        />
      )}
      {selectedStudyEvent && (
        <ViewCalendarModal
          isOpen={viewCalendarEventModalOpen}
          closeModal={closeModal}
          id={selectedStudyEvent}
        />
      )}
      {selectedCustomEvent && (
        <ViewCustomCalendarEvent
          isOpen={viewCustomCalendarEventModalOpen}
          closeModal={closeModal}
          id={selectedCustomEvent}
=======
          isOpen={isModalOpen}
          closeModal={closeModal}
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
        />
      )}
    </>
  );
};

export default Calendar;
