export type NotificationType="JOB_CREATED" | "JOB_UPDATED" | "JOB_DELETED";

export type Notification = {

    id: string;
    userId:string;
    notificationType: NotificationType;
    title:string;
    message?: string | null;
    jobId:string;
    read:boolean;
    readAt:string;

}