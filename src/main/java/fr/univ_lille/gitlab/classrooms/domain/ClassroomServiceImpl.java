package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<Classroom> getAllClassrooms() {
        return this.classroomRepository.findAll();
    }
}
