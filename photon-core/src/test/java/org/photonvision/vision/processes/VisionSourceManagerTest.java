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

package org.photonvision.vision.processes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.photonvision.common.configuration.CameraConfiguration;
import org.photonvision.common.configuration.ConfigManager;

public class VisionSourceManagerTest {
    @Test
    public void visionSourceTest() {
        var inst = new VisionSourceManager();
        var infoList = new ArrayList<CameraConfiguration>();
        inst.cameraInfoSupplier = () -> infoList;
        ConfigManager.getInstance().load();

        inst.tryMatchUSBCamImpl();
        var config = new CameraConfiguration("secondTestVideo", "dev/video1");
        CameraConfiguration info1 = new CameraConfiguration("firstVideoTest", "/dev/video0");
        infoList.add(info1);

        inst.registerLoadedConfigs(config);
        var sources = inst.tryMatchUSBCamImpl(false);

        assertTrue(inst.knownUsbCameras.contains(info1));
        assertEquals(1, inst.unmatchedLoadedConfigs.size());

        CameraConfiguration info2 = new CameraConfiguration("secondVideoTest", "/dev/video1");
        infoList.add(info2);
        inst.tryMatchUSBCamImpl(false);

        assertTrue(inst.knownUsbCameras.contains(info2));
        assertEquals(2, inst.knownUsbCameras.size());
        assertEquals(0, inst.unmatchedLoadedConfigs.size());
    }
}
