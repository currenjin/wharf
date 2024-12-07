package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Framework;

import java.nio.file.Path;

public interface FrameworkDetector {
    Framework detect(Path path);
}
