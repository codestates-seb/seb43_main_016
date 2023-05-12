import axios from "axios";

// TODO StudyGroup의 정보를 get 요청으로 가져오는 코드
export const getStudyInfo = async (id: number, accessToken: string | null) => {
  try {
    // accessToken이 null인 경우 alert를 띄우고 함수를 종료한다.
    if (!accessToken) {
      alert('권한이 없습니다.')
    }

    const response = await axios.get(
      `${import.meta.env.VITE_APP_API_URL}/study/${id}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

// TODO StudyGroup의 정보를 업데이트 하는 코드
export const updateStudyInfo = async (
  id: number,
  accessToken: string | null,
  data: any
) => {
  try {
    // accessToken이 null인 경우 alert를 띄우고 함수를 종료한다.
    if (!accessToken) {
      alert('권한이 없습니다.')
    }

    // 아직 권한부여에 대한 기능이 구현되지 않아서 일단 주석으로 남겨둠
    // if (!isAuthorized) {
    //   alert('권한이 없습니다.');
    //   return;
    // }


    // update 기능은 patch 요청으로 수행되며
    // 인수로는 엔드포인트URL , 헤더(accessToken) , 데이터를 받는다.
    const response = await axios.patch(`${import.meta.env.VITE_APP_API_URL}/study/${id}`, {headers : {Authorization: `Bearer ${accessToken}`}}, data);
    return response.data;
  } catch (error) {
    console.error('Error updating study info:', error);
    throw error;
  }
};

// TODO StudyGroup의 정보를 삭제하는 코드
export const deleteStudyInfo = async (id: number, accessToken: string | null) => {
  try {
    // accessToken이 null인 경우 alert를 띄우고 함수를 종료한다.
    if (!accessToken) {
      alert('권한이 없습니다.');
      return;
    }

    // 아직 권한부여에 대한 기능이 구현되지 않아서 일단 주석으로 남겨둠
    // if (!isAuthorized) {
    //   alert('권한이 없습니다.');
    //   return;
    // }

    const response = await axios.delete(
      `${import.meta.env.VITE_APP_API_URL}/study/${id}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error('Error deleting study info:', error);
    throw error;
  }
};