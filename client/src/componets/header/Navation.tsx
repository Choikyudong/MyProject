import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Menu } from "semantic-ui-react";
import UserService from "../../service/UserService";

const Navation: React.FC = () => {
  const [activeItem, setActiveItem] = useState("");

  const handleItemClick = (value: string): any => {
    setActiveItem(value);
    if (value === "logout") {
      UserService.logut();
    }
  };

  return (
    <div>
      <Menu pointing secondary>
        <Link to="/">
          <Menu.Item
            name="home"
            active={activeItem === "home"}
            onClick={() => handleItemClick("home")}
          />
        </Link>
        <Link to="/MyStory">
          <Menu.Item
            name="MyStory"
            active={activeItem === "MyStory"}
            onClick={() => handleItemClick("MyStory")}
          />
        </Link>

        <Menu.Menu position="right">
          <Menu.Item
            name="logout"
            active={activeItem === "logout"}
            onClick={() => handleItemClick("logout")}
          />
        </Menu.Menu>
      </Menu>
    </div>
  );
};

export default Navation;
