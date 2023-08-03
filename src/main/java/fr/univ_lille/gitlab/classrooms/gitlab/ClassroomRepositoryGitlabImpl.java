package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the Classroom repository that uses the Gitlab API
 */
@Repository
class ClassroomRepositoryGitlabImpl implements ClassroomRepository {

    GitLabApi gitLabApi;

    public ClassroomRepositoryGitlabImpl(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @Override
    public Iterable<Classroom> findAllClassrooms() throws GitLabApiException {
        return gitLabApi.getGroupApi().getGroupsStream()
                .map(it -> new Classroom(it.getName(), it.getName(), it.getWebUrl()))
                .toList();
    }

    @Override
    public void saveClassroom(Classroom classroom) throws GitLabApiException {
        var groupParams = new GroupParams()
                .withName(classroom.name())
                .withPath(classroom.name())
                .withDescription("Gitlab Classroom group");
        this.gitLabApi.getGroupApi().createGroup(groupParams);
    }
}
