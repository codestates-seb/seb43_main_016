import { rest } from "msw";

interface LoginDto {
  email: string;
  password: string;
}

export const handlers = [

  rest.post<LoginDto>("/members/login", (req, res, ctx) => {
    const { email, password } = req.body;

    // 로그인 로직 처리
    if (email === "test1@gmail.com" && password === "test") {
      // 로그인 성공한 경우 액세스 토큰 발급
      const accessToken = "mock-access-token";

      // 액세스 토큰을 응답에 포함하여 반환
      return res(
        ctx.status(200),
        ctx.json({
          accessToken: accessToken,
        })
      );
    } else {
      // 로그인 실패한 경우 오류 응답
      return res(ctx.status(401), ctx.json({ message: "Invalid credentials" }));
    }
  }),
];
