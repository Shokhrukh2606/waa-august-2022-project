import * as React from "react";
import { useDispatch, useSelector } from "react-redux";
import { loginRequest } from "store/authSlice";
import { logoutSuccess } from "store/authSlice";
import { loginSuccess } from "store/authSlice";

export function useAuth() {
    const auth = useSelector(state => state.auth);
    const dispatch = useDispatch()
    return {
        loggedIn: auth.loggedIn,
        login(data) {
            return new Promise((res) => {
                dispatch(loginRequest())
                dispatch(loginSuccess({ token: "12345" }))
                res();
            });
        },
        logout() {
            return new Promise((res) => {
                dispatch(logoutSuccess())
                res();
            });
        },
    };
}

