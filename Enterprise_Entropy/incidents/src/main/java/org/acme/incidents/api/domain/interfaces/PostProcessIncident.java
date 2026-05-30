package org.acme.incidents.api.domain.interfaces;

import org.acme.incidents.dto.ProcessedIncidentFull;

//UnaryOperator
@FunctionalInterface
public interface PostProcessIncident {
    ProcessedIncidentFull postProcess(ProcessedIncidentFull processed);
}
