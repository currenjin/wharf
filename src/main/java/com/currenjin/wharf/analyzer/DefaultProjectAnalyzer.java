package com.currenjin.wharf.analyzer;

import com.currenjin.wharf.detector.FrameworkDetector;
import com.currenjin.wharf.detector.ServiceDetector;
import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;
import com.currenjin.wharf.domain.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultProjectAnalyzer implements ProjectAnalyzer {
    private final List<FrameworkDetector> frameworkDetectorList;
    private final List<ServiceDetector> serviceDetectorList;

    public DefaultProjectAnalyzer(List<FrameworkDetector> frameworkDetectorList) {
        this.frameworkDetectorList = frameworkDetectorList;
        this.serviceDetectorList = new ArrayList<>();
    }

    public DefaultProjectAnalyzer(
        List<FrameworkDetector> frameworkDetectorList,
        List<ServiceDetector> serviceDetectorList
    ) {
        this.frameworkDetectorList = frameworkDetectorList;
        this.serviceDetectorList = serviceDetectorList;
    }

    @Override
    public Project analyze(Path path) {
        Framework detectedFramework = detectFramework(path);
        List<Service> detectedServiceList = detectService(path);
        return new Project(detectedFramework, detectedServiceList);
    }

    private Framework detectFramework(Path path) {
        return frameworkDetectorList.stream()
            .map(detector -> detector.detect(path))
            .filter(Framework::isSupported)
            .findFirst()
            .orElse(Framework.UNKNOWN);
    }

    private List<Service> detectService(Path path) {
        return serviceDetectorList.stream()
            .map(detector -> detector.detect(path))
            .filter(Objects::nonNull)
            .toList();
    }
}
