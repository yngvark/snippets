import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

class DockerHttpServerRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProcessHelper processHelper = new ProcessHelper();
    private final String httpHealthEndpoint;

    public DockerHttpServerRunner(String httpHealthEndpoint) {
        this.httpHealthEndpoint = httpHealthEndpoint;
    }

    public String runCommand(String command) {
        try {
            return tryToRunCommand(command);
        } catch (RuntimeException e) {
            stopContainerIfPresent();
            throw e;
        } catch (InterruptedException | IOException e) {
            stopContainerIfPresent();
            throw new RuntimeException(e);
        }
    }

    private String containerId = "";

    private String tryToRunCommand(String command) throws IOException, InterruptedException {
        InputStream inputStream = processHelper.runCommand(command);
        containerId = saveContainerId(inputStream);

        waitForHttpServerToRespond();

        return containerId;
    }

    private String saveContainerId(InputStream inputStream) throws IOException {
        BufferedInputStream bos = new BufferedInputStream(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(bos));
        String containerId = br.readLine();
        return containerId;
    }

    private void waitForHttpServerToRespond() throws InterruptedException {
        boolean containerResponded;
        LocalDateTime start = LocalDateTime.now();

        while (true) {
            containerResponded = HealthChecker.isUp(httpHealthEndpoint);
            logger.debug("containerResponded: {}", containerResponded);

            if (containerResponded)
                return;

            throwErrorIfTimeout(start);

            // We do a sleep here to prevent a fast loop of stuff happening, in case health checker returns immediately,
            // which it should not.
            Thread.sleep(2000l);
        }
    }

    private void throwErrorIfTimeout(LocalDateTime start) {
        Duration timeSpent = Duration.between(start, LocalDateTime.now());
        logger.trace("Duration: {}", timeSpent);

        if (timeSpent.getSeconds() > 16l)
            throw new RuntimeException("Timeout");
    }

    private void stopContainerIfPresent() {
        if (!containerId.isEmpty())
            stopContainer();
    }

    public void stopContainer() {
        logger.debug("Stopping container: {}", containerId);
        try {
            new ProcessBuilder().command(
                    "docker",
                    "stop",
                    containerId
            ).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
