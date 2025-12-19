import { useState } from "react";
import { login, register } from "../api/api";
import { setToken } from "../utils/token";
import { Box, Button, Paper, TextField, Typography, Stack, Alert, Link } from "@mui/material";


export default function Register() {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function submit(e: React.FormEvent) {
        e.preventDefault();
        setError("");
        if (!name || !email || !password) {
            setError("All fields are required");
            return;
        }
        else {

            const cleanEmail = email.trim().toLowerCase();
            register({ name: name.trim(), email: cleanEmail, password: password })
            .then(() => {
                login({ email: cleanEmail, password: password })
                .then((data) => {
                    setToken(data.accessToken);
                    window.location.href = "/board";
                })
            }).catch((err) => {
                setError(err.response?.data?.message || "Registration failed. Please try again.");
            });
        }
    }
    return(
         <Box sx={{ minHeight: "calc(100vh - 64px)", display: "grid", placeItems: "center", px: 2 }}>
      <Paper sx={{ width: "100%", maxWidth: 440, p: 4 }}>
        <Stack spacing={1.2} sx={{ mb: 2 }}>
          <Typography variant="h5">Create your account</Typography>
          <Typography color="text.secondary">
            Track applications, interviews, and offers in one board.
          </Typography>
        </Stack>

        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

        <form onSubmit={submit}>
          <Stack spacing={2}>
            <TextField label="Full name" value={name} onChange={(e) => setName(e.target.value)} fullWidth />
            <TextField label="Email" value={email} onChange={(e) => setEmail(e.target.value)} fullWidth />
            <TextField label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} fullWidth />
            <Button type="submit" variant="contained" size="large" sx={{ py: 1.2 }}>
              Create account
            </Button>
          </Stack>
        </form>

        <Typography sx={{ mt: 2 }} color="text.secondary">
          Already have an account?{" "}
          <Link href="/login" underline="hover" sx={{ fontWeight: 700 }}>
            Sign in
          </Link>
        </Typography>
      </Paper>
    </Box>

    );
}
