package cn.mydreamy.gradle.plugin

import cn.mydreamy.gradle.plugin.task.Png2SVGTask
import cn.mydreamy.gradle.plugin.utils.Holder
import org.gradle.api.Plugin
import org.gradle.api.Project

class Png2SvgPlugin implements Plugin<Project> {
    private static final String SVG_TASK_GROUP = "png"

    @Override
    void apply(Project project) {
        if (!project.android) {
            throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!');
        }

        //最外层配置
        def pngExtension = project.extensions.create("png", PngExtension)

        //内部转换配置png2svg
        def png2SvgExtensions = project.container(Png2SVGExtension)
        //设置参数
        project.extensions.png2svg = png2SvgExtensions

        //配置不能为空
        if (project.extensions.png2svg == null) {
            project.logger.info('png conf should be set!')
            return
        }

        project.afterEvaluate {
            Holder.PNG_HOLDER.clear()

            // get package name from android plugin
            def androidPlugin = project.android
            //初始化最外层配置的包名
            if (androidPlugin != null && pngExtension != null && pngExtension.packageName == null) {
                pngExtension.packageName = androidPlugin.defaultConfig.applicationId
            }
            project.logger.error("pngExtension.packageName:" + pngExtension.packageName + ",pngExtension.svgDir:" + pngExtension.svgDirs.size())

            // add vector and shape dirs to sourceSets
            if (androidPlugin != null && pngExtension != null) {
                //默认的svg目录
                def svgDirs = pngExtension.svgDirs != null ? pngExtension.svgDirs : []

                if (png2SvgExtensions != null) {//需要转换后的svg目录
                    png2SvgExtensions.each { config ->
                        if (config.svgDir != null && !svgDirs.contains(config.svgDir)) {
                            svgDirs.add(config.svgDir)
                        }
                    }
                }
            }

            // resolve dependencies
            if (!png2SvgExtensions.isEmpty()) {//内部转换参数不为空
                def png2svgTask = project.tasks.create("png2svg")
                png2svgTask.setGroup(SVG_TASK_GROUP)
                png2SvgExtensions.each { config ->
                    def name = config.name
                    project.logger.info("config.name:" + name)
                    def png2svgChildTask = project.tasks.create("png2svg" + firstLettertoUpperCase(name), Png2SVGTask)
                    png2svgChildTask.setGroup(SVG_TASK_GROUP)
                    png2svgChildTask.setExtensionName(name)
                    png2svgTask.dependsOn png2svgChildTask
                }
            }
        }
    }

    private def firstLettertoUpperCase(def text) {
        text.getAt(0).toString().toUpperCase() + text.substring(1)
    }
}