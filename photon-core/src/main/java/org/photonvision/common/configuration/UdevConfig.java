/*
 * Copyright (C) Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.common.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.photonvision.common.util.file.JacksonUtils;

public class UdevConfig {
    // Can be an integer team number, or an IP address
    public String allowedRegex = "video-pv\\d";
    public String udevDir = "/dev";
    public String newCameraPrefix = "Camera_at_";

    public UdevConfig() {
    }

    @JsonCreator
    public UdevConfig(
            @JsonProperty("udevDir") String udevDir,
            @JsonProperty("allowedRegex") String allowedRegex,
            @JsonProperty("newCameraPrefix") String newCameraPrefix) {
        this.udevDir = udevDir;
        this.allowedRegex = allowedRegex;
        this.newCameraPrefix = newCameraPrefix;
    }

    public Map<String, Object> toHashMap() {
        try {
            var ret = new ObjectMapper().convertValue(this, JacksonUtils.UIMap.class);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public String toString() {
        return "UdevConfig [udevPath="
                + udevDir
                + ", allowedRegex="
                + allowedRegex
                + ", newCameraPrefix="
                + newCameraPrefix
                + "]";
    }
}
