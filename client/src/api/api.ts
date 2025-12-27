import type { Job, JobStatus } from '../types/job';
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

export const updateJobStatus = (jobId: string, status: JobStatus) => {
    return apiClient
    .patch(`api/v1/jobs/${jobId}/status`, { status })
    .then((response) => response.data);
}

export const createJob = (data: {
    company: string;
    title: string;
    status: JobStatus;
    locationType: "REMOTE" | "HYBRID" | "ONSITE";
    location?: string | null;
    appliedDate?: string;
    source?: string | null;
    followUpAt?: string | null;
    notes?: string | null;
}) => {
    return apiClient
    .post("api/v1/jobs", data)
    .then((response) => response.data as Job);
}

export const deleteJob = (jobId: string) => {
    return apiClient
    .delete(`api/v1/jobs/${jobId}`)
    .then((response) => response.data);
}

export const updateJob = (jobId: string, data: {
    company?: string;
    title?: string;
    status?: JobStatus;
    locationType?: "REMOTE" | "HYBRID" | "ONSITE";
    location?: string | null;
    appliedDate?: string;
    source?: string | null;
    notes?: string | null;
    followUpAt?: string;
   
}) => {
    return apiClient
    .put(`api/v1/jobs/${jobId}`, data)
    .then((response) => response.data as Job);
}

export function getAnalyticsSummary() {
  return apiClient.get("/api/v1/analytics/summary").then((res) => res.data);
}

export function getAnalyticsTimeline() {
  return apiClient.get("/api/v1/analytics/timeline").then((res) => res.data);
}
