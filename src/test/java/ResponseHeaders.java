import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class ResponseHeaders {

    private CloseableHttpClient client;

    @BeforeMethod
    public void createClient(){
        client = HttpClients.createDefault();
    }

    @Test
    public void repositoryUsersAvatarUrlReturnsXXssProtectionHeader() throws IOException {
        HttpGet get = new HttpGet("https://api.github.com/users/avatar");
        try (CloseableHttpResponse response = client.execute(get)){
            boolean hasXXssProtectionHeader = response.getFirstHeader("x-xss-protection") != null;
            Assert.assertTrue(hasXXssProtectionHeader);
        }
    }

    @Test
    public void repositoryUsersAvatarUrlReturnsXXssProtectionHeaderHas0() throws IOException {
        HttpGet get = new HttpGet("https://api.github.com/users/avatar");
        try (CloseableHttpResponse response = client.execute(get)){
            Header xXssProtectionHeader = response.getFirstHeader("x-xss-protection");
            Assert.assertEquals(xXssProtectionHeader.getValue(), "0");
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
