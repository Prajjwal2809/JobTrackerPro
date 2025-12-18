import { apiClient } from './apiClient';


export const login = (data:{email: string, password: string}) => {
  
    return apiClient
    .post("api/v1/auth/login",data)
    .then((response) => response.data);
}

export const register = (data:{name: string, email: string, password: string}) => {
    return apiClient
    .post("api/v1/auth/register",data)
    .then((response) => response.data);
}


export const getJobs = () => {
    return apiClient
    .get("api/v1/jobs")
    .then((response) => response.data?.content ?? response.data);
}

export const updateJobStatus = (jobId: string, status: string) => {
    return apiClient
    .patch(`api/v1/jobs/${jobId}`, { status })
    .then((response) => response.data);
}