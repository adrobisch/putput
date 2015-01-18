package org.putput.util;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MailTemplates {

  Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/mails/"));

  public static class MailTemplate {
    Template template;

    Map<String, Object> data = new HashMap<>();

    protected MailTemplate(Template template) {
      this.template = template;
    }

    public String getText() {
      try {
        return template.apply(Context.newContext(new Object()).data(data));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public MailTemplate replace(String key, String value) {
      data.put(key, value);
      return this;
    }

    public MailTemplate data(String key, Object value) {
      data.put(key, value);
      return this;
    }
  }

  public MailTemplate create(String templateName) {
    try {
      return new MailTemplate(handlebars.compile(templateName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
