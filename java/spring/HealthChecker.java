import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

class HealthChecker {
    public static boolean isUp(String httpHealthEndpoint) {
        try {
            return _isUp(httpHealthEndpoint);
        } catch (NoHttpResponseException | SocketException | SocketTimeoutException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean _isUp(String httpHealthEndpoint) throws IOException {
        Response response = Request.Get(httpHealthEndpoint)
                .connectTimeout(5000)
                .socketTimeout(5000)
                .execute();
        return response.returnResponse().getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }
}
