export const validateEmptyInput = (text: any): boolean => {
  if (!text.hasOwnProperty("length")) {
    throw new Error("text is not a text?");
  }
  if (text.trim().length === 0) {
    return true;
  }
  return false;
};
