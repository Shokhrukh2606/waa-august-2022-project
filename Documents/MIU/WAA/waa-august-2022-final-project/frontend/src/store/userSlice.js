import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    data: null,
    error: false,
    loading: false
}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        userRequest: (state) => {
            state.loading = true
            state.error = false
            state.data = null
        },
        userSuccess: (state, { payload }) => {
            state.loading = false
            state.error = false
            state.data = payload.data
        },
        userFailure: (state, { payload }) => {
            state.loading = false
            state.error = payload.error
            state.data = null
        }
    },
})

// Action creators are generated for each case reducer function
export const { userRequest, userSuccess, userFailure } = userSlice.actions

export default userSlice.reducer