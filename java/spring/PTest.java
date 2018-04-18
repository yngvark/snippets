import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void arne() throws Exception {
        DockerHttpServerRunner dockerHttpServerRunner = new DockerHttpServerRunner("http://localhost:8080/auth");

        try {
            String containerId = dockerHttpServerRunner.runCommand("docker run" +
                    " --rm -d " +
                    "-e KEYCLOAK_USER=hehe " +
                    "-e KEYCLOAK_PASSWORD=hihi " +
                    "-p 8080:8080 " +
                    "jboss/keycloak@sha256:518aaa87ecd1492d4bd7f7d0df6c7bb856a37c89d323c110ebc4835860a4de82"); // 3.4.3.Final

            ProcessHelper processHelper = new ProcessHelper();
            processHelper.runCommandAndLogOutput(
                    "docker exec " + containerId + " " + "/opt/jboss/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user hehe --password hihi"
            );
        } catch (Throwable e) {
            throw e;
        } finally {
            dockerHttpServerRunner.stopContainer();
        }
    }
}
