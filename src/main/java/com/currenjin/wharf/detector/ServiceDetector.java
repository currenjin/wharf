package com.currenjin.wharf.detector;

import java.nio.file.Path;
import java.util.List;

import com.currenjin.wharf.domain.Service;

public interface ServiceDetector {
	List<Service> detect(Path path);
}
