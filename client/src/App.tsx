import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "semantic-ui-css/semantic.min.css";
import { Container } from "semantic-ui-react";

import Header from "./componets/header/Header";
import Login from "./pages/Login";
import Routes from "./Routes";

const App: React.FC = () => {
  const history = useHistory();

  const [user, getUser] = useState(false);

  useEffect(() => {
    if (localStorage.getItem("user")) {
      getUser(true);
    } else {
      // alert("다시 로그인 해주세요");
      getUser(false);
    }
  }, [user]);

  const pageOpen = user ? (
    <React.Fragment>
      <Container>
        <Header />
        <Switch>
          {Routes.map((route) => {
            return (
              <Route key={route.path} path={route.path} exact={true}>
                <route.page />
              </Route>
            );
          })}
        </Switch>
      </Container>
    </React.Fragment>
  ) : (
    <React.Fragment>
      <Login />
    </React.Fragment>
  );

  return (
    <React.Fragment>
      <BrowserRouter>{pageOpen}</BrowserRouter>
    </React.Fragment>
  );
};

export default App;
