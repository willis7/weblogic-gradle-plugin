package org.gradle.api.plugins.weblogic

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

/**
 * Custom Gradle task wrapping the Ant WLST task
 * @author Sion Williams
 */
class WLST extends DefaultTask{

    @Input
    String fileName

    @Input
    @Optional
    String properties

    @Input
    @Optional
    String arguments

    @Input
    @Optional
    boolean failOnError = true

    @Input
    @Optional
    boolean executeScriptBeforeFile = false

    @Input
    @Optional
    boolean debug = false

    @TaskAction
    void runWlstCommand(){
        ant.taskdef( name: 'wlst',
                classname: 'weblogic.ant.taskdefs.management.WLSTTask',
                classpath: System.env.WEBLOGIC_CLASSPATH )
        ant.wlst( debug: debug,
                arguments: arguments,
                failOnError: failOnError,
                properties: properties,
                fileName: fileName,
                classpath: System.env.WEBLOGIC_CLASSPATH )
    }
}
