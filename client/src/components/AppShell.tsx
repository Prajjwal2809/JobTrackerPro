import { NavLink } from "react-router-dom";
import { removeToken } from "../utils/token";
import {
  AppBar,
  Box,
  Button,
  Container,
  Toolbar,
  Typography,
} from "@mui/material";

export default function AppShell({ children }: { children: React.ReactNode }) {
  function logout() {
    removeToken();
    window.location.href = "/login";
  }

  const linkStyle = {
    textDecoration: "none",
    color: "text.secondary",
    fontWeight: 600,
    px: 1.5,
    py: 0.5,
    borderRadius: 1,
    "&.active": {
      color: "text.primary",
      bgcolor: "action.selected",
    },
  };

  return (
    <Box sx={{ minHeight: "100vh", bgcolor: "background.default" }}>
      <AppBar position="sticky" elevation={0} color="transparent">
        <Toolbar
          sx={{
            borderBottom: "1px solid #EEF0F4",
            backdropFilter: "blur(10px)",
          }}
        >
          <Container
            maxWidth="lg"
            sx={{ display: "flex", alignItems: "center", gap: 2 }}
          >
            {/* Logo */}
            <Typography variant="h6" sx={{ fontWeight: 900 }}>
              JobTracker <span style={{ opacity: 0.6 }}>Pro</span>
            </Typography>

            {/* Navigation */}
            <Box sx={{ display: "flex", gap: 1 }}>
              <NavLink to="/" end style={{ textDecoration: "none" }}>
                {({ isActive }) => (
                  <Box sx={{ ...linkStyle, ...(isActive && linkStyle["&.active"]) }}>
                    Pipeline
                  </Box>
                )}
              </NavLink>

              <NavLink to="/analytics" style={{ textDecoration: "none" }}>
                {({ isActive }) => (
                  <Box sx={{ ...linkStyle, ...(isActive && linkStyle["&.active"]) }}>
                    Analytics
                  </Box>
                )}
              </NavLink>
            </Box>

            <Box sx={{ flex: 1 }} />

            {/* Logout */}
            <Button variant="outlined" onClick={logout}>
              Logout
            </Button>
          </Container>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ py: 4 }}>
        {children}
      </Container>
    </Box>
  );
}
