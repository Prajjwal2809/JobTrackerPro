
export type jobStatus="APPLIED" | "INTERVIEW" | "OFFER" | "REJECECTED"; 

export interface Job{
    id: string;
    title: string;
    company: string;
    locationType: "REMOTE" | "ONSITE" | "HYBRID";
    status: jobStatus;
    location?: string;
}

