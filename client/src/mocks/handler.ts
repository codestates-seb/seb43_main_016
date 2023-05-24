import { RestRequest, rest } from "msw";
import { MemberPasswordCheckDto } from "../apis/MemberApi";
import { getStudyGroupInfo } from "../apis/StudyGroupApi";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

const isLoggedIn = useRecoilValue(LogInState)

interface LoginRequestBody {
  email: string;
  password: string;
}

interface LoginResponse {
  data: string;
}

interface UserInfo {
  uuid: string;
  email: string;
  profileImage: string;
  nickName: string;
  aboutMe: string;
  withMe: string;
  memberStatus: string;
  roles: string[];
}

interface data {
  nickName: string;
  password: string;
}

interface profileDetailData {
  aboutMe: string;
  withMe: string;
}

interface passwordData {
  password: string;
}

export const handlers = [
  // TODO refreshToken 요청 테스트
  rest.post(`${import.meta.env.VITE_APP_API_URL}/refresh`, (req, res, ctx) => {
    // 요청 헤더에서 refresh 토큰을 추출합니다.
    const refreshToken = req.headers.get('refresh');

    // refresh 토큰이 유효한 경우 가짜 액세스 토큰을 생성하여 응답합니다.
    if (refreshToken === '유효한_리프레시_토큰') {
      const fakeAccessToken = '가짜_액세스_토큰';
      // 가짜 액세스 토큰을 응답 헤더의 authorization에 설정합니다.
      return res(
        ctx.set('authorization', fakeAccessToken),
        ctx.status(200)
      );
    }

    // refresh 토큰이 유효하지 않은 경우 401 Unauthorized 응답을 반환합니다.
    return res(
      ctx.status(401),
      ctx.json({ message: 'Unauthorized' })
    );
  }),

  // TODO 로그인 기능 테스트
  rest.post<LoginRequestBody, LoginResponse>(
    `${import.meta.env.VITE_APP_API_URL}/members/login`,
    (req, res, ctx) => {
      const { email, password } = req.body;

      // 로그인 정보 확인 로직

      if (email === "user1@gmail.com" && password === "user1") {
        const accessToken = "your-access-token"; // 모의 액세스 토큰 값

        return res(
          ctx.status(200),
          ctx.set("Authorization", `Bearer ${accessToken}`),
          ctx.json({ message: "로그인 성공", data: accessToken })
        );
      } else {
        return res(ctx.status(401), ctx.json({ error: "인증 실패" }));
      }
    }
  ),

  // TODO 회원정보 요청 테스트
  rest.get(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");

      // 액세스 토큰과 일치하는 유저 정보를 반환
      if (accessToken === "Bearer your-access-token") {
        // 모의 액세스 토큰 값으로 수정

        const userInfo: UserInfo = {
          uuid: "b1c452bf-4b2f-4ea5-89f3-a241795495ba",
          email: "test5555@gmail.com",
          profileImage: "https://avatars.githubusercontent.com/u/120456261?v=4",
          nickName: "테스트5555",
          aboutMe: "test5555_aboutMe",
          withMe: "test5555_aboutMe",
          memberStatus: "MEMBER_ACTIVE",
          roles: ["LEADER"],
        };

        return res(ctx.status(200), ctx.json(userInfo));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO 회원정보 수정 테스트
  rest.patch(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
        // 모의 액세스 토큰 값으로 수정
        const data: data = {
          nickName: "테스트5550",
          password: "user5550",
        };

        return res(ctx.status(200), ctx.json(data));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO 회원정보 수정 테스트
  rest.patch(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
        // 모의 액세스 토큰 값으로 수정
        const data: data = {
          nickName: "테스트5550",
          password: "user5550",
        };

        return res(ctx.status(200), ctx.json(data));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO 비밀번호 검증 테스트
  rest.post(
    `${import.meta.env.VITE_APP_API_URL}/members/password`,
    (req: RestRequest<MemberPasswordCheckDto>, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");

      const requestPassword = req.body?.password;
      const handlerPassword = "user5555";

      if (accessToken === "Bearer your-access-token") {
        if (requestPassword === handlerPassword) {
          const data: passwordData = {
            password: "user5555",
          };
          return res(ctx.status(200), ctx.json(data));
        } else {
          return res(ctx.status(400), ctx.json({ error: "Invalid password" }));
        }
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO 자기소개, 함께하고 싶은 동료 테스트
  rest.patch(
    `${import.meta.env.VITE_APP_API_URL}/members/detail`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
        // 모의 액세스 토큰 값으로 수정
        const data: profileDetailData = {
          aboutMe: "테스트5550",
          withMe: "테스트5550",
        };
        return res(ctx.status(200), ctx.json(data));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO DELETE 테스트
  rest.delete(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers
        .get("Authorization")
        ?.replace("Bearer ", "");
      if (accessToken === "Bearer your-access-token") {
        return res(
          ctx.status(200),
          ctx.json({ message: "회원 정보가 삭제되었습니다." })
        );
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  // TODO 스터디 그룹 조회 테스트
  rest.get(`${import.meta.env.VITE_APP_API_URL}/studygroups/:id`, async (req, res, ctx) => {
    try {
      const studyGroupId = Number(req.params.id);
      const studyInfo = await getStudyGroupInfo(studyGroupId, isLoggedIn);
      console.log(studyInfo);
      return res(ctx.json(studyInfo));
    } catch (error) {
      return res(ctx.status(403), ctx.json({ message: "권한이 없습니다" }));
    }
  }),

  // TODO 스터디 그룹 수정 테스트
];
