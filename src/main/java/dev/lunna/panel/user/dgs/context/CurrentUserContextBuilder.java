package dev.lunna.panel.user.dgs.context;

import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import dev.lunna.panel.user.ApplicationUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserContextBuilder implements DgsCustomContextBuilder<CurrentUserContext> {
    @Override
    public CurrentUserContext build() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return new CurrentUserContext(null);
        }

        if (!(auth.getPrincipal() instanceof ApplicationUser user)) {
            return new CurrentUserContext(null);
        }

        return new CurrentUserContext(user);
    }
}
