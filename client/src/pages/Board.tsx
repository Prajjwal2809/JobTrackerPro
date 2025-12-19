import { useEffect, useMemo, useState } from "react";
import { Box, Paper, TextField, Typography } from "@mui/material";
import AppShell from "../components/AppShell";
import KanbanBoard from "../components/KanbanBoard";
import { getJobs, updateJobStatus } from "../api/api";
import type { Job, JobStatus } from "../types/job";



export default function Board() {

    const [jobs, setJobs] = useState<Job[]>([]);
    const [q, setQ] = useState("");

    useEffect(() => {
        getJobs().then(setJobs).catch(console.error);
    }, []);

    const filtered=useMemo(() => {    
        const query = q.trim().toLowerCase();

    if (!query) return jobs;

    return jobs.filter((job) => {
            job.company.toLowerCase().includes(query) ||
            job.title.toLowerCase().includes(query)
    });
    }, [jobs, q]);

    function onMove(jobId: string, newStatus: JobStatus) {

        setJobs((prevJobs) =>
            prevJobs.map((job) =>
                job.id === jobId ? { ...job, status: newStatus } : job
            )
     );

        updateJobStatus(jobId, newStatus).catch(() => {
            getJobs().then(setJobs).catch(console.error);
        });
    } 

  return (
     <AppShell>
      <Paper sx={{ p: 3, mb: 2 }}>
        <Box sx={{ display: "flex", alignItems: "center", gap: 2, justifyContent: "space-between", flexWrap: "wrap" }}>
          <Box>
            <Typography variant="h5">Pipeline</Typography>
            <Typography color="text.secondary">Drag cards to update status instantly.</Typography>
          </Box>
          <TextField
            placeholder="Search company or role…"
            value={q}
            onChange={(e) => setQ(e.target.value)}
            sx={{ width: { xs: "100%", sm: 320 } }}
          />
        </Box>
      </Paper>

      <KanbanBoard jobs={filtered} onMove={onMove} />
    </AppShell>
    
  );
}