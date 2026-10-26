package utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public final class ResourceUtil {

    public static Reader openReader(String name) {
        InputStream in = ResourceUtil.class.getClassLoader().getResourceAsStream(name);
        if (in == null) {
            throw new IllegalStateException("Resource not found on classpath: " + name);
        }
        return new InputStreamReader(in, StandardCharsets.UTF_8);
    }

    public static String absolutePath(String name) {
        URL url = ResourceUtil.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new IllegalStateException("Resource not found on classpath: " + name);
        }
        try {
            return Paths.get(url.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Bad resource URI for " + name, e);
        }
    }
}