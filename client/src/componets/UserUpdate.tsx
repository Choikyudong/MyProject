import React, { useState } from "react";

import UpdateForm from "../domain/UpdateForm";
import UserService from "../service/UserService";

const UserUpdate = () => {
  const UpdateFormState = {
    username: "",
    password: "",
    name: "",
  };

  const [getUpdateForm, setUpdateForm] = useState<UpdateForm>(UpdateFormState);
  const [processing, setProcessing] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>("");

  const changeValue = (data: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = data.target;
    setUpdateForm({
      ...getUpdateForm,
      [name]: value,
    });
  };

  const doSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      UserService.updateAxios(getUpdateForm);
    } catch (error) {
      console.log(error);
      setErrorMsg("이게뭐임;");
    }
  };

  return (
    <>
      <form onSubmit={doSubmit}>
        <input
          name="password"
          type="password"
          value={getUpdateForm.password}
          placeholder="비밀번호"
          onChange={changeValue}
        />
        <input
          name="name"
          type="text"
          value={getUpdateForm.name}
          placeholder="이름"
          onChange={changeValue}
        />
        <button type="submit">수정</button>
      </form>
    </>
  );
};

export default UserUpdate;
