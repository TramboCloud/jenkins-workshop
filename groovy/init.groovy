#!groovy
import jenkins.*
import jenkins.model.*
import hudson.model.*
import java.io.File
import jenkins.model.Jenkins
import hudson.security.*
import java.util.logging.Logger
import jenkins.security.s2m.*
import hudson.util.Secret

import hudson.tasks.Maven.MavenInstallation;
import hudson.tools.*;
import hudson.util.DescribableList;

def loader = new GroovyClassLoader(getClass().getClassLoader())
def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
def jenkins_Url = System.getenv("JENKINS_URL")
def adminAddress = System.getenv("ADMIN_ADDRESS")

if ( jenkins_Url == null)
  throw new Exception("JENKINS_URL needs to be set")

if ( adminAddress == null)
  throw new Exception("ADMIN_ADDRESS needs to be set")

Jenkins.instance.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false);
Jenkins.instance.save()

// maven congig global tool
def mavenDesc = jenkins.model.Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0]

def isp = new InstallSourceProperty()
def autoInstaller = new hudson.tasks.Maven.MavenInstaller("3.3.3")
isp.installers.add(autoInstaller)

def proplist = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
proplist.add(isp)

def installation = new MavenInstallation("maven3", "", proplist)

mavenDesc.setInstallations(installation)
mavenDesc.save()
