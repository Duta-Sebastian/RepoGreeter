package RepoGreeter.TokenProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {
    public static String loadToken(String configPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File(configPath), Config.class);
        return config.getToken();
    }

    private static class Config {
        private String token;

        public String getToken() {
            return token;
        }
    }
}
