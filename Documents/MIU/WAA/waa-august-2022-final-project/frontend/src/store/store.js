import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./authSlice";
import userReducer from "./userSlice";
import pageDataReducer from "./pageDataSlice";
import middlewares from "./middlewares";

const store = configureStore({
  reducer: {
    auth: authReducer,
    user: userReducer,
    pageData: pageDataReducer
  },
  middleware: middlewares
});

export default store;
