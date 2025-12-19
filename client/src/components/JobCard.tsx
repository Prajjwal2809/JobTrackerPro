import { useDraggable } from "@dnd-kit/core";
import { Box, Chip, Typography } from "@mui/material";
import BusinessIcon from "@mui/icons-material/Business";
import type { Job } from "../types/job";

export default function JobCard({ job }: { job: Job }) {
    
    const {setNodeRef, listeners, attributes, transform, isDragging} = useDraggable({id: job.id});
    const style= transform ? {
        transform: `translate3d(${transform.x}px, ${transform.y}px, 0)`,
        opacity: isDragging ? 0.5 : 1,
        cursor: "grab"
    } : undefined;
    return (
       <Box
      ref={setNodeRef}
      {...listeners}
      {...attributes}
      sx={{
        p: 2,
        border: "1px solid #EEF0F4",
        borderRadius: 2,
        bgcolor: "background.paper",
        cursor: "grab",
        boxShadow: isDragging ? 4 : 0,
        opacity: isDragging ? 0.7 : 1,
      }}
      style={style as any}
    >
      <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 0.5 }}>
        <BusinessIcon sx={{ fontSize: 18, opacity: 0.7 }} />
        <Typography fontWeight={900}>{job.company}</Typography>
      </Box>
      <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
        {job.title}
      </Typography>

      <Chip size="small" label={job.locationType} variant="outlined" />
    </Box>
    );
}