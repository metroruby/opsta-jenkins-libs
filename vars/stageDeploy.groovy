/**
  *
  * @param appName String of Application Name
  * @param gitPushJenkinsCred String of Jenkins Credential to pull and push source code
  * @param gitCloneUrl String of URL to use with git clone
  * @param appPrdTag String of tag to use with specify in git repo
  * @param commandTools String of commands to use with specify in comamnd
  */
def call(
  appName,
  gitPushJenkinsCred,
  gitCloneUrl,
  appPrdTag,
  commandTools
) {

  stage("Deploy ${appName}") {
    checkout([
        $class: 'GitSCM',
        branches: [[name: appPrdTag]],
        userRemoteConfigs: [[
          credentialsId: gitPushJenkinsCred,
          url: gitCloneUrl
        ]]
      ])
      sshagent(credentials: [gitPushJenkinsCred]) {
        sh """
          ${commandTools}
        """
      }
  }
}
