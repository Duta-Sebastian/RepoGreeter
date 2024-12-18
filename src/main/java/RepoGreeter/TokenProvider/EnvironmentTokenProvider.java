package RepoGreeter.TokenProvider;

public class EnvironmentTokenProvider implements TokenProvider {
    private final String environmentVariable;

    public EnvironmentTokenProvider(String environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    public String getToken() {
        String token = System.getenv(environmentVariable);
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Environment variable " + environmentVariable + " not set");
        }
        return token;
    }
}
