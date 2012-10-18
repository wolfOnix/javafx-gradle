/*
 * Copyright (c) 2012, Danno Ferrin
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of Danno Ferrin nor the
 *         names of contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.bitbucket.shemnon.javafxplugin.tasks;


import org.gradle.api.internal.ConventionTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.file.FileCollection

/**
 * Created by IntelliJ IDEA.
 * User: dannoferrin
 * Date: 3/5/11
 * Time: 7:18 AM
 */
public class JavaFXJarTask extends ConventionTask {

    @TaskAction
    processResources() {
        ant.taskdef(name: 'fxJar',
                classname: 'com.sun.javafx.tools.ant.FXJar',
                classpath: getAntJavaFXJar().asPath)

        ant.fxJar(destfile: getOutputFile()) {

            getInputFiles().each {
                if (it.directory) {
                    fileset(dir: it)
                } else if (it.file) {
                    fileset(file: it)
                }
            }
            application(
                    name: getAppName(),
                    mainClass: getJavafxMainClass()
                    //FIXME preloader
                    //FIXME fallback
            )
        }
    }

    FileCollection antJavaFXJar

    String appName
    String javafxMainClass

    @OutputFile
    File outputFile

    @InputFiles
    FileCollection inputFiles
}