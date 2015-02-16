package org.putput;

import com.jayway.restassured.RestAssured;
import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApp.class, IntegrationTestConfiguration.class})
@ActiveProfiles("integration")
@WebIntegrationTest("server.port:0")
public abstract class BrowserTest extends FluentTest {

  @Value("${local.server.port}")
  int port;

  @Autowired
  ApplicationContext applicationContext;

  @Before
  public void setUp() {
    RestAssured.port = port;
  }

  public int getPort() {
    return port;
  }

  @Override
  public WebDriver getDefaultDriver() {
    if(System.getProperty("phantomjs.binary.path") != null) {
      return new PhantomJSDriver();
    } else {
      return new FirefoxDriver();
    }
  }

  @Override
  public <P extends FluentPage> P goTo(P page) {
    StringBuffer pageUrl = new StringBuffer();
    if (page.getBaseUrl() != null) {
      pageUrl.append(page.getBaseUrl());
    } else {
      getBaseUrl();
    }
    pageUrl.append(page.getUrl());

    super.goTo(pageUrl.toString());
    return page;
  }

  @Override
  public String getBaseUrl() {
    return String.format("http://localhost:%d", getPort());
  }
}
