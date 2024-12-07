package com.currenjin.wharf.domain;

public enum Framework {
    SPRING_BOOT("Spring Boot"),
    NODE_JS("Node.js"),
    DJANGO("Django"),
    RAILS("Ruby on Rails"),
    UNKNOWN("Unknown"),
    ;

    private final String displayName;

    Framework(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
