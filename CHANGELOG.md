# Changelog

<a name="0.0.2"></a>
## 0.0.2 (2023-08-22)

### Added

- ✨ : add score on student dashboard [ccbb8bc]
    * ✨ : add empty student dashboard (7226b2c)
- ✨ : submit quiz answer with assignment ids [b0978c0]
- ✨ : add quiz preview [17eef5f]
- ➕ : add spring-boot-starter-actuator [c80558b]
- ✨ : add AssignmentScoreService [aae182b]
- ✅ : add more tests [d655717]
- ✨ : student dashboard [259425a]
- ✨ : findAllJoinedClassrooms [0d1f4ca]
- ✨ : create gitlab project [fa096e0]
- ✨ : save gitlab user id for classroom users [d399f14]
- ✨ : create gitlab group for exercise assignment [817b429]
- ✨ : create group for a classroom [1b8418f]
- ✨ : get group uri [de75359]
- ✨ : list groups of connected user [1dfd70c]

### Changed

- 🗃️ : add student assignment tables [5694542]
- 💄 : use layout without menu for quiz submission pages [d45f39a]
- ♻️ : delete old quizScore implementation [1bdc080]
- ♻️ : extract model attribute name [59c1d3f]
- ♻️ : introduce common ancestor for student quiz &amp; exercise assignments [cffc93e]
- 💄 : make logout button full width [ebd8f23]
- 🚚 : rename domain package to classrooms [e403812]
- 🚚 : move to security package [4d5c5ac]
- 🚚 : rename HomeController [aef9d96]
- 🚚 : rename &#x27;home&#x27; to &#x27;teacher-dashboard&#x27; [b6d96b5]
- 🗃️ : add missing database script [bba7206]
- ♻️ : set visibility to package-private [97af73c]
- ♻️ : get projects of connected user [bf73e55]

### Removed

- 🔥 : remove unused exception [f474e83]

### Miscellaneous

- 🔀 : merge branch &#x27;feature/student-dashboard&#x27; into develop [4aa4042]
- 🔀 : merge branch &#x27;feature/gitlab-service&#x27; into develop [0b61d10]
- 🔀 : merge tag &#x27;0.0.1&#x27; into develop [360fa2e]


<a name="0.0.1"></a>
## 0.0.1 (2023-08-18)

### Added

- ✨ : show gitlab project link after assignment acceptation [1c9cba7]
- ✨ : show exerciseResults on assignment page [97dd103]
- ✨ : add StudentExercise entity [8de1c2a]
- ✨ : show submissions count [6c07579]
- ✨ : create gitlab project when accepting exercice assignments [222f3df]
- ✨ : add gitlab_group_id column on Classroom table [f19c195]
- ✨ : add &#x27;open in gitlab&#x27; button on Classroom view [5bad698]
- ✨ : redirect student to join classroom if needed [9dd2446]
- ✨ : add equals &amp; hashcode [33218a2]
- ✅ : add missing tests [16093e8]
- ✨ : make student join gitlab group when joining classroom [18d9f11]
- ✅ : add test for assignment creation [518c301]
- ✨ : add QuizService [6b9fcea]
- ✨ : add specialized annotation for test roles [4b95a8f]
- ✨ : add update schema scripts [a14eb4d]
- ✨ : save the teacher that created the classroom [355a604]
- ✅ : update tests after ModelAttribute change [fe31b35]
- ✨ : create gitlab group when creating exercise assignment [8ab0b77]
- ✨ : create gitlab group when creating classroom [f23356a]
- ✨ : add exercise submission view [db32c48]
- ✨ : add schema update for ExerciseAssignments [93354e0]
- ✨ : add exercice assignment [a14ef61]
- ✨ : add view assignment results [0224e49]
- ✅ : add tests for ClassroomController [c2aae32]
- ✨ : new assignment [96d8c2a]
- ✨ : view classroom [aab139b]
- ✨ : accept assignments [c0d3783]
- ✨ : join classrooms [1fe27c0]
- ✨ : use classroom user info [ab20fef]
- ✨ : add foreign key for quiz_score to classroom_user [49b68bf]
- ✨ : save users avatar_url &amp; email [747bb8f]
- ✅ : add publish folder [5eb58b0]
- ✨ : add quiz results page [ba3a8fd]
- ✨ : show previous quiz submission info [07ecff4]
- ✨ : add database types [cf48b51]
- ✨ : add QuizScoreService [75a605a]
- ✅ : fully test MVC for QuizController [8ca6775]
- ✅ : add main test class [1d7a38f]
- ✅ : test role enforcement for quiz edition [19fd885]
    * ✅ : test role enforcement for quiz edition (dd8d47c)
