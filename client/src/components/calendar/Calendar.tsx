import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { generateStudyEvents, Event } from "../../apis/CalendarApi";
import AddEventModal from "../modal/AddEvent";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
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

  const handleDateClick = (info: { dateStr: string }) => {
    setIsModalOpen(true);
    setSelectedDate(info.dateStr);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedDate(null);
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
        slotMinTime={"09:00"}
        slotMaxTime={"33:00"}
        slotEventOverlap={true}
        height={"100%"}
      />
      {selectedDate && (
        <AddEventModal
          isOpen={isModalOpen}
          closeModal={closeModal}
        />
      )}
    </>
  );
};

export default Calendar;
