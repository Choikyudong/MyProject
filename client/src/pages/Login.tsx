import React, { useState } from "react";
import { useHistory } from "react-router";
import { Button, Divider, Form, Grid, Segment } from "semantic-ui-react";

import SignInForm from "../domain/SignInForm";
import SignUpForm from "../domain/SignUpForm";
import UserService from "../service/UserService";

const Login: React.FC = () => {
  const history = useHistory();

  const SignInFormState = {
    username: "",
    password: "",
  };

  const SignUpFormState = {
    username: "",
    password: "",
    name: "",
  };

  const [page, setPage]: [boolean, any] = useState<boolean>(true);
  const [getSignInForm, setSignInForm] = useState<SignInForm>(SignInFormState);
  const [getSignUpForm, setSignUpForm] = useState<SignUpForm>(SignUpFormState);
  const [processing, setProcessing] = useState(true);

  const loginValidation = () => {
    let regExp = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    if (getSignInForm.username && getSignInForm.username.search(regExp) !== -1) {
      setProcessing(true);
    } else {
      setProcessing(false);
    }
  }

  const signUpValidation = () => {
    let regExp = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    if (getSignUpForm.username && getSignUpForm.username.search(regExp) !== -1) {
      setProcessing(true);
    } else {
      setProcessing(false);
    }
  }

  const loginValue = (data: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = data.target;
    setSignInForm({
      ...getSignInForm,
      [name]: value,
    });
  };

  const singupValue = (data: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = data.target;
    setSignUpForm({
      ...getSignUpForm,
      [name]: value,
    });
  };

  const doLogin = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      loginValidation();
      if (processing) {
        UserService.signinAxios(getSignInForm).then((res) => {});
        alert("로그인성공");
        history.push("/");
      }
    } catch (error) {
      console.log(error);
      alert("에러");
    }
  };

  const doSignUp = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      signUpValidation();
      if (processing) {
        UserService.signupAxios(getSignUpForm);
        alert("회원가입성공");
        setPage(true);
      }
    } catch (error) {
      console.log(error);
      alert("에러");
    }
  };

  const getPage = page ? (
    <React.Fragment>
      <Segment placeholder>
        <Grid columns={2} relaxed="very" stackable>
          <Grid.Column>
            <Form onSubmit={doLogin}>
              <Form.Input
                icon="user"
                iconPosition="left"
                label="Username"
                placeholder="Username"
                value={getSignInForm.username}
                name="username"
                onChange={loginValue}
              />
              <Form.Input
                icon="lock"
                iconPosition="left"
                label="Password"
                type="password"
                placeholder="Password"
                value={getSignInForm.password}
                name="password"
                onChange={loginValue}
              />
              <Button content="Login" primary />
            </Form>
          </Grid.Column>

          <Grid.Column verticalAlign="middle">
            <Button
              onClick={() => setPage(false)}
              content="Sign up"
              icon="signup"
              size="big"
            />
          </Grid.Column>
        </Grid>

        <Divider vertical>Or</Divider>
      </Segment>
    </React.Fragment>
  ) : (
    <React.Fragment>
      <Segment placeholder>
        <Grid columns={2} relaxed="very" stackable>
          <Grid.Column>
            <Form onSubmit={doSignUp}>
              <Form.Input
                icon="user"
                iconPosition="left"
                label="Email"
                placeholder="email"
                value={getSignUpForm.username}
                name="username"
                onChange={singupValue}
              />
              <Form.Input
                icon="lock"
                iconPosition="left"
                label="Password"
                type="password"
                placeholder="Password"
                value={getSignUpForm.password}
                name="password"
                onChange={singupValue}
              />
              <Form.Input
                icon="lock"
                iconPosition="left"
                label="Name"
                type="text"
                placeholder="YourName"
                value={getSignUpForm.name}
                name="name"
                onChange={singupValue}
              />
              <Button content="SignUp" primary />
            </Form>
          </Grid.Column>

          <Grid.Column verticalAlign="middle">
            <Button
              onClick={() => setPage(true)}
              content="Login"
              icon="signup"
              size="big"
            />
          </Grid.Column>
        </Grid>

        <Divider vertical>Or</Divider>
      </Segment>
    </React.Fragment>
  );

  return <React.Fragment>{getPage}</React.Fragment>;
};

export default Login;
