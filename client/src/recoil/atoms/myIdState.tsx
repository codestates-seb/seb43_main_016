import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const myIdState = atom({
  key: "myIdState",
  default: 0,
  effects_UNSTABLE: [persistAtom],
});
