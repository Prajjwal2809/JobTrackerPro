export type JobStatus = "APPLIED" | "INTERVIEW" | "OFFER" | "REJECTED";
export type LocationType = "REMOTE" | "HYBRID" | "ONSITE";

export type Job = {
  id: string;
  company: string;
  title: string;
  status: JobStatus;
  locationType: LocationType;
  location?: string | null;
  appliedDate?: string;
  source?: string | null;
  notes?: string | null;
};
