import React, { useState } from "react";

interface EventInfo {
  title: string;
  daysOfWeek: number[];
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
    daysOfWeek: [],
    startDate: "",
    endDate: "",
    startTime: "",
    endTime: "",
    minPerson: 0,
    maxPerson: 0,
    platform: "",
    tag: "",
    color: "#ffffff",
    description: "",
  });
  // TODO: 요일을 선택하면, 해당 요일을 0,1,2 ... 6의 데이터로 변환하는 코드

  // TODO: input 값이 바뀔 때마다 eventInfo를 업데이트하는 코드
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setEventInfo((prevState) => ({
      // 아래의 경우 외에는 이전 상태를 그대로 유지한다.
      ...prevState,
      [name]:
        // minPerson, maxPerson을 정하는 코드
        name === "minPerson" || name === "maxPerson" ? Number(value) : value,
    }));
  };

  // TODO: 이벤트 등록 버튼 클릭 시, 이벤트를 서버 형식에 맞춰 재처리하는 코드
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
