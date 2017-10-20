package com.sk.wiki.models;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiRetriever {
	private static final Long INTERVAL_BETWEEN_REQUESTS = 1000L;
	private Long lastRequestedAt = -1L;

	public PageInfo readWikiPage(final String url) throws IOException {
		// waitIfNeeded();

		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("Invalid URL " + url);
		}
		final Connection conn = Jsoup.connect(url);
		final Document doc = conn.get();

		String title = null;

		final Elements titleElements = doc.getElementsByTag("title");
		if (titleElements.size() > 0) {
			title = titleElements.get(0).text().replace(" - Wikipedia", "");
		}
		final Element textContent = doc.getElementById("mw-content-text");

		final Elements paragraphs = textContent.select("p").select(":not(span[id=coordinates])");

		final PageInfo pageInfo = new PageInfo();

		pageInfo.setTitle(title);
		pageInfo.setParas(paragraphs);

		return pageInfo;
	}

	private void waitIfNeeded() {
		if (lastRequestedAt != -1) {
			final long currentMillis = System.currentTimeMillis();
			final long nextRequestMillis = lastRequestedAt + INTERVAL_BETWEEN_REQUESTS;
			if (currentMillis < nextRequestMillis) {
				try {
					Thread.sleep(nextRequestMillis - currentMillis);
				} catch (final InterruptedException e) {
					System.err.println("Warning: sleep interrupted in fetchWikipedia.");
				}
			}
		}
		lastRequestedAt = System.currentTimeMillis();
	}
}