- ➕ : add spring-security-test [32b758f]
    * ➕ : add spring-security-test (cbd7917)
- ✅ : test role enforcement for new classroom page [3965f6b]
- ✅ : test role enforcement for home page [e4a09d2]
- ✨ : sort quiz on list page [a898cdf]
- ✨ : add signle choice answers correction [75e102c]
- ✨ : add help text [888f3b3]
- ✨ : implement single choice answers from frontend [2356bf0]
- ✨ : implement single-choice questions in backend [bdd1083]
- ✨ : add quiz creation [f9bb9dd]
- ✨ : add quiz to menu [cc17f9a]
- ✨ : add quiz edition view [53b32e7]
- ✨ : add quiz-list view [3bd3189]
- ✨ : add logout [cfd1f0e]
- ✨ : add user profile [537e8d2]
- ✨ : load quiz from database [dc28105]
- ✅ : add full text question test [369de97]
- ✨ : add custom error page [bc0dfcb]
    * 💄 : add custom login page (4850f29)
- ✨ : add ClassroomRole as user Authorities [e3d2736]
- ✨ : set hibernate to validate mode [f2bba9c]
- ✨ : add ClassroomUser entity [c443792]
- ✨ : add question explanation [ac8e514]
    * ✨ : add question explanation parsing (832e654)
- ✨ : check full text answers [50768ac]
- ✨ : add correction page after quiz submission [52e7653]
    * ✨ : add confirmation page after quiz submission (a4ec71d)
- ✨ : save quiz score to database [b31c917]
- ✨ : compute score for quiz [cdcc9ec]
- ✅ : add tests for quiz [74aa9ee]
- ✨ : implement question correctness check [5c481c8]
- ✅ : correct integration test [97f4e0d]
- ✅ : add some tests for Answers [40480ee]
- ✨ : restore input text uppon submission [ecb5645]
- ✨ : reselect previous answers for partial submission [fb66776]
- ✨ : handle partial form submission [112926d]
    * 💩 : handle form submission (a34381c)
- ✨ : correct answer id [2fcc94b]
- ✨ : add quiz name [66b1c37]
    * ✨ : add quiz page (fb276f1)
- ✨ : add question types [5019817]
- ✨ : add sha256 answer ids [7a37d0d]
- ➕ : add commons codec [51f6f5d]
- ✨ : implement Quiz view [619f2aa]
- ✨ : add sample markdown quiz file [815ee92]
- ✨ : add java classes from quiz [85139a4]
- ✨ : use quiz engine on sample data [c5c7a44]
- ✨ : init quiz-engine [2d40423]
- ✨ : add view controller for quiz page [76c86f0]
- ✨ : add oauth2_authorized_clients migration script [2ed8cd3]
- ➕ : add flyway dependency for database migration scripts [506f9f8]
- ➕ : add postgresql JDBC driver [50fc3e3]
- ➕ : add spring-boot-starter-data-jpa [165ae9c]
    * ➕ : add spring-boot-starter-thymeleaf (c965a4e)
    * ➕ : add spring-boot-starter-web (d1675f6)
- ✨ : add GrantedAuthoritiesMapper to be able to customize roles [17a843c]
- ✨ : use OAuth2AuthorizedClient to authenticate to the gitlab Api [9ce4f3e]
- ✨ : add oauth2 properties for gitlab authentication [63aa16b]
- ➕ : add spring-boot-starter-oauth2-client [bfee6bf]
- ✨ : add Gitlab link on classroom list [e80c1c5]
- ✨ : add Gitlab URL to Classroom data [ecee08e]
- ✨ : add repository that access Gitlab API [c26820e]
- ✨ : configure GitLabApi [fff422b]
- 🔊 : add gitlab info on classroom creation form [3376367]
- ✨ : add new classroom button on classroom list [105d6b7]
- ✨ : add classroom creation form [013ec89]
- ✨ : add new classroom page [12bc976]
    * ✨ : add an id to the Classrooms (10a5e81)
