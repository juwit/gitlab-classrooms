package fr.univ_lille.gitlab.classrooms.assignments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class AutoArchiveJob {

    private AssignmentRepository assignmentRepository;

    private ArchiveAssignmentUseCase archiveAssignmentUseCase;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoArchiveJob.class.getName());

    public AutoArchiveJob(AssignmentRepository assignmentRepository, ArchiveAssignmentUseCase archiveAssignmentUseCase) {
        this.assignmentRepository = assignmentRepository;
        this.archiveAssignmentUseCase = archiveAssignmentUseCase;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    void autoArchiveAssignments(){
        LOGGER.info("Auto archiving assignments");
        this.assignmentRepository.findAll()
                .stream()
                .filter(Assignment::isAutoArchive)
                .filter(it -> it.getDueDate().isBefore(ZonedDateTime.now()))
                .forEach(it -> this.archiveAssignmentUseCase.archive(it));
    }
}
