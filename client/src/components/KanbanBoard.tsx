import { Box } from "@mui/material";
import type { Job, JobStatus } from "../types/job";
import { DndContext, type DragEndEvent } from "@dnd-kit/core";
import KanbanColumn from "./KanbanColumn";


const COLUMNS: { key: JobStatus; title: string }[] = [
  { key: "APPLIED", title: "Applied" },
  { key: "INTERVIEW", title: "Interview" },
  { key: "OFFER", title: "Offer" },
  { key: "REJECTED", title: "Rejected" },
];


export default function KanbanBoard ({
    jobs,
    onMove,
    onCardClick,
}:{
    jobs: Job[];
    onMove: (jobId: string, newStatus: JobStatus) => void;
    onCardClick?: (job:Job) => void;
}){

    function handleDragEnd(e:DragEndEvent)
    {
        const jobId=e.active.id as string;
        const overId=e.over?.id as string | null;
        if(!overId) return;

        const newStatus=overId as JobStatus;

        const job=jobs.find((j)=>j.id===jobId);
        if(!job) return;

        job.status!==newStatus ? onMove(jobId,newStatus) : null;

    }

    return (
        <DndContext onDragEnd={handleDragEnd}>
      <Box sx={{ display: "grid", gridTemplateColumns: { xs: "1fr", md: "repeat(4, 1fr)" }, gap: 2 }}>
        {COLUMNS.map((c) => (
          <KanbanColumn
            key={c.key}
            status={c.key}
            title={c.title}
            jobs={jobs.filter((j) => j.status === c.key)}
            onCardClick={onCardClick}
          />
        ))}
      </Box>
    </DndContext>
    );
}