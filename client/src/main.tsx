import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { ProtectedRoutes} from "./routes/ProtectedRoutes";
import { CssBaseline, ThemeProvider } from "@mui/material";
import { theme } from "./theme/theme";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Board from "./pages/Board";
import Analytics from "./pages/Analytics";

const router = createBrowserRouter([
  
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    element: <ProtectedRoutes />,
    children: [
       { 
        path: "/", 
        element: <Board /> 
      },
      { 
        path: "/board", 
        element: <Board /> 
      },
      {
        path: "/analytics",
        element: <Analytics />,
      }
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
    <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
