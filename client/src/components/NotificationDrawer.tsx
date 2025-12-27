import { useEffect, useMemo, useState } from "react";
import {
  Badge,
  Box,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Stack,
  Tooltip,
  Typography,
  Button,
} from "@mui/material";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import DoneIcon from "@mui/icons-material/Done";

import type { Notification } from "../types/notification";
import {
  getNotification,
  getNotificationUnreadCount,
  markReadNotification,
  deleteNotification,
} from "../api/api";

function formatType(t: Notification["notificationType"]) {
  if (t === "JOB_CREATED") return "Job created";
  if (t === "JOB_UPDATED") return "Job updated";
  if (t === "JOB_DELETED") return "Job deleted";
  return t;
}

export default function NotificationsDrawer() {
  const [open, setOpen] = useState(false);
  const [items, setItems] = useState<Notification[]>([]);
  const [unreadCount, setUnreadCount] = useState<number>(0);
  const [loading, setLoading] = useState(false);

  const unread = useMemo(
    () => items.filter((n) => !n.read).length,
    [items]
  );

  async function refreshAll() {
    setLoading(true);
    try {
      const [list, count] = await Promise.all([
        getNotification(),
        getNotificationUnreadCount(),
      ]);

      // Your API may return {content: []} or []
      const normalized: Notification[] = Array.isArray(list)
        ? list
        : (list?.content ?? []);

      setItems(normalized);
      setUnreadCount(typeof count === "number" ? count : count?.unreadCount ?? unread);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    refreshAll();
    const t = setInterval(refreshAll, 15000); // polling
    return () => clearInterval(t);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function onMarkRead(id: string) {
    // optimistic
    setItems((prev) => prev.map((n) => (n.id === id ? { ...n, read: true } : n)));
    setUnreadCount((c) => Math.max(0, c - 1));

    try {
      await markReadNotification(id);
    } catch (e) {
      console.error(e);
      // rollback by refetching
      refreshAll();
    }
  }

  async function onDelete(id: string) {
    const target = items.find((x) => x.id === id);
    setItems((prev) => prev.filter((n) => n.id !== id));
    if (target && !target.read) setUnreadCount((c) => Math.max(0, c - 1));

    try {
      await deleteNotification(id);
    } catch (e) {
      console.error(e);
      refreshAll();
    }
  }

  return (
    <>
      <Tooltip title="Notifications">
        <IconButton onClick={() => setOpen(true)} size="large">
          <Badge badgeContent={unreadCount} color="error">
            <NotificationsNoneIcon />
          </Badge>
        </IconButton>
      </Tooltip>

      <Drawer anchor="right" open={open} onClose={() => setOpen(false)}>
        <Box sx={{ width: 380, p: 2 }}>
          <Stack direction="row" alignItems="center" justifyContent="space-between">
            <Typography variant="h6" fontWeight={900}>
              Notifications
            </Typography>

            <Stack direction="row" spacing={1}>
              <Button size="small" onClick={refreshAll} disabled={loading}>
                {loading ? "Refreshing..." : "Refresh"}
              </Button>
              <Button size="small" onClick={() => setOpen(false)}>
                Close
              </Button>
            </Stack>
          </Stack>

          <Typography variant="body2" color="text.secondary" sx={{ mt: 0.5 }}>
            Unread: {unreadCount}
          </Typography>

          <Divider sx={{ my: 2 }} />

          {items.length === 0 ? (
            <Typography color="text.secondary">No notifications.</Typography>
          ) : (
            <List disablePadding>
              {items.map((n) => (
                <ListItem
                  key={n.id}
                  disablePadding
                  secondaryAction={
                    <Stack direction="row" spacing={0.5}>
                      {!n.read && (
                        <Tooltip title="Mark as read">
                          <IconButton edge="end" onClick={() => onMarkRead(n.id)}>
                            <DoneIcon />
                          </IconButton>
                        </Tooltip>
                      )}
                      <Tooltip title="Delete">
                        <IconButton edge="end" onClick={() => onDelete(n.id)}>
                          <DeleteOutlineIcon />
                        </IconButton>
                      </Tooltip>
                    </Stack>
                  }
                >
                  <ListItemButton
                    onClick={() => {
                      if (!n.read) onMarkRead(n.id);
                      // Optional: navigate to job details if you have route
                      // if (n.jobId) navigate(`/jobs/${n.jobId}`);
                    }}
                    sx={{
                      alignItems: "flex-start",
                      opacity: n.read ? 0.6 : 1,
                      "& .MuiListItemText-primary": { fontWeight: n.read ? 600 : 900 },
                    }}
                  >
                    <ListItemText
                      primary={n.title || formatType(n.notificationType)}
                      secondary={
                        n.message
                          ? n.message
                          : formatType(n.notificationType) + (n.jobId ? ` • Job: ${n.jobId}` : "")
                      }
                    />
                  </ListItemButton>
                </ListItem>
              ))}
            </List>
          )}
        </Box>
      </Drawer>
    </>
  );
}
