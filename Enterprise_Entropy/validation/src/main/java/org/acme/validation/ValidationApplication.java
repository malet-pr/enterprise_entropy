package org.acme.validation;

import org.acme.validation.demo.Applicant;
import org.acme.validation.demo.Pipelines;
import org.acme.validation.demo.Rules;
import org.acme.validation.core.model.ValidationError;
import org.acme.validation.core.model.ValidationResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;


@SpringBootApplication
public class ValidationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Logger logger = Logger.getLogger(ValidationApplication.class.getName());
        logger.info("\n\nDemo validation module ... \n");

        List<Function<Applicant, Optional<ValidationError>>> rules = List.of( Rules.nameIsRequired,
                                                                                Rules.mustBeAdult,
                                                                                Rules.depositPositive,
                                                                                Rules.youngUnemployed);

        Applicant applicant1 = new Applicant(null,16,0,false,false);
        Applicant applicant2 = new Applicant("Marcus",16,0,true,false);
        Applicant applicant3 = new Applicant("Lucas",22,100,false,false);
        Applicant applicant4 = new Applicant("John",42,1000,true,false);
        Applicant applicant5 = new Applicant("",42,1000,true,false);

        ValidationResult<Applicant> vr1 = Pipelines.validatePipeline1(applicant1, rules);
        ValidationResult<Applicant> vr2 = Pipelines.validatePipeline1(applicant2, rules);
        ValidationResult<Applicant> vr3 = Pipelines.validatePipeline1(applicant3, rules);
        ValidationResult<Applicant> vr4 = Pipelines.validatePipeline1(applicant4, rules);
        ValidationResult<Applicant> vr5 = Pipelines.validatePipeline1(applicant5, rules);

        logger.info(("Applicant 1: " + vr1));
        logger.info(("Applicant 2: " + vr2));
        logger.info(("Applicant 3: " + vr3));
        logger.info(("Applicant 4: " + vr4));
        logger.info(("Applicant 5: " + vr5));

        logger.info("\n\nEnd of demo. \n");

    }
}
