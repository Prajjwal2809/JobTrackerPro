import { removeToken } from "../utils/token";
import { AppBar, Box, Button, Container, Toolbar, Typography } from "@mui/material";

export default function AppShell({ children }: { children: React.ReactNode}) {

    function logout() {
       
        removeToken();
        window.location.href = '/login';
    }   

    return(
   <Box sx={{ minHeight: "100vh", bgcolor: "background.default" }}>
      <AppBar position="sticky" elevation={0} color="transparent">
        <Toolbar sx={{ borderBottom: "1px solid #EEF0F4", backdropFilter: "blur(10px)" }}>
          <Container maxWidth="lg" sx={{ display: "flex", alignItems: "center", gap: 2 }}>
            <Typography variant="h6" sx={{ fontWeight: 900 }}>
              JobTracker <span style={{ opacity: 0.6 }}>Pro</span>
            </Typography>
            <Box sx={{ flex: 1 }} />
            <Button variant="outlined" onClick={logout}>Logout</Button>
          </Container>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ py: 4 }}>
        {children}
      </Container>
    </Box>
  );


}