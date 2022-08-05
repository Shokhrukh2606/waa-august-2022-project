import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";
import userReducer from "./userSlice";
import middlewares from "./middlewares";

const store = configureStore({
  reducer: {
    auth: authReducer,
    user: userReducer,
  },
  middleware: middlewares
});

export default store;
