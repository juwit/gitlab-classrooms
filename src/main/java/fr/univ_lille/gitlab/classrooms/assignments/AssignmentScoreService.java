package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;

public interface AssignmentScoreService {

    void registerScore(Assignment assignment, ClassroomUser student, long score, long maxScore);
}
