package com.tungstun.core.port.persistence.bar;

import com.tungstun.core.domain.bar.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBarRepository extends JpaRepository<Bar, Long> {
}
