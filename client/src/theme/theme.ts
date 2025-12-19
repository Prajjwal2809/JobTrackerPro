import { createTheme } from "@mui/material/styles";

export const theme = createTheme({
  shape: { borderRadius: 14 },
  typography: {
    fontFamily: [
      "Inter",
      "ui-sans-serif",
      "system-ui",
      "-apple-system",
      "Segoe UI",
      "Roboto",
      "Arial",
    ].join(","),
    h4: { fontWeight: 800 },
    h5: { fontWeight: 800 },
    button: { textTransform: "none", fontWeight: 700 },
  },
  palette: {
    mode: "light",
    background: {
      default: "#F7F8FA",
      paper: "#FFFFFF",
    },
  },
  components: {
    MuiPaper: { styleOverrides: { root: { border: "1px solid #EEF0F4" } } },
    MuiTextField: { defaultProps: { size: "medium" } },
  },
});
