import { atom } from "recoil";
//import { recoilPersist } from "recoil-persist";

//const { persistAtom } = recoilPersist();

export const RenderingState = atom({
  key: "renderingState",
  default: false,
});
