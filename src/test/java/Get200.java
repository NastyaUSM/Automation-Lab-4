import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get200 {

    private CloseableHttpClient client;

    @BeforeMethod
    public void createClient(){
        client = HttpClients.createDefault();
    }

    @Test
    public void repositoryUsersAvatarUrlReturns200() throws IOException {
        HttpGet get = new HttpGet("https://api.github.com/users/avatar");
        try (CloseableHttpResponse response = client.execute(get)){
            int actualStatus = response.getStatusLine().getStatusCode();
            Assert.assertEquals(actualStatus, 200);
        }
    }

    @AfterMethod
    public void close(){
        try {
            if (client != null) {
                client.close();
            }
        } catch (Exception e){}
    }
}
