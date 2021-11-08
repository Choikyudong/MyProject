import { useEffect } from "react";
import { useHistory } from "react-router";

import Navation from "./Navation";

const Header: React.FC = () => {
  const history = useHistory();

  useEffect(() => {
    console.log(history);
    history.push("/login");
  });

  return (
    <header>
      <Navation />
    </header>
  );
};

export default Header;