- ✨ : add page title variable to template [bf714f0]
- ✨ : update mobile menu [2a4b761]
- ✨ : add root href [1430ab8]
- ✨ : add new classroom button link [6126118]
- ✨ : add ClassroomRepository [13d4fdf]
- ✨ : add classroom list [759900d]
- ✨ : add dashboard and empty state [d6ae2d7]
- ➕ : add spring-boot-devtools [bab45da]
- ➕ : add gitlab4j-api dependency [b12302a]
    * ➕ : add gitlab4j-api dependency (d1053e1)
- 🎉 : init project [222bcc1]

### Changed

- 🚚 : move to users package [af059d5]
- ♻️ : change visibility to package-private [3213ab9]
- 🚚 : move all users code to dedicated package [a1d597d]
- ♻️ : use ClassroomService [3510344]
    * ✨ : add ClassroomUserService (8784528)
- ♻️ : update class visibilities [9ca2545]
- 🚚 : move all assignments code to dedicated package [ba3dc34]
- ♻️ : move tests for AssignmentController [bf38f80]
    * ✅ : add tests for AssignmentController (6126ad7)
- ♻️ : move assignment creation to AssignmentController [a56b535]
- ♻️ : craete joinClassroom method [6cd9399]
- ♻️ : use ClassroomService where possible [39e528c]
- ♻️ : move Classroom creation to ClassroomService [b19e417]
- ♻️ : replace switch with an if [a26bea7]
- ♻️ : remove redundant regex selectors [7593350]
- ♻️ : extract AssignmentService interface [ebef69c]
- ♻️ : use AssignmentServiceImpl where possible [1f35bbb]
- ♻️ : use ClassroomUser directly instead of passing the id [2371409]
- ♻️ : use ClassroomUser model attribute instead of Authentication [ca1f036]
- ♻️ : extract ClassroomService [849b1bf]
- ♻️ : organize imports [49b6ec6]
    * ♻️ : organize imports (ad12434)
- ♻️ : add a ModelAttribut for connected user [824a03d]
- ♻️ : use new template names [d8ec176]
- 🚚 : rename template [16a3575]
    * 🚚 : rename template (a1e14c3)
    * 🚚 : rename template (a141fbc)
- 🚨 : correct some sonarqube issues [a6919b4]
    * 💚 : correct some sonarqube issues (0b7f49a)
- 🔧 : add assignments schema definition [7695881]
- 🔧 : local profile [271d346]
- 💄 : display classrooms on 2 colums on md breakpoint [e8d61fc]
- 💄 : display quiz on 2 colums on md breakpoint [96c6252]
- 🔧 : configure mkdocs [dd46aed]
- ♻️ : split QuizController in two [decc5ad]
- 💄 : add active menu condition [5eecee1]
- 🔧 : add clevercloud build configuration [17a3bf4]
    *  👷 : add clever-cloud configuration (38d20f7)
- 🚚 : move file to gitlab package [7b50438]
- ⬆️ : upgrade spring-boot-starter-parent to 3.1.2 [0ac9e88]
- 🔧 : do not use flyway when testing [8d1a3e6]
- 🔧 : configure jacoco-maven-plugin for coverage [6a61ff0]
- 🚚 : rename .gitlab-ci.yaml file [782f1a7]
- 🎨 : format code [f9645be]
- ♻️ : remove useless curly braces around statement [7d87953]
- ♻️ : define a constant instead of duplicating this literal &quot;login&quot; 3 times. [b1e5bdb]
    * ♻️ : define a constant instead of duplicating this literal &quot;quiz/edit&quot; 3 times. (daf3a49)
- ♻️ : define and throw a dedicated exception instead of using a generic one [bf3ef5c]
- ♻️ : replace this lambda with method reference &#x27;Answer::isCorrect&#x27;. [a27ab8e]
- 🔧 : add production properties [b93c340]
- 🍱 : add static icon assets [c6c93d8]
- ♻️ : use @Nested for answers types [1ab5188]
    * ♻️ : use @Nested for question types (5dfd167)
