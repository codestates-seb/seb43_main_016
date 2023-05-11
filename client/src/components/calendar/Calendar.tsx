import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useState, useEffect } from "react";
import axios from "axios";
import { v4 as uuidv4 } from "uuid";

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
const Calendar = () => {
  const [events, setEvents] = useState<Event[]>([]);

  // TODO 서버 측 데이터 형식을 클라이언트 측 데이터 형식으로 변환하는 함수
  const transformData = (serverEvent: ServerEvent): Event[] => {
    const transformedEvents = serverEvent.schedules.map((schedules, index) => {
      return {
        id: uuidv4(),
        title: serverEvent.title,
        allDay: serverEvent.allDay,
        start: `${schedules.start.replaceAll(".", "-")}T${
          serverEvent.hours[index].start
        }`,
        end: `${schedules.end.replaceAll(".", "-")}T${
          serverEvent.hours[index].end
        }`,
        description: serverEvent.description,
        overlap: serverEvent.overlap === "true",
        extendedProps: serverEvent.extendedProps,
        color: serverEvent.color,
      };
    });
    // console.log(transformedEvents); // ! Debug
    return transformedEvents;
  };

  // TODO 서버에서 데이터를 받아오는 함수
  useEffect(() => {
    axios
      .get<ServerEvent[]>("http://localhost:3001/event")
      .then((res) => {
        const transformedData = res.data.flatMap(transformData);
        setEvents(transformedData);
      })
      .catch((error) => console.error(error));
  }, []);

  // TODO 이벤트를 클릭했을 때 발생하는 함수 ===> // TODO 추후 이벤트의 상세 내용을 표현할 예정
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
