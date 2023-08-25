package fr.univ_lille.gitlab.classrooms;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Controller;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "fr.univ_lille.gitlab.classrooms")
class ArchitectureTest {

    @ArchTest
    final ArchRule repositoriesAreNotExposedOutsidePackages = classes()
            .that()
            .areAssignableTo(Repository.class)
            .should()
            .bePackagePrivate();

    @ArchTest
    final ArchRule serviceInterfacesAreExposedAsPublic = classes()
            .that()
            .haveNameMatching(".*Service")
            .should()
            .beInterfaces()
            .andShould()
            .bePublic();

    @ArchTest
    final ArchRule controllersAreNotExposedOutsidePackages = classes()
            .that()
            .haveNameMatching(".*Controller")
            .or()
            .areAnnotatedWith(Controller.class)
            .should()
            .bePackagePrivate();
}
