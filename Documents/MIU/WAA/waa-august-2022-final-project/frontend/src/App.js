/**
=========================================================
* Material Kit 2 React - v2.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-kit-react
* Copyright 2021 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

import { useEffect } from "react";

// react-router components
import { Routes, Route, Navigate, useLocation } from "react-router-dom";

// @mui material components
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";

// Material Kit 2 React themes
import theme from "assets/theme";
import Presentation from "layouts/pages/presentation";

// Material Kit 2 React routes
import routes from "routes";
import admin_routes from "admin-routes";
import student_routes from "student-routes";
import faculty_routes from "faculty-routes";
import DefaultLayout from "layouts/admin";
import SignOut from "pages/LandingPages/SignOut";

export default function App() {
  const { pathname } = useLocation();

  // Setting page scroll to 0 when changing the route
  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
  }, [pathname]);

  const getRoutes = (allRoutes) =>
    allRoutes.map((route) => {
      if (route.collapse) {
        return getRoutes(route.collapse);
      }

      if (route.index) {
        return <Route index element={route.component} key={route.name} />;
      } else if (route.route) {
        return <Route path={route.route} element={route.component} key={route.name} />;
      }

      return null;
    });

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Routes>
        {getRoutes(routes)}
        <Route path="/presentation" element={<Presentation />} />
        <Route path="/admin" element={<DefaultLayout />}>
          {getRoutes(admin_routes)}
        </Route>
        <Route path="/faculty" element={<DefaultLayout />}>
          {getRoutes(faculty_routes)}
        </Route>
        <Route path="/student" element={<DefaultLayout />}>
          {getRoutes(student_routes)}
        </Route>
        <Route path="/authentication/sign-out" element={<SignOut />} />
        <Route path="*" element={<Navigate to="/presentation" />} />
      </Routes>
    </ThemeProvider>
  );
}
