package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.gitlab.GitlabApiFactory;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final GitLabApi gitLabApi;

    private final GitlabApiFactory gitlabApiFactory;

    ClassroomServiceImpl(ClassroomRepository classroomRepository, GitLabApi gitLabApi, GitlabApiFactory gitlabApiFactory) {
        this.classroomRepository = classroomRepository;
        this.gitLabApi = gitLabApi;
        this.gitlabApiFactory = gitlabApiFactory;
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return this.classroomRepository.findAll();
    }

    @Override
    public Optional<Classroom> getClassroom(UUID uuid) {
        return this.classroomRepository.findById(uuid);
    }

    @Transactional
    @Override
    public void joinClassroom(Classroom classroom, ClassroomUser student) throws GitLabApiException {
        classroom.join(student);

        // make the student join the gitlab group
        var teacher = classroom.getTeacher();
        var teacherGitlabApi = gitlabApiFactory.userGitlabApi(teacher);

        var user = teacherGitlabApi.getUserApi().getUser(student.getName());
        teacherGitlabApi.getGroupApi().addMember(classroom.getGitlabGroupId(), user.getId(), AccessLevel.DEVELOPER);

        this.classroomRepository.save(classroom);
    }

    @Transactional
    @Override
    public void createClassroom(String classroomName, Long parentGitlabGroupId, ClassroomUser teacher) throws GitLabApiException {
        var classroom = new Classroom();
        classroom.setName(classroomName);
        classroom.setTeacher(teacher);

        var groupPath = classroomName.trim().replaceAll("[^\\w\\-.]", "_");

        var groupParams = new GroupParams()
                .withName(classroomName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the Classroom " + classroomName);
        if(parentGitlabGroupId != null){
            groupParams.withParentId(parentGitlabGroupId);
        }
        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        classroom.setGitlabGroupId(group.getId());

        this.classroomRepository.save(classroom);
    }

    @Override
    public void saveClassroom(Classroom classroom) {
        this.classroomRepository.save(classroom);
    }
}
