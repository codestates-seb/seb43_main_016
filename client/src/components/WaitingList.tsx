import styled from "styled-components";
import { useEffect } from "react";
import axios from "axios";

// TODO 서버에서 받아온 데이터를 동적으로 렌더링하여 리스트로 보내는 컴포넌트 // ? 엔드포인트를 알 수 없음 ===> 서버에 추후 문의
// const WaitingListContents = ( { waitingList }) => {
//   return waitingList.map((study) => (
//     <div key={study.id}>{study.name}</div>
//   ));
// };

const WaitingList = () => {
  // const [waitingList, setWaitingList] = useState([]);

  // TODO 서버에서 리스트를 받아오는 코드 // ? 엔드포인트를 알 수 없음 ===> 서버에 추후 문의
  useEffect(() => {
    const fetchWaitingList = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        const res = await axios.get(`${import.meta.env.VITE_APP_API_URL}/waiting-list`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log(res.data);
        // setWaitingList(res.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchWaitingList();
  }, []);

  return (
    <WaitingListWrapper>
      <WaitingListTitle>신청중인 스터디</WaitingListTitle>
      {/* <WaitingListContents waitingList={waitingList} /> */}
      <ul>
        <li>스터디 이름</li>
        <li>스터디 이름</li>
        <li>스터디 이름</li>
      </ul>
    </WaitingListWrapper>
  );
};

export default WaitingList;

const WaitingListWrapper = styled.div``;
const WaitingListTitle = styled.div``;