package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
class ClassroomUserServiceImpl implements ClassroomUserService {

    private final ClassroomUserRepository classroomUserRepository;

    public ClassroomUserServiceImpl(ClassroomUserRepository classroomUserRepository) {
        this.classroomUserRepository = classroomUserRepository;
    }

    @Override
    @Transactional
    public ClassroomUser loadOrCreateClassroomUser(OAuth2User oauth2User) {
        var name = oauth2User.getName();
        var classroomUser = classroomUserRepository.findById(name)
                .orElseGet(() -> new ClassroomUser(name, List.of(ClassroomRole.STUDENT)));

        // update email and avatar if needed
        if(oauth2User.getAttributes().containsKey("avatar_url")){
            try {
                classroomUser.setAvatarUrl(new URL(oauth2User.getAttribute("avatar_url")));
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
