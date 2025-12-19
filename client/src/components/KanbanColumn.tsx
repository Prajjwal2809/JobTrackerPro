import { useDroppable } from "@dnd-kit/core";
import type { Job, JobStatus } from "../types/job";
import { Box, Paper, Typography } from "@mui/material";
import JobCard from "./JobCard";



export default function KanbanColumn({
    status,
    title,
    jobs,
    onCardClick,
} : {
    status: JobStatus;
    title: string;
    jobs: Job[];
    onCardClick?: (job:Job) => void;
}) {
   
    const {setNodeRef, isOver} = useDroppable({id: status});

    return(
        <Paper
      ref={setNodeRef}
      sx={{
        p: 2,
        minHeight: 520,
        bgcolor: isOver ? "rgba(0,0,0,0.03)" : "background.paper",
      }}
    >
      <Box sx={{ display: "flex", alignItems: "baseline", justifyContent: "space-between", mb: 2 }}>
        <Typography variant="subtitle1" sx={{ fontWeight: 900 }}>{title}</Typography>
        <Typography variant="caption" color="text.secondary">{jobs.length}</Typography>
      </Box>

      <Box sx={{ display: "flex", flexDirection: "column", gap: 1.5 }}>
        {jobs.map((job) => (
          <JobCard key={job.id} job={job} onClick={() => onCardClick?.(job)} />
        ))}

        {jobs.length === 0 && (
          <Typography variant="body2" color="text.secondary">
            Drag jobs here
          </Typography>
        )}
      </Box>
    </Paper>

    );
}