package org.acme.features.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineContext {
    @Builder.Default
    private int revivalSignal = 0;
    @Builder.Default
    private int qaRejections = 0;
    @Builder.Default
    private int springsIgnored = 0;
}
