import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logoutSuccess } from "store/authSlice";
export default function SignOut() {
    const dispatch = useDispatch()
    const navigate = useNavigate()
    useEffect(() => {
        dispatch(logoutSuccess())
        navigate("/")
    }, []);
    return (
        <></>
    )
}