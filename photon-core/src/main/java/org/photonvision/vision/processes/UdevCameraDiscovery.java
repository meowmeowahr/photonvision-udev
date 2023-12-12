package org.photonvision.vision.processes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.photonvision.common.configuration.CameraConfiguration;
import org.photonvision.common.configuration.ConfigManager;

public class UdevCameraDiscovery {

    public static Set<String> UdevCams;

    public static List<CameraConfiguration> getConnectedUsbCameras() {
        List<CameraConfiguration> cams = new ArrayList<>();

        var config = ConfigManager.getInstance().getConfig().getUdevConfig();

        // Find udevs matching regex
        reloadUdevs(config.udevDir, config.allowedRegex);

        for (String cameraUdev : UdevCams) {
            cams.add(new CameraConfiguration(cameraNameToBaseName(config.newCameraPrefix + cameraUdev), config.udevDir + "/" + cameraUdev));
        }
        return cams;
    }

    private static Set<String> reloadUdevs(String udevDir, String udevRegex) {
        UdevCams = filterSetByRegex(Stream.of(new File(udevDir).listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet()), udevRegex);
        return UdevCams;
    }

    private static String cameraNameToBaseName(String cameraName) {
        return cameraName.replaceAll("[^\\x00-\\x7F]", "");
    }

    public static Set<String> filterSetByRegex(Set<String> inputList, String regex) {
        List<String> filteredList = new ArrayList<>();

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Iterate through the input list and add matching elements to the filtered list
        for (String element : inputList) {
            if (pattern.matcher(element).find()) {
                filteredList.add(element);
            }
        }

        return Set.copyOf(filteredList);
    }
}
