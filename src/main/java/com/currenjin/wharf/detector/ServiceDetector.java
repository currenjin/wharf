package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Service;

import java.nio.file.Path;

public interface ServiceDetector {
    Service detect(Path path);
}
