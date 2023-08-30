package fr.univ_lille.gitlab.classrooms.assignments;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class StudentAssignmentServiceImpl implements StudentAssignmentService {

    private final StudentExerciceAssignmentRepository studentExerciceAssignmentRepository;

    StudentAssignmentServiceImpl(StudentExerciceAssignmentRepository studentExerciceAssignmentRepository) {
        this.studentExerciceAssignmentRepository = studentExerciceAssignmentRepository;
    }

    @Override
    public Optional<StudentExerciseAssignment> getByGitlabProjectId(long gitlabProjectId) {
        return this.studentExerciceAssignmentRepository.findByGitlabProjectId(gitlabProjectId);
    }

    @Override
    public void save(StudentExerciseAssignment exerciseAssignment) {
        this.studentExerciceAssignmentRepository.save(exerciseAssignment);
    }
}
