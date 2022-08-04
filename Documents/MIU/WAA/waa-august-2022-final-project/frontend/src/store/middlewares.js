import { createLogger } from "redux-logger";

const middlewares = [];
const loggerMiddleware = createLogger();
middlewares.push(loggerMiddleware);

export default middlewares;
