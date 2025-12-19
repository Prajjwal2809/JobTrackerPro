import { useDraggable } from "@dnd-kit/core";
import { Box, Chip, IconButton, Typography } from "@mui/material";
import BusinessIcon from "@mui/icons-material/Business";
import DragIndicatorIcon from "@mui/icons-material/DragIndicator";
import type { Job } from "../types/job";

const STATUS_STRIP:Record<string,string>={
    APPLIED: "#0078D4",   
    INTERVIEW: "#7B61FF",  
    OFFER: "#107C10",     
    REJECTED: "#D13438",  
};

export default function JobCard({ job, onClick }: { job: Job, onClick?: () => void }) {
    
    const {setNodeRef, listeners, attributes, transform, isDragging} = useDraggable({id: job.id});
    const style= transform ? {
        transform: `translate3d(${transform.x}px, ${transform.y}px, 0)`,
        opacity: isDragging ? 0.5 : 1,
        cursor: "grab"
    } : undefined;
    return (
  <Box
    ref={setNodeRef}
    onClick={onClick}
    sx={{
      display: "flex",              
      alignItems: "stretch",
      p: 2,
      border: "1px solid #EEF0F4",
      borderRadius: 2,
      bgcolor: "background.paper",
      boxShadow: isDragging ? 4 : 0,
      opacity: isDragging ? 0.7 : 1,
      userSelect: "none",
      overflow: "hidden",           
      cursor: "grab",           
    }}
    style={style as any}
  >
    {/* ✅ Azure-style status strip */}
    <Box
      sx={{
        width: "6px",
        borderRadius: "6px",
        bgcolor: STATUS_STRIP[job.status] || "#9AA0A6",
        mr: 1.5,
      }}
    />

    <Box sx={{ flex: 1 }}>
      <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 0.5 }}>
        <IconButton
          size="small"
          {...listeners}
          {...attributes}
          sx={{ cursor: "grab" }}
          onClick={(e) => e.stopPropagation()} 
        >
          <DragIndicatorIcon fontSize="small" />
        </IconButton>

        <BusinessIcon sx={{ fontSize: 18, opacity: 0.7 }} />
        <Typography fontWeight={900}>{job.company}</Typography>
      </Box>

      <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
        {job.title}
      </Typography>

      <Chip size="small" label={job.locationType} variant="outlined" />
    </Box>
  </Box>
);
}