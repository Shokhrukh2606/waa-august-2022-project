import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

export default function RequireAuth({ children, role }) {
    const { loggedIn } = useSelector(state => state.auth);
    return loggedIn  === true ? children : <Navigate to="/login" replace />;
}