export const setAccessToken = (accessToken: string): void => {
  localStorage.setItem("accessToken", accessToken);
};

export const setRefreshToken = (refreshToken: string): void => {
  localStorage.setItem("refreshToken", refreshToken);
};

export const getAccessToken = (): string | null => {
  return localStorage.getItem("accessToken");
};

export const getRefreshToken = (): string | null => {
  return localStorage.getItem("refreshToken");
};

export const removeTokens = (): void => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
};
