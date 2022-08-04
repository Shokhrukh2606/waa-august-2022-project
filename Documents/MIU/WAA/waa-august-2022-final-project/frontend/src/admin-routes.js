
// @mui material components
import Icon from "@mui/material/Icon";
import Avatars from "layouts/sections/elements/avatars";
import Admin from "pages/Admin";
import { Outlet } from "react-router-dom";

const routes = [
    {
        icon: <Icon>view_day</Icon>,
        name: "Students",
        route: "students",
        component: <>Students</>,
        key: "admin1"
    },
    {
        icon: <Icon>view_day</Icon>,
        name: "Profile",
        route:"/admin",
        index: true,
        component: <Admin />,
        key: "admin2"
    }

];

export default routes;
