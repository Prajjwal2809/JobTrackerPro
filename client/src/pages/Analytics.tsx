import { useEffect, useState } from "react";
import { Box, Paper, Typography } from "@mui/material";
import AppShell from "../components/AppShell";
import { getAnalyticsSummary, getAnalyticsTimeline } from "../api/api";
import {
  PieChart, Pie, Tooltip, Legend,
  LineChart, Line, XAxis, YAxis, CartesianGrid,
  Cell
} from "recharts";
import { STATUS_COLORS } from "../utils/colors";


export default function Analytics() {

    const [summary, setSummary] = useState<any>(null);
    const [timeline, setTimeline] = useState<any[]>([]);
    

    useEffect(() => {
        getAnalyticsSummary().then(setSummary).catch(console.error);
        getAnalyticsTimeline().then(setTimeline).catch(console.error);
    },[]);

    const byStatus = summary?.byStatus || {};
    const pieData = Object.keys(byStatus).map((status) => ({
        name: status,
        value: byStatus[status],
    }));


    return (
        <AppShell>
      <Typography variant="h5" sx={{ mb: 2 }}>Analytics</Typography>

      <Box sx={{ display: "grid", gridTemplateColumns: { xs: "1fr", md: "repeat(5, 1fr)" }, gap: 2, mb: 2 }}>
        {[
          ["Total", summary?.total ?? 0],
          ["Applied", byStatus.APPLIED ?? 0],
          ["Interview", byStatus.INTERVIEW ?? 0],
          ["Offer", byStatus.OFFER ?? 0],
          ["Rejected", byStatus.REJECTED ?? 0],
        ].map(([label, value]) => (
          <Paper key={label as string} sx={{ p: 2, borderRadius: 2 }}>
            <Typography color="text.secondary" variant="body2">{label}</Typography>
            <Typography variant="h6" fontWeight={900}>{value as any}</Typography>
          </Paper>
        ))}
      </Box>


      <Paper sx={{ p: 2, borderRadius: 2, mb: 2 }}>
        <Typography fontWeight={900} sx={{ mb: 1 }}>Conversion</Typography>
        <Typography color="text.secondary">
         Applied → Interview: {Math.round((summary?.appliedToInterviewRatio ?? 0) * 100)}%
        {"  "} | {"  "}
        Interview → Offer: {Math.round((summary?.interviewToOfferRatio ?? 0) * 100)}%
        </Typography>
      </Paper>

      <Box sx={{ display: "grid", gridTemplateColumns: { xs: "1fr", md: "1fr 1fr" }, gap: 2 }}>
        <Paper sx={{ p: 2, borderRadius: 2 }}>
          <Typography fontWeight={900} sx={{ mb: 1 }}>Status breakdown</Typography>
          <PieChart width={420} height={280}>
            <Pie data={pieData} 
            dataKey="value" 
            nameKey="name" 
            outerRadius={90}
            >
                {pieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} 
                    fill={ STATUS_COLORS[entry.name] || "#9AA0A6" } 
                    />
                ))}
            </Pie>
            <Tooltip />
            <Legend />
          </PieChart>
        </Paper>

        <Paper sx={{ p: 2, borderRadius: 2 }}>
          <Typography fontWeight={900} sx={{ mb: 1 }}>Applications over time</Typography>
          <LineChart width={520} height={280} data={timeline}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="period" />
            <YAxis allowDecimals={false} />
            <Tooltip />
            <Line type="monotone" dataKey="count" />
          </LineChart>
        </Paper>
      </Box>
    </AppShell>
    );
}