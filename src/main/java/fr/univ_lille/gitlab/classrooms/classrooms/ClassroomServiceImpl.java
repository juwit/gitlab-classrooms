package fr.univ_lille.gitlab.classrooms.classrooms;

import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final Gitlab gitlab;

    ClassroomServiceImpl(ClassroomRepository classroomRepository, Gitlab gitlab) {
        this.classroomRepository = classroomRepository;
        this.gitlab = gitlab;
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return this.classroomRepository.findAll();
    }

    @Override
    public List<Classroom> getAllJoinedClassrooms(ClassroomUser student) {
        return this.classroomRepository.findClassroomByStudentsContains(student);
    }

    @Override
    public Optional<Classroom> getClassroom(UUID uuid) {
        return this.classroomRepository.findById(uuid);
    }

    @Transactional
    @Override
    public void joinClassroom(Classroom classroom, ClassroomUser student) {
        classroom.join(student);

        this.classroomRepository.save(classroom);
    }

    @Transactional
    @Override
    public void createClassroom(String classroomName, Long parentGitlabGroupId, ClassroomUser teacher) throws GitLabApiException {
        var classroom = new Classroom();
        classroom.setName(classroomName);
        classroom.addTeacher(teacher);

        this.gitlab.createGroup(classroom, Optional.ofNullable(parentGitlabGroupId));

        this.classroomRepository.save(classroom);
    }

    @Override
    public void saveClassroom(Classroom classroom) {
        this.classroomRepository.save(classroom);
    }
}
