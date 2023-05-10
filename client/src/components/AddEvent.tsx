import React, { useState } from "react";

interface EventInfo {
  title: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  minPerson: number;
  maxPerson: number;
  platform: string;
  tag: string;
  description: string;
  color: string;
}

const AddEvent = () => {
  const [eventInfo, setEventInfo] = useState<EventInfo>({
    title: "",
    startDate: "",
    endDate: "",
    startTime: "",
    endTime: "",
    minPerson: 0,
    maxPerson: 0,
    platform: "",
    tag: "",
    description: "",
    color: "#ffffff",
  });

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setEventInfo((prevState) => ({
      ...prevState,
      [name]:
        name === "minPerson" || name === "maxPerson" ? Number(value) : value,
    }));
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const eventObject = {
      title: eventInfo.title,
      allDay: false,
      schedules: [
        {
          start: eventInfo.startDate,
          end: eventInfo.endDate,
        },
      ],
      hours: [
        {
          start: eventInfo.startTime,
          end: eventInfo.endTime,
        },
      ],
      description: eventInfo.description,
      overlap: "true",
      extendedProps: {
        department: eventInfo.tag,
      },
      color: eventInfo.color,
    };

    const eventArray = [eventObject];

    console.log({ event: eventArray });
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        <input
          type="text"
          name="title"
          value={eventInfo.title}
          onChange={handleInputChange}
          placeholder="스터디 이름"
        />
      </label>
      <label>
        시작일:
        <input
          type="date"
          name="startDate"
          value={eventInfo.startDate}
          onChange={handleInputChange}
        />
      </label>
      <label>
        종료일:
        <input
          type="date"
          name="endDate"
          value={eventInfo.endDate}
          onChange={handleInputChange}
        />
      </label>
      <label>
        시작시간:
        <input
          type="time"
          name="startTime"
          value={eventInfo.startTime}
          onChange={handleInputChange}
        />
      </label>
      <label>
        종료시간:
        <input
          type="time"
          name="endTime"
          value={eventInfo.endTime}
          onChange={handleInputChange}
        />
      </label>
      <label>
        최소 인원:
        <input
          type="number"
          name="minPerson"
          value={eventInfo.minPerson}
          onChange={handleInputChange}
        />
      </label>
      <label>
        최대 인원:
        <input
          type="number"
          name="maxPerson"
          value={eventInfo.maxPerson}
          onChange={handleInputChange}
        />
      </label>
      <label>
        플랫폼:
        <input
          type="text"
          name="platform"
          value={eventInfo.platform}
          onChange={handleInputChange}
        />
      </label>
      <label>
        태그:
        <input
          type="text"
          name="tag"
          value={eventInfo.tag}
          onChange={handleInputChange}
        />
      </label>
      <label>
        색상:
        <input
          type="color"
          name="color"
          value={eventInfo.color}
          onChange={handleInputChange}
        />
      </label>
      <label>
        설명:
        <input
          type="text"
          name="description"
          value={eventInfo.description}
          onChange={handleInputChange}
        />
      </label>
      <button type="submit">추가</button>
    </form>
  );
};

export default AddEvent;
