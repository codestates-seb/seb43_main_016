import { selector } from "recoil";
import { myIdState } from "../atoms/MyIdState";
import { getAccessToken, getRefreshToken } from "../../pages/utils/Auth";

export const isLoggedInSelector = selector({
  key: "isLoggedInSelector",
  get: ({ get }) => {
    const myId = get(myIdState);
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();
    return accessToken && refreshToken && myId > 0;
  },
});
