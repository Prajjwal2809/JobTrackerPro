import {Navigate, Outlet} from "react-router-dom";
import {getToken} from "../utils/token";


export const ProtectedRoutes=()=>{
    const token=getToken();
    return token ? <Outlet/> : <Navigate to="/login" replace={true}/>;
}