- 🚚 : use error page for all 4xx errors [64d6f5f]
- ♻️ : share title between question types [d547d0e]
- 💄 : manage question types [1fb4e65]
- 🔧 : remove unused configuration file [13bb6e9]
- 🔧 : configure storage of authorized clients [b851074]
- 🔧 : set jdbc pool size to 2 [36858bf]
- 🔧 : configure database connectivity [1b888f9]
- 💄 : update favicon uri [b7481fc]
- 💄 : add cute fox logo [2d9e30c]
- 🔧 : enable configuration properties [fae360b]
- ⬆️ : upgrade gitlab4j to 6.0.0 [ce202f6]
- 🚚 : move mock to dedicated package [37256fb]
- 💄 : remove fixed height [933a0a3]
- ♻️ : extract layout from home page [3ca8416]
- ♻️ : extract Classroom to its own class file [01abb55]
    * ♻️ : extract Classroom to its own class file (584653c)
- 🎨 : replace tabs with spaces [bf52a9d]

### Removed

- 🔥 : do not add the student to the gitlab group [e5f8d08]
- 🔥 : remove unnecessary import [ee684d7]
- 🔥 : remove unused mocks [04710fe]
    * 🔥 : remove unused code (b4e5d72)
    * 🔥 : remove unused imports (026d1d0)
- 🔥 : remove unused ClassroomRepository reference [a42f374]
- 🔥 : delete old user modelAttribute [3c3f143]
- 🔥 : remove unused template [5d16b4d]
- 🔥 : remove dead code [55497c8]
- 🔥 : delete unused mock class [bfd4e87]
- 🔥 : remove this unused &quot;LOGGER&quot; private field. [f92de07]
- 🔥 : remove this unused import &#x27;java.util.Optional&#x27;. [50985ef]
- 🔥 : remove client-side quiz-engine [35a7c20]
- 🔥 : delete old markdown quiz [613b211]
- 🔥 : remove GrantedAuthoritiesMapper [607828c]
- 🔥 : remove quiz view controller [a3a2c23]
- 🔥 : remove unused properties [b6f46a2]
- 🔥 : remove mobile menu [555b2f9]

### Fixed

- 🐛 : correct assignment type check [d06e655]
- 🐛 : correct broken cancel link [4b112b3]
- 💚 : add entrypoint to clever-tools image definition [e906ff2]
- 💚 : correct sonar issues [2e0d0a3]
- 🐛 : correct single choice question answer [9feb600]
- 🐛 : support quiz with windows line breaks [1021b44]
- 🐛 : correct full text rendering [3fa1b07]
- ✏️ : correct encoding issues on questions [25eaa62]
- 🐛 : do not use blank answers [d633ae0]

### Security

- 🔒 : configure OAuth2AuthorizedClientManager to access an oauth2 authorized client (end-user) out of the scope of a HttpServletRequest. [bc9dc5e]
- 🔒 : enable method security [0ac574b]
- 🔒 : add csrf token [62a879f]

### Miscellaneous

- 🔐 : only teachers should be able to view assignment results [6ea2e06]
- 🔀 : merge branch &#x27;feature/docs&#x27; into develop [1f675ee]
    * 🔀 : merge branch &#x27;feature/mvc-tests&#x27; into develop (d2b6c7d)
    * 🔀 : merge branch &#x27;feature/sonar-issues&#x27; into develop (4ae944c)
    * 🔀 : merge branch &#x27;feature/quiz-creation&#x27; into develop (74a5ce5)
    * 🔀 : merge branch &#x27;feature/quiz-edition&#x27; into develop (e037626)
-  👷 : add pages publication [4c0cc7e]
- 📝 : add configuration docs [a8a1447]
- 📝 : add explanation question syntax doc [bd279fe]
-  👷 : add deployment to clever-cloud [65ca38a]
    *  👷 : add deployment to clever-cloud (d313047)
-  👷 : add simple CI with sonarqube checks [04d464c]
- 🔀 : merge branch &#x27;feature/single-choice-questions&#x27; into develop [922197c]
- 🔀 : merge branch &#x27;feature/classroom-users&#x27; into develop [6c3bfaa]
- 💩 : manage full text submissions [64dcbdd]
- 💡 : add template source comment [145109d]
- 📝 : add adr 0002-use-gitlab4j [d777195]
- 📝 : add adr 0001-use-spring-boot [0a63b99]
- 📝 : add adr template [ac1ba6c]
-  👷 : switch to maven [6ea82a9]


