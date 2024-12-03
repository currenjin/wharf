package com.currenjin.wharf.analyzer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.currenjin.wharf.detector.FrameworkDetector;
import com.currenjin.wharf.domain.Framework;
import com.currenjin.wharf.domain.Project;

public class DefaultProjectAnalyzer implements ProjectAnalyzer {
	private final List<FrameworkDetector> detectors;

	public DefaultProjectAnalyzer(List<FrameworkDetector> detectors) {
		this.detectors = detectors;
	}

	@Override
	public Project analyze(Path path) {
		Framework framework = detectors.stream()
			.map(detector -> detector.detect(path))
			.filter(detected -> detected != Framework.UNKNOWN)
			.findFirst()
			.orElse(Framework.UNKNOWN);

		return new Project(framework, new ArrayList<>());
	}
}