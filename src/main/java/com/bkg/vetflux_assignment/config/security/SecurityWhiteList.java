package com.bkg.vetflux_assignment.config.security;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import lombok.Getter;

public class SecurityWhiteList {

    private static final List<Path> WHITE_LIST = List.of(
            new Path("/ws/**", HttpMethod.GET),
            new Path("/page/**", HttpMethod.GET),
            new Path("/user/**", HttpMethod.POST));

    public static String[] getWhitelistByMethod(HttpMethod method) {
        return WHITE_LIST.stream()
                .filter(white -> white.getMethods().contains(method))
                .map(Path::getUri)
                .toArray(String[]::new);
    }

    {
    }

    public static boolean isWhitelist(String contextPath, String currentPath, HttpMethod method) {
        AntPathMatcher matcher = new AntPathMatcher();
        return WHITE_LIST.stream()
                .filter(path -> matcher.match(contextPath + path.getUri(), currentPath))
                .anyMatch(white -> white.getMethods().contains(method));
    }

    @Getter
    public static class Path {

        private final String uri;
        private final Set<HttpMethod> methods;

        public Path(String uri, HttpMethod method) {
            this.uri = uri;
            this.methods = Set.of(method);
        }

        public Path(String uri, HttpMethod... method) {
            this.uri = uri;
            this.methods = Set.of(method);
        }

    }
}
