package com.tungstun.core.application.bar.query;

import javax.validation.constraints.NotNull;
import java.util.List;

public record GetOwnedBars(
        @NotNull(message = "Owned bar id's cannot be empty")
        List<Long> barIds) {
}
