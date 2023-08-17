package fr.univ_lille.gitlab.classrooms.domain;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final GitLabApi gitLabApi;

    ClassroomServiceImpl(ClassroomRepository classroomRepository, GitLabApi gitLabApi) {
        this.classroomRepository = classroomRepository;
        this.gitLabApi = gitLabApi;
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return this.classroomRepository.findAll();
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
}
