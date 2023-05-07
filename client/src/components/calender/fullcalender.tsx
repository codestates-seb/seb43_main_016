import FullCalendar from "@fullcalendar/react";
import timeGridPlugin from "@fullcalendar/timegrid";

const FullCalender = () => {
  return (
    <>
      <FullCalendar
        plugins={[timeGridPlugin]}
        initialView="timeGridWeek"
        allDaySlot={true}
        weekends={true}
        events={[

        ]}
      />
    </>
  );
};

export default FullCalender;
