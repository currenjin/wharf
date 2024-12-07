package com.currenjin.wharf.detector;

import com.currenjin.wharf.domain.Framework;

import java.nio.file.Files;
import java.nio.file.Path;

public class NodeFrameworkDetector implements FrameworkDetector {
    public static final String PACKAGE_JSON = "package.json";

    @Override
    public Framework detect(Path path) {
        if (Files.exists(path.resolve(PACKAGE_JSON))) {
            return Framework.NODE_JS;
        }
        return Framework.UNKNOWN;
    }
}
