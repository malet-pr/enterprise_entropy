package org.acme.incidents;

import org.acme.incidents.demo.SampleIncidents;
import org.acme.incidents.engine.Process;
import org.acme.incidents.model.Incident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
public class IncidentsApplication  implements CommandLineRunner {

    protected static Logger log = LoggerFactory.getLogger(IncidentsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IncidentsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("\n\nStarting Incident Triage Application Runner ...\n");

       Process process = new Process();

        Supplier<List<Incident>> incidentSupplier = SampleIncidents.sampleData();
        List<Incident> incidents = incidentSupplier.get();
        process.processTwo(incidents);

        log.info("\n\nIncident Triage Application Runner Finished...\n");
    }
}
