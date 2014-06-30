package org.gradle.api.plugins.weblogic

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

/**
 * Custom Gradle task wrapping the Ant WLDeploy task
 * @author Sion Williams
 */
class WLDeploy extends DefaultTask{

    /*
     * The deployment action to perform.
     * Valid values are deploy, cancel, undeploy, redeploy, distribute, start, and stop.
     */
    @Input
    String action


    /*
     * The URL of the Administration Server.
     *
     * The format of the value of this attribute is protocol://host:port, where protocol is either http or t3,
     * host is the host on which the Administration Server is running, and port is the port which the Administration
     * Server is listening.
     *
     * Note: In order to use the HTTP protocol, you must enable the http tunnelling option in the Administration Console.
     */
    @Input
    String adminurl


    /*
     * The version identifier of the deployed application.
     */
    @Input
    String appversion = project.version


    /*
     * Enable wldeploy debugging messages.
     */
    @Input
    @Optional
    boolean debug = false

    /*
     * The deployment name for the deployed application.
     * If you do not specify this attribute, WebLogic Server assigns a deployment name to the application, based on its
     * archive file or exploded directory.
     */
    @Input
    String deploymentName = "${project.name}_${project.version}"

    /*
     * The administrative password.
     * To avoid having the plain text password appear in the build file or in process utilities such as ps, first store
     * a valid username and encrypted password in a configuration file using the WebLogic Scripting Tool (WLST)
     * storeUserConfig command. Then omit both the username and password attributes in your Ant build file. When the
     * attributes are omitted, wldeploy attempts to login using values obtained from the default configuration file.
     * If you want to obtain a username and password from a non-default configuration file and key file, use the
     * userconfigfile and userkeyfile attributes with wldeploy.
     * See the command reference for storeUserConfig in the WLST Command and Variable Reference for more information on
     * storing and encrypting passwords.
     */
    @Input
    String password

    /*
     * The archive file or exploded directory to deploy.
     */
    @Input
    String source

    /*
     * The list of target servers to which the application is deployed.
     * The value of this attribute is a comma-separated list of the target servers, clusters, or virtual hosts.
     * If you do not specify a target list when deploying an application, the target defaults to the Administration
     * Server instance.
     */
    @Input
    String targets

    /*
     * The administrative username.
     */
    @Input
    String user

    /*
     * Specifies whether wldeploy displays verbose output messages.
     */
    @Input
    @Optional
    boolean verbose = false

    @TaskAction
    void runWlDeployCommand(){
        ant.taskdef( name: 'wldeploy',
                classname: 'weblogic.ant.taskdefs.management.WLDeploy',
                classpath: System.env.WEBLOGIC_CLASSPATH )

        ant.wldeploy( action: action, verbose: verbose, debug: debug,
                name: deploymentName, source: source,
                user: user, password: password,
                adminurl: adminurl, targets: targets)
    }
}
