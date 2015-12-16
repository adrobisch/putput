package org.putput.util;

import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class MailTemplates {
  public static class MailTemplate {
    String template;

    Map<String, String> replacements = new HashMap<>();

    protected MailTemplate(String template) {
      this.template = template;
    }

    public String getText() {
      String resultText = "" + template;
      for (Map.Entry<String, String> replace : replacements.entrySet()) {
        if (replace.getValue() != null) {
          resultText = resultText.replace("{{" + replace.getKey() + "}}", replace.getValue());
        }
      }
      return resultText;
    }

    public MailTemplate replace(String key, String value) {
      replacements.put(key, value);
      return this;
    }
  }

  public MailTemplate create(String templateName) {
    InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("mails" + File.separatorChar + templateName);
    try {
      return new MailTemplate(StreamUtils.copyToString(resourceAsStream, Charset.forName("UTF-8")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
