package RepoGreeter.TokenProvider;

import java.io.IOException;

public interface TokenProvider {
    String getToken() throws IOException;
}
