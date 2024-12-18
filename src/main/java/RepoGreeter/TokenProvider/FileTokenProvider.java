package RepoGreeter.TokenProvider;

import java.io.IOException;

public class FileTokenProvider implements TokenProvider {
    private final String configPath;

    public FileTokenProvider(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public String getToken() throws IOException {
        return ConfigLoader.loadToken(configPath);
    }
}
