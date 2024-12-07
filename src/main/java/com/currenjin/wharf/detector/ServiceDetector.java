package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;

import java.nio.file.Path;
import java.util.List;

public interface ServiceDetector {
    List<Service> detect(Path path);
}
