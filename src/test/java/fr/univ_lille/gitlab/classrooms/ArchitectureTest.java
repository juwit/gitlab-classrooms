package fr.univ_lille.gitlab.classrooms;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "fr.univ_lille.gitlab.classrooms")
class ArchitectureTest {

    @ArchTest
    final ArchRule repositories_are_not_exposed_outside_packages = classes()
            .that()
            .areAssignableTo(Repository.class)
            .should()
            .bePackagePrivate();

    @ArchTest
    final ArchRule service_interfaces_are_exposed_as_public = classes()
            .that()
            .haveNameMatching(".*Service")
            .should()
            .beInterfaces()
            .andShould()
            .bePublic();

    @ArchTest
    final ArchRule service_implementations_are_not_exposed_outside_packages = classes()
            .that()
            .haveNameMatching(".*ServiceImpl")
            .or()
            .areAnnotatedWith(Service.class)
            .should()
            .beTopLevelClasses()
            .andShould()
            .bePackagePrivate();

    @ArchTest
    final ArchRule controllers_are_not_exposed_outside_packages = classes()
            .that()
            .haveNameMatching(".*Controller")
            .or()
            .areAnnotatedWith(Controller.class)
            .should()
            .bePackagePrivate();

    @ArchTest
    final ArchRule controllers_and_services_only_have_private_final_fields = fields()
            .that()
            .areDeclaredInClassesThat()
            .haveNameMatching(".*Controller")
            .or()
            .areAnnotatedWith(Controller.class)
            .or()
            .haveNameMatching(".*ServiceImpl")
            .or()
            .areAnnotatedWith(Service.class)
            .should()
            .beFinal()
            .andShould()
            .bePrivate();

    @ArchTest
    final ArchRule controllers_and_services_only_have_package_private_constructors = constructors()
            .that()
            .areDeclaredInClassesThat()
            .haveNameMatching(".*Controller")
            .or()
            .areAnnotatedWith(Controller.class)
            .or()
            .haveNameMatching(".*ServiceImpl")
            .or()
            .areAnnotatedWith(Service.class)
            .should()
            .bePackagePrivate();
}
