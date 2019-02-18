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

package cn.mydreamy.gradle.plugin
/**
 * <p>png2svg {
 *      svg_a {//name
 *      pngDir = "${projectDir}/png_a"
 *      svgDir = "src/main/res_svg/drawable"
 *      }
 * }</p>
 * @author yangzhao
 * @since 2019-01-20 上午11:07:34
 */
class Png2SVGExtension {

    public def name

    public def pngDir
    public def svgDir

    Png2SVGExtension(def name) {
        this.name = name
    }
}
