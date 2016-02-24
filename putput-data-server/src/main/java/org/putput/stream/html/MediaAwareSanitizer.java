package org.putput.stream.html;

import org.apache.commons.lang3.tuple.Pair;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.putput.api.model.StreamItemMedia;

import java.util.ArrayList;
import java.util.List;

class MediaAwareSanitizer {
  PolicyFactory LINKS = new HtmlPolicyBuilder()
          .allowStandardUrlProtocols()
          .allowElements("a")
          .allowAttributes("href", "target")
          .onElements("a")
          .requireRelNofollowOnLinks()
          .toFactory();

  PolicyFactory IFRAME =
          new HtmlPolicyBuilder()
                  .allowStandardUrlProtocols()
                  .allowElements("iframe")
                  .allowAttributes("src", "width", "height", "frameborder", "allowfullscreen")
                  .onElements("iframe")
                  .toFactory();

  public MediaAwareSanitizer() {
  }

  public Pair<String, List<StreamItemMedia>> sanitize(String input) {
    List<StreamItemMedia> media = new ArrayList<>();
    Appendable htmlOutput = new StringBuilder();
    ImageAwareHtmlRenderer htmlRenderer = new ImageAwareHtmlRenderer(htmlOutput, media);
    HtmlSanitizer.sanitize(input, createPolicy(htmlRenderer));
    return Pair.of(htmlOutput.toString(), media);
  }

  HtmlSanitizer.Policy createPolicy(ImageAwareHtmlRenderer htmlRenderer) {
    return Sanitizers.FORMATTING
            .and(LINKS)
            .and(IFRAME)
            .and(Sanitizers.IMAGES)
            .apply(htmlRenderer);
  }
}
