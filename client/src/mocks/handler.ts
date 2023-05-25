import { RestRequest, rest } from "msw";
import { MemberPasswordCheckDto } from "../apis/MemberApi";
import { getStudyGroupInfo } from "../apis/StudyGroupApi";
import { useRecoilValue } from "recoil";
import { LogInState } from "../recoil/atoms/LogInState";

const isLoggedIn = useRecoilValue(LogInState);

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

interface Data {
  nickName: string;
  password: string;
}

interface ProfileDetailData {
  aboutMe: string;
  withMe: string;
}

interface PasswordData {
  password: string;
}

export const handlers = [
  rest.post(`${import.meta.env.VITE_APP_API_URL}/refresh`, (req, res, ctx) => {
    const refreshToken = req.headers.get('refresh');

    if (refreshToken === '유효한_리프레시_토큰') {
      const fakeAccessToken = '가짜_액세스_토큰';
      return res(
        ctx.set('authorization', fakeAccessToken),
        ctx.status(200)
      );
    }

    return res(
      ctx.status(401),
      ctx.json({ message: 'Unauthorized' })
    );
  }),

  rest.post<LoginRequestBody, LoginResponse>(
    `${import.meta.env.VITE_APP_API_URL}/members/login`,
    (req, res, ctx) => {
      const { email, password } = req.body;

      if (email === "user1@gmail.com" && password === "user1") {
        const accessToken = "your-access-token";

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

  rest.get(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers.get("Authorization")?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
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

  rest.patch(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers.get("Authorization")?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
        const data: Data = {
          nickName: "테스트5550",
          password: "user5550",
        };

        return res(ctx.status(200), ctx.json(data));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  rest.post(
    `${import.meta.env.VITE_APP_API_URL}/members/password`,
    (req: RestRequest<MemberPasswordCheckDto>, res, ctx) => {
      const accessToken = req.headers.get("Authorization")?.replace("Bearer ", "");

      const requestPassword = req.body?.password;
      const handlerPassword = "user5555";

      if (accessToken === "Bearer your-access-token") {
        if (requestPassword === handlerPassword) {
          const data: PasswordData = {
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

  rest.patch(
    `${import.meta.env.VITE_APP_API_URL}/members/detail`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers.get("Authorization")?.replace("Bearer ", "");

      if (accessToken === "Bearer your-access-token") {
        const data: ProfileDetailData = {
          aboutMe: "테스트5550",
          withMe: "테스트5550",
        };
        return res(ctx.status(200), ctx.json(data));
      } else {
        return res(ctx.status(401), ctx.json({ error: "Unauthorized" }));
      }
    }
  ),

  rest.delete(
    `${import.meta.env.VITE_APP_API_URL}/members`,
    (req: RestRequest, res, ctx) => {
      const accessToken = req.headers.get("Authorization")?.replace("Bearer ", "");
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
];
