/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.mydreamy.gradle.plugin.task

import cn.mydreamy.gradle.plugin.utils.Holder
import cn.mydreamy.lib.utils.PngSvgConverter
import org.gradle.api.tasks.TaskAction

class Png2SVGTask extends Png2SVGBaseTask {

    def errorSvgs = []

    def extensionName;

    void setExtensionName(def extensionName) {
        this.extensionName = extensionName;
    }

    @TaskAction
    void run() {
        super.run();
        errorSvgs.clear()
        png2svgConfigurations.each { config ->
            if (extensionName.equals(config.name)) {
                doPng2Svg(config.pngDir, resolveProjectDir(config.svgDir))
            }
        }
        errorSvgs.each { errorSvg ->
            logger.error(errorSvg + " error！")
        }
    }

    void doPng2Svg(def pngDir, def svgDir) {
        def dir = file(pngDir)
        if (dir.exists() && dir.isDirectory()) {
            dir.listFiles().each { pngFile ->
                if (!pngFile.isDirectory() && pngFile.length() > 0 && (pngFile.name.endsWith(".png"))) {
                    def pngName = pngFile.name.substring(0, pngFile.name.lastIndexOf(".png"))
                    png2svg(pngFile, file(svgDir, pngName + ".svg"))
                }
            }
        } else {
            logger.error("None of svg file was found! Please check " + pngDir + "!")
        }
    }

    void png2svg(def pngFile, def svgFile) {
        if (!svgFile.getParentFile().exists() && !svgFile.getParentFile().mkdirs()) {
            return
        }
        if (Holder.PNG_HOLDER.contains(svgFile.name)) {
            logger.error("Duplicated svg image file named ${pngFile.name}")
            return
        }
        Holder.PNG_HOLDER.add(svgFile.name)
        String error = PngSvgConverter.convertToSvg(pngFile.path, svgFile.path)
        if (error != null && !error.isEmpty()) {
            errorSvgs.add(pngFile.path)
            svgFile.delete()
            if (configuration.debugMode) {
                logger.error(error)
            }
        }
    }
}
