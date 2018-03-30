properties (
        [pipelineTriggers([]),
            [$class: 'GithubProjectProperty', displayName: 'GitLab', projectUrlStr: 'https://code.anecon.com/taf/a2a.taf.git/'],
            [$class: 'GitLabConnectionProperty', gitLabConnection: 'GitLab']
        ]
)

node {
    stage "Checkout"
    checkout scm

    stage "Build"
    gitlabCommitStatus("Build") {
        mvn 'clean compile'
    }

    stage "Test & QA"
    gitlabCommitStatus("Test & QA") {
        mvn "-Dsonar.host.url=https://code.anecon.com/sonar -Dsonar.branch=${env.BRANCH_NAME} verify org.sonarsource.scanner.maven:sonar-maven-plugin:3.0.2:sonar -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true"
    }

    gitlabCommitStatus("Package & Install") {
        mvn "-Dmaven.test.skip=true package install"
    }
}


void mvn(def args) {
    if (isUnix()) {
        sh "./mvnw ${args}"
    } else {
        bat "mvnw ${args}"
    }
}