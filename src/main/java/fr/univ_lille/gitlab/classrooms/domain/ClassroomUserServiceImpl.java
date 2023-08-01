package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClassroomUserServiceImpl implements ClassroomUserService{

    private final ClassroomUserRepository classroomUserRepository;

    public ClassroomUserServiceImpl(ClassroomUserRepository classroomUserRepository) {
        this.classroomUserRepository = classroomUserRepository;
    }

    @Override
    public ClassroomUser loadOrCreateClassroomUser(String name){
        return classroomUserRepository.findById(name)
                .orElseGet(() -> this.createClassroomUser(name));
    }

    ClassroomUser createClassroomUser(String name){
        var classroomUser = new ClassroomUser();
        classroomUser.setName(name);
        classroomUser.setRoles(List.of(ClassroomRole.STUDENT));

        return classroomUserRepository.save(classroomUser);
    }
}
