package org.putput.stream.html;

import org.owasp.html.Handler;
import org.owasp.html.HtmlStreamEventReceiver;
import org.owasp.html.HtmlStreamRenderer;
import org.putput.api.model.StreamItemMedia;

import java.util.List;
import java.util.Optional;

/**
 * this render will delegate to HtmlStreamRenderer for all tags except img,
 * it will create a media item for every image tags
 * images excluded in the output
 */
public class ImageAwareHtmlRenderer implements HtmlStreamEventReceiver {
  private final List<StreamItemMedia> media;
  HtmlStreamRenderer htmlStreamRenderer;

  public ImageAwareHtmlRenderer(Appendable htmlOutput, List<StreamItemMedia> media) {
    this.media = media;
    this.htmlStreamRenderer = HtmlStreamRenderer.create(htmlOutput, Handler.DO_NOTHING, Handler.DO_NOTHING);
  }

  @Override
  public void openDocument() {
    htmlStreamRenderer.openDocument();
  }

  @Override
  public void closeDocument() {
    htmlStreamRenderer.closeDocument();
  }

  @Override
  public void openTag(String elementName, List<String> attrs) {
    if (isImage(elementName)) {
      media.add(new StreamItemMedia().withType("image")
              .withUrl(attrValue("src", attrs).orElse(null)));
    }  else {
      htmlStreamRenderer.openTag(elementName, attrs);
    }
  }

  private boolean isImage(String elementName) {
    return elementName.equalsIgnoreCase("img");
  }

  Optional<String> attrValue(String name, List<String> attrs) {
    for (int i = 0; i < attrs.size(); i+=2) {
      if (attrs.get(i).equalsIgnoreCase(name)) {
        return Optional.of(attrs.get(i+1));
      }
    }
    return Optional.empty();
  }

  @Override
  public void closeTag(String elementName) {
    if (!isImage(elementName)) {
      htmlStreamRenderer.closeTag(elementName);
    }
  }

  @Override
  public void text(String text) {
    htmlStreamRenderer.text(text);
  }
}
