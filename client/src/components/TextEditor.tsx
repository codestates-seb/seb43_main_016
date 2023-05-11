import styled from "styled-components";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import { useState } from "react";

type Props = {
  onFocus: () => void;
  onBlur: () => void;
  handleContentChange: (content: string) => void;
};

function TextEditor({ onFocus, onBlur, handleContentChange }: Props) {
  const [text, setText] = useState("");

  const handleOnChange = (_event: any, editor: ClassicEditor) => {
    const data = editor.getData();
    const plainText = data.replace(/(<([^>]+)>)/gi, "");
    setText(data);
    // console.log("TextEditor handleChange:", data);
    handleContentChange(plainText);
  };

  return (
    <EditorContainer>
      <Editor>
        <CKEditor
          editor={ClassicEditor}
          data={text}
          // config={{
          //   contentCss: "/path/to/custom.css",
          // }}
          onChange={handleOnChange}
          onFocus={onFocus}
          onBlur={onBlur}
        />
      </Editor>
    </EditorContainer>
  );
}

export default TextEditor;

const EditorContainer = styled.div`
  width: 720px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Editor = styled.div`
  width: 100%;

  &:focus-within {
    box-shadow: 0 0 0 4px rgba(0, 149, 255, 0.15);
    border: 1px solid #59a4de;
    outline: none;
  }

  & {
    border: 1px solid hsl(210, 8%, 85%);
    border-radius: 3px;
    background-color: white;
  }

  .ck-icon:focus {
    border: none;
  }

  .ck-editor__main {
    width: 100%;
    margin: 0;
  }

  .ck-editor__editable {
    min-height: 210px;
    width: 100%;
  }

  .ck.ck-editor__editable:not(.ck-editor__nested-editable):focus {
    border: none;
    box-shadow: none;
  }

  .ck.ck-toolbar .ck.ck-toolbar__separator {
    background-color: white;
  }

  .ck {
    border: none;
  }
`;
