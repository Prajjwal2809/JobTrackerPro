import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import { ProtectedRoutes} from "./routes/ProtectedRoutes";

const router = createBrowserRouter([
  
  {
    element: <ProtectedRoutes />,
    children: [
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
