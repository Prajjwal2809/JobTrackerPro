import { useState } from "react";
import { login } from "../api/api";
import { setToken } from "../utils/token";
import { Box, Button, Paper, TextField, Typography, Stack, Alert, Link } from "@mui/material";


export default function Login(){

    const [email,setEmail]=useState("");
    const [password,setPassword]=useState("");
    const[error,setError]=useState("");

    function submit(e:React.FormEvent){
        e.preventDefault();
        setError("");

        if(!email || !password){
            setError("Email and Password are required");
            return;
        }
        else{
            login({email:email.trim().toLowerCase(),password:password})
            .then((data)=>{
                setToken(data.accessToken);
                window.location.href="/board";
            }).catch((err)=>{
                setError(err.response?.data?.message || "Login failed. Please try again.");
            });
        }
}
    return(
    <Box sx={{ minHeight: "calc(100vh - 64px)", display: "grid", placeItems: "center", px: 2 }}>
      <Paper sx={{ width: "100%", maxWidth: 440, p: 4 }}>
        <Stack spacing={1.2} sx={{ mb: 2 }}>
          <Typography variant="h5">Welcome back</Typography>
          <Typography color="text.secondary">
            Sign in to manage your job pipeline like a pro.
          </Typography>
        </Stack>

        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

        <form onSubmit={submit}>
          <Stack spacing={2}>
            <TextField label="Email" value={email} onChange={(e) => setEmail(e.target.value)} fullWidth />
            <TextField label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} fullWidth />
            <Button type="submit" variant="contained" size="large" sx={{ py: 1.2 }}>
              Sign in
            </Button>
          </Stack>
        </form>

        <Typography sx={{ mt: 2 }} color="text.secondary">
          New here?{" "}
          <Link href="/register" underline="hover" sx={{ fontWeight: 700 }}>
            Create an account
          </Link>
        </Typography>
      </Paper>
    </Box>
  );

}
