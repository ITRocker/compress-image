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
 * <p>png {
 *      svgDirs = "${projectDir}\svg-resources1"
 *      packageName="cn.mydreamy.gradle.plugin.png.sample"
 * }</p>
 *
 * @author yangzhao
 * @since 2019-01-20 上午11:07:34
 */
class PngExtension {

    public def svgDirs = []

    public def packageName

    public def debugMode = false
}