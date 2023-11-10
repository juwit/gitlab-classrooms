package fr.univ_lille.gitlab.classrooms.users;

import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

@Service
class ClassroomUserServiceImpl implements ClassroomUserService {

    private final ClassroomUserRepository classroomUserRepository;

    public ClassroomUserServiceImpl(ClassroomUserRepository classroomUserRepository) {
        this.classroomUserRepository = classroomUserRepository;
    }

    @Override
    public ClassroomUser getClassroomUser(String userId) {
        return this.classroomUserRepository.findById(userId).orElseThrow();
    }

    @Override
    @Transactional
    public ClassroomUser loadOrCreateClassroomUser(OAuth2User oauth2User) {
        var name = oauth2User.getName();
        var classroomUser = classroomUserRepository.findById(name)
                .orElseGet(() -> new ClassroomUser(name, List.of(ClassroomRole.STUDENT)));

        // update id, email and avatar if needed
        var id = oauth2User.getAttributes().get("id");
        classroomUser.setGitlabUserId(Long.parseLong(id.toString()));

        if(oauth2User.getAttributes().containsKey("avatar_url")){
            try {
                classroomUser.setAvatarUrl(URI.create(oauth2User.getAttribute("avatar_url")).toURL());
            } catch (MalformedURLException ignore) {
                // ignore incorrect url
            }
        }
        if(oauth2User.getAttributes().containsKey("email")) {
            classroomUser.setEmail(oauth2User.getAttribute("email"));
        }

        classroomUserRepository.save(classroomUser);

        return classroomUser;
    }

}
