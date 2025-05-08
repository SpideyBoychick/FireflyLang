package org.main;

import java.io.IOException;

public class ResManager {
    public String getRes(String path) throws IOException {
        return new String(getClass().getResourceAsStream(path).readAllBytes());
    }
}
