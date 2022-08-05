import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

export const fetchPageData = createAsyncThunk(
    "paginData/fetchAll",
    async (url) => {
        const response = await axios.get(url);
        return response.data
    }
)
const initialState = {
    data: [],
    meta: {},
    state: "idle"
}
const pageDataSlice = createSlice({
    name: "pageData",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchPageData.pending, (state) => {
                state.state = "pending"
            })
            .addCase(fetchPageData.fulfilled, (state, action) => {
                state.state = "succeeded"
                state.data = action.payload
            })
            .addCase(fetchPageData.rejected, (state, action) => {
                state.error = action.error.message
                state.state = "failed"
            })
    }
})
export default pageDataSlice.reducer