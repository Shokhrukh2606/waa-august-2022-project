// @mui material components
import BaseLayout from "layouts/sections/components/BaseLayout";

import { Outlet, Route } from "react-router-dom";
import RequireAuth from "hocs/RequireAuth";


function AdminLayout({ children }) {
    const getRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getRoutes(route.collapse);
            }

            if (route.route) {
                return <Route exact path={`${route.route}`} element={route.component} key={route.key} />;
            }

            return null;
        });


    return (
        <RequireAuth role="admin">
            <BaseLayout
                title="Shohruh Toshniyozov"
                breadcrumb={[
                    { label: "Admin" },
                ]}
            >
                <Outlet />
            </BaseLayout>
        </RequireAuth>
    );
}

export default AdminLayout;
