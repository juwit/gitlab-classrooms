package fr.univ_lille.gitlab.classrooms;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ArchitectureTest {

    @Test
    void repositoriesAreNotExposedOutsidePackages() {
        var importedClasses = new ClassFileImporter()
                .importPackages("fr.univ_lille.gitlab.classrooms");

        var rule = classes()
                .that()
                .areAssignableTo(Repository.class)
                .should()
                .bePackagePrivate();

        rule.check(importedClasses);
    }

    @Test
    void serviceInterfacesAreExposedAsPublic() {
        var importedClasses = new ClassFileImporter()
                .importPackages("fr.univ_lille.gitlab.classrooms");

        var rule = classes()
                .that()
                .haveNameMatching(".*Service")
                .should()
                .beInterfaces()
                .andShould()
                .bePublic();

        rule.check(importedClasses);
    }
}
