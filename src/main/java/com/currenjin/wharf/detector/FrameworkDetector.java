package com.currenjin.wharf.detector;

import java.nio.file.Path;

import com.currenjin.wharf.domain.Framework;

public interface FrameworkDetector {
	Framework detect(Path path);
}
