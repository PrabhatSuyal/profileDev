package com.profileDev.profileDev.Auditing;

import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("HardCodedUser");
    }
}
