import jenkins.model.Jenkins
import hudson.security.*
import hudson.plugins.active_directory.*

def jenkins = Jenkins.instance

if( System.getenv('DOCKER_COMPOSE') ){
  println "Jenkins database auth"
  def hudsonRealm = new HudsonPrivateSecurityRealm(false)
  hudsonRealm.createAccount("admin","admin")
  hudsonRealm.createAccount('builder',"builder")
  hudsonRealm.createAccount('reader',"reader")
  jenkins.instance.setSecurityRealm(hudsonRealm)
  jenkins.save()
}
