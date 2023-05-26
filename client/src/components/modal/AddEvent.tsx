import { useState, ChangeEvent } from "react";
import Modal from "react-modal";
import { generateCustomEvents } from "../../apis/CalendarApi";
import { useRecoilValue } from "recoil";
import { LogInState } from "../../recoil/atoms/LogInState";

interface EventInfo {
  title: string;
  studyTimeStart: string;
  studyTimeEnd: string;
  platform: string;
  description: string;
  color: string;
}

interface AddEventProps {
  isOpen: boolean;
  closeModal: () => void;
<<<<<<< HEAD
  onNewEvent?: () => void;
}

const AddEvent = ({ isOpen, closeModal, onNewEvent }: AddEventProps) => {
=======
}

const AddEvent = ({ isOpen, closeModal }: AddEventProps) => {
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  const isLoggedIn = useRecoilValue(LogInState);
  const [eventInfo, setEventInfo] = useState<EventInfo>({
    title: "",
    studyTimeStart: "",
    studyTimeEnd: "",
    platform: "",
    description: "",
    color: "#557AB4",
  });

  const handleInputChangeText = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setEventInfo((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleInputChangeTextArea = (
    event: ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setEventInfo((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
<<<<<<< HEAD
    await generateCustomEvents(isLoggedIn, eventInfo);
    closeModal();
    onNewEvent && onNewEvent();
=======
    // eventInfo 상태 업데이트 후에 generateCustomEvents 호출
    await setEventInfo((prevState) => ({
      ...prevState,
    }));
    await generateCustomEvents(isLoggedIn, eventInfo);
    closeModal();
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
  };

  const customModalStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      border: "none",
      boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
      borderRadius: "8px",
      maxWidth: "400px",
      padding: "24px",
<<<<<<< HEAD
      display: "flex",
      flexDirection: undefined,
      alignItems: "center",
      justifyContent: "space-between",
=======
      // display: "flex", // remove this
      // flexDirection: "column", // remove this
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
    },
    overlay: {
      zIndex: 9999,
      backgroundColor: "rgba(0, 0, 0, 0.5)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
    },
  };

  return (
    <div className="modal-wrapper">
      <Modal
        isOpen={isOpen}
        onRequestClose={closeModal}
        contentLabel="Add Event Modal"
        style={customModalStyles}
      >
        <form onSubmit={handleSubmit}>
          <div>
            <label>
              <input
                type="text"
                name="title"
                value={eventInfo.title}
                onChange={handleInputChangeText}
<<<<<<< HEAD
                placeholder="일정명"
                required
=======
                placeholder="스터디 이름"
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
              />
            </label>
          </div>
          <div>
            <label>
              <input
                type="datetime-local"
                name="studyTimeStart"
                value={eventInfo.studyTimeStart}
                onChange={handleInputChangeText}
<<<<<<< HEAD
                required
=======
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
              />
            </label>
          </div>
          <div>
            <label>
              <input
                type="datetime-local"
                name="studyTimeEnd"
                value={eventInfo.studyTimeEnd}
                onChange={handleInputChangeText}
<<<<<<< HEAD
                required
=======
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
              />
            </label>
          </div>
          <div>
            <label>
              <input
                type="text"
                name="platform"
                placeholder="스터디 플랫폼"
                value={eventInfo.platform}
                onChange={handleInputChangeText}
              />
            </label>
          </div>
          <div>
            <label>
              색상:
              <input
                type="color"
                name="color"
                value={eventInfo.color}
                onChange={handleInputChangeText}
                style={{ marginTop: "8px" }}
<<<<<<< HEAD
                required
=======
>>>>>>> d6e29db2b9e5868d24be2a5b05aef6ebc6fb46a3
              />
            </label>
          </div>
          <div>
            <label>
              <textarea
                name="description"
                placeholder="스터디 설명"
                value={eventInfo.description}
                onChange={handleInputChangeTextArea}
                style={{ marginTop: "8px" }}
              ></textarea>
            </label>
          </div>
          <div>
            <button type="submit" style={{ font: "12px" }}>
              이벤트 추가
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
};

export default AddEvent;
