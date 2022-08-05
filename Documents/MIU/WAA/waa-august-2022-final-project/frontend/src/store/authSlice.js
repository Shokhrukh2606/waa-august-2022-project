import { createSlice } from '@reduxjs/toolkit'

const token = localStorage.getItem("user_token");
const initialState = {
    token: token||"good",
    loggedIn: token ? true : false,
    error: false,
    loading: false
}

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        loginRequest: (state) => {
            state.loading = true
            state.error = false
            state.loggedIn = false
        },
        loginSuccess: (state, { payload }) => {
            state.token = payload.token
            state.loading = false
            state.error = false
            state.loggedIn = true
        },
        loginFailure: (state, { payload }) => {
            state.error = payload.message
            state.loggedIn = false
            state.loading = false
        },
        logoutSuccess: (state) => {
            state.error = false
            state.loggedIn = false
            state.loading = false
            state.token = ""
        }
    },
})

// Action creators are generated for each case reducer function
export const { loginRequest, loginSuccess, loginFailure, logoutSuccess } = authSlice.actions

export default authSlice.reducer