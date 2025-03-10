package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
class AssignmentScoreServiceImpl implements AssignmentScoreService{

    private final StudentAssignmentRepository studentAssignmentRepository;

    AssignmentScoreServiceImpl(StudentAssignmentRepository studentAssignmentRepository) {
        this.studentAssignmentRepository = studentAssignmentRepository;
    }

    @Override
    @Transactional
    public void registerScore(Assignment assignment, ClassroomUser student, long score, long maxScore) {
        var studentAssignment = this.studentAssignmentRepository.findByAssignmentAndStudent(assignment, student);

        // increment retake count if needed
        if(studentAssignment instanceof StudentQuizAssignment quizAssignment && quizAssignment.hasBeenSubmitted()){
            quizAssignment.setRetakes(quizAssignment.getRetakes() + 1);
        }

        studentAssignment.setScore(score);
        studentAssignment.setMaxScore(maxScore);
        studentAssignment.setSubmissionDate(ZonedDateTime.now());
        this.studentAssignmentRepository.save(studentAssignment);
    }
}
