package com.jobtracker.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;





public class CurrentUser {

    public static Object principle(){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getPrincipal();

    }

    public static String username() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null ? null : auth.getName();
    }

    public static UUID tryUserId(){
        
        Object principle= principle();

        if(principle==null) return null;

        try {
            var m = principle.getClass().getMethod("getId");
            Object val = m.invoke(principle);
            if (val instanceof UUID) return (UUID) val;
            if (val instanceof String) return UUID.fromString((String) val);
        } catch (Exception ignored) {}

        return null;
    }

      
}
