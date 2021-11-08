import SignInForm from "../domain/SignInForm";
import SignUpForm from "../domain/SignUpForm";
import TokenResponse from "../domain/TokenResponse";
import UpdateForm from "../domain/UpdateForm";

import Axios from "../util/Axios";

const signinAxios = (SignInForm: SignInForm) => {
  return Axios.NormalAxios
  .post<TokenResponse>("/login", SignInForm)
  .then((res) => {
    if (res.data.token) {
      localStorage.setItem("user", JSON.stringify(res.data));
    }
    return res;
  }).catch((error) => {
    console.log(error());
  })
};

const logut = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user") || "");
};

const signupAxios = (SignUpForm: SignUpForm) => {
  Axios.NormalAxios.post("/signup", SignUpForm)
    .then((res) => {
      console.log(res);
    }).catch((error) => {
        console.log(error);
    })
};

const updateAxios = (UpdateForm: UpdateForm) => {
  Axios.AuthAxios.post("/update", UpdateForm)
    .then((res) => {
      console.log(res);
    }).catch((error) => {
        console.log(error);
    })
};

export default {
  signinAxios,
  logut,
  getCurrentUser,
  signupAxios,
  updateAxios,
};
