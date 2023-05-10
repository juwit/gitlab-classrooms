package fr.univ_lille.gitlab.classrooms.mock;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the Classroom repository that uses the Gitlab API
 */
@Repository
@Primary
class ClassroomRepositoryGitlabImpl implements ClassroomRepository {

    GitLabApi gitLabApi;

    public ClassroomRepositoryGitlabImpl(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @Override
    public Iterable<Classroom> findAllClassrooms() {
        try {
            return gitLabApi.getGroupApi().getGroupsStream()
                    .map(it -> new Classroom(it.getName(), it.getName()))
                    .toList();
        } catch (GitLabApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveClassroom(Classroom classroom) {
        try {
            var groupParams = new GroupParams()
                    .withName(classroom.name())
                    .withPath(classroom.name())
                    .withDescription("Gitlab Classroom group");
            this.gitLabApi.getGroupApi().createGroup(groupParams);
        } catch (GitLabApiException e) {
            throw new RuntimeException(e);
        }
    }
}
