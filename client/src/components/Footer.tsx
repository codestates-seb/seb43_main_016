import styled from "styled-components";

const Footer = () => {
  return (
    <FooterContainer>
      <p>Created by EduSync Team.</p>
    </FooterContainer>
  );
};

const FooterContainer = styled.div`
  width: 100%;
  height: 100px;
  background-color: #e9e9e9;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  p {
    font-size: 14px;
    font-weight: 300;
    color: #666;
  }
`;

export default Footer;
