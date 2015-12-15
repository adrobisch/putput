package org.putput.stream;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamItemContentParserTest {
  @Test
  public void shouldParseMentions() {
    List<StreamItemContentParser.ContentFragment> mentions = StreamItemContentParser.parse("@foo @bar");

    assertThat(mentions)
        .hasSize(2)
        .hasOnlyElementsOfType(StreamItemContentParser.Mention.class);
  }
}