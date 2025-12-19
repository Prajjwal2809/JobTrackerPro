import { useEffect, useMemo, useState } from "react";
import { Box, Button, Paper, TextField, Typography } from "@mui/material";
import AppShell from "../components/AppShell";
import KanbanBoard from "../components/KanbanBoard";
import { createJob, deleteJob, getJobs, updateJob, updateJobStatus } from "../api/api";
import type { Job, JobStatus } from "../types/job";
import JobModal from "../components/JobModal";



export default function Board() {

    const [jobs, setJobs] = useState<Job[]>([]);
    const [q, setQ] = useState("");

    const [modalOpen, setModalOpen] = useState(false);
    const [mode, setMode] = useState<"CREATE" | "EDIT">("CREATE");
    const [selectedJob, setSelectedJob] = useState<Job | null>(null);
    const [saving, setSaving] = useState(false);
    const [deleting, setDeleting] = useState(false); 

    function refresh(){
      getJobs().then(setJobs).catch(console.error);

    }


    useEffect(() => {
       refresh()
    }, []);

    const filtered=useMemo(() => {    
        const query = q.trim().toLowerCase();

      if (!query) return jobs;

      return jobs.filter((job) => {
        return(
          job.company.toLowerCase().includes(query) ||
          job.title.toLowerCase().includes(query)
        );
      });
    }, [jobs, q]);

    function openCreate(){
        setMode("CREATE");
        setSelectedJob(null);
        setModalOpen(true);
    }

    function openEdit(job:Job){
        setMode("EDIT");
        setSelectedJob(job);
        setModalOpen(true);
    }

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

    function onSave(formData: any) {
      setSaving(true);

      const jobId = selectedJob?.id;
      const prevStatus = selectedJob?.status;
      const nextStatus = formData.status as JobStatus;

      const baseAction =
        mode === "CREATE"
          ? createJob(formData)
          : updateJob(jobId!, {
              company: formData.company,
              title: formData.title,
              locationType: formData.locationType,
              location: formData.location,
              appliedDate: formData.appliedDate,
              source: formData.source,
              notes: formData.notes,
            });

      const statusAction =
        mode === "EDIT" && nextStatus !== prevStatus
          ? updateJobStatus(jobId!, nextStatus)
          : Promise.resolve();

      Promise.all([baseAction, statusAction])
        .then(() => {
          setModalOpen(false);
          refresh();
        })
        .catch(console.error)
        .finally(() => setSaving(false));
  }
    function onDelete(){

      if(!selectedJob) return;
      setDeleting(true);
      deleteJob(selectedJob.id)
      .then(()=>{
        setModalOpen(false);
        refresh();
      })
      .catch(console.error)
      .finally(()=>{
        setDeleting(false);
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
          <Box
            sx={{
              display: "flex",
              gap: 1.5,
              width: { xs: "100%", sm: "auto" },
            }}
          >
          <TextField
            placeholder="Search company or role…"
            value={q}
            onChange={(e) => setQ(e.target.value)}
            sx={{ width: { xs: "100%", sm: 320 } }}
          />
          <Button variant="contained" onClick={openCreate}> Add Job </Button>
          </Box>
        </Box>
      </Paper>

      <KanbanBoard jobs={filtered} onMove={onMove} onCardClick={openEdit} />

      <JobModal
        open={modalOpen}
        mode={mode}
        job={selectedJob}
        onClose={() => setModalOpen(false)}
        onSave={onSave}
        onDelete={mode==="EDIT" ? onDelete : undefined}
        saving={saving}
        deleting={deleting}
      />
    </AppShell>
    
  );
}