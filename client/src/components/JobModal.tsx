import { useEffect, useMemo, useState } from "react";
import type { Job, JobStatus } from "../types/job";
import { Alert,Stack,Dialog, DialogContent, DialogTitle, TextField, MenuItem, DialogActions, Button } from "@mui/material";


type formState={
    company: string;
    title: string;
    status: JobStatus;
    locationType: "REMOTE" | "HYBRID" | "ONSITE";
    location?: string;
    appliedDate?: string;
    source?: string;
    notes?: string;
}

export default function JobModal({
    open,
    mode,
    job,
    onClose,
    onSave,
    onDelete,
    saving,
    deleting,
}:{
    open: boolean;
    mode: "CREATE" | "EDIT";
    job?: Job | null;
    onClose: () => void;
    onSave: (data: formState) => void;
    onDelete?: () => void;
    saving?: boolean;
    deleting?: boolean;
})

{
    const isEdit = mode==="EDIT";

    const [form,SetForm]=useState<formState>({
        company: job?.company ?? "",
        title: job?.title ?? "",
        status: job?.status ?? "APPLIED",
        locationType: job?.locationType ?? "REMOTE",
        location: job?.location ?? "",
        appliedDate: job?.appliedDate ?? "",
        source: job?.source ?? "",
        notes: job?.notes ?? "",
    });
    const [error,SetError]=useState<String>("");

    useEffect(()=>{
        if(isEdit && job){
            SetForm({
                company: job.company ?? "",
                title: job.title ?? "",
                status: job.status ?? "APPLIED",
                locationType: job.locationType ?? "REMOTE",
                location: (job.location ?? "") as string,
                appliedDate: (job.appliedDate ?? "") as string,
                source: (job.source ?? "") as string,
                notes: (job.notes ?? "") as string,
            });
        }   
         if(!isEdit)
    {
        SetForm({
            company: "",
            title: "",
            status: "APPLIED",
            locationType: "REMOTE",
            location: "",
            appliedDate: "",
            source: "",
            notes: "",
        });
    }
    },[isEdit,job,open]);

    const title=useMemo(()=> (isEdit ? "Edit Job" : "Add Job"),[isEdit]);

    function set<K extends keyof formState>(key: K, value: formState[K]){
        SetForm((prev)=>({
            ...prev,
            [key]: value
        }));
    }
    function handleSave(){

        SetError("");
        if(!form.company || form.company.trim().length===0) return SetError("Company is required");
        if(!form.title || form.title.trim().length===0) return SetError("Job Title is required");

        onSave({
            ...form,
            company: form.company.trim(),
            title: form.title.trim(),
            location: (form.location ?? "").trim(),
            source: (form.source ?? "").trim(),
            notes: (form.notes ?? "").trim(),
        })
    }

    return(

        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle sx={{ fontWeight: 900 }}>{title}</DialogTitle>
      <DialogContent sx={{ pt: 1 }}>
        <Stack spacing={2} sx={{ mt: 1 }}>
          {error && <Alert severity="error">{error}</Alert>}

          <TextField
            label="Company"
            value={form.company}
            onChange={(e) => set("company", e.target.value)}
            fullWidth
          />
          <TextField
            label="Role / Title"
            value={form.title}
            onChange={(e) => set("title", e.target.value)}
            fullWidth
          />

          <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
            <TextField
              select
              label="Status"
              value={form.status}
              onChange={(e) => set("status", e.target.value as JobStatus)}
              fullWidth
            >
              {["APPLIED", "INTERVIEW", "OFFER", "REJECTED"].map((s) => (
                <MenuItem key={s} value={s}>
                  {s}
                </MenuItem>
              ))}
            </TextField>

            <TextField
              select
              label="Location type"
              value={form.locationType}
              onChange={(e) => set("locationType", e.target.value as any)}
              fullWidth
            >
              {["REMOTE", "HYBRID", "ONSITE"].map((t) => (
                <MenuItem key={t} value={t}>
                  {t}
                </MenuItem>
              ))}
            </TextField>
          </Stack>

          <TextField
            label="Location (optional)"
            value={form.location}
            onChange={(e) => set("location", e.target.value)}
            fullWidth
          />

          <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
            <TextField
              type="date"
              label="Applied date (optional)"
              InputLabelProps={{ shrink: true }}
              value={form.appliedDate}
              onChange={(e) => set("appliedDate", e.target.value)}
              fullWidth
            />
            <TextField
              label="Source (optional)"
              value={form.source}
              onChange={(e) => set("source", e.target.value)}
              fullWidth
            />
          </Stack>

          <TextField
            label="Notes (optional)"
            value={form.notes}
            onChange={(e) => set("notes", e.target.value)}
            fullWidth
            multiline
            minRows={3}
          />
        </Stack>
      </DialogContent>

      <DialogActions sx={{ px: 3, pb: 2 }}>
        {isEdit && onDelete && (
          <Button
            color="error"
            variant="outlined"
            onClick={onDelete}
            disabled={!!deleting || !!saving}
          >
            {deleting ? "Deleting..." : "Delete"}
          </Button>
        )}

        <Button onClick={onClose} disabled={!!saving || !!deleting}>
          Cancel
        </Button>
        <Button
          variant="contained"
          onClick={handleSave}
          disabled={!!saving || !!deleting}
        >
          {saving ? "Saving..." : isEdit ? "Save" : "Add job"}
        </Button>
      </DialogActions>
    </Dialog>
    );

